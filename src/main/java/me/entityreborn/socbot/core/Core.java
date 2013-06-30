/*
 * The MIT License
 *
 * Copyright 2012 Jason Unger <entityreborn@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.entityreborn.socbot.core;

import me.entityreborn.socbot.events.EventManager;
import me.entityreborn.socbot.events.Listener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import me.entityreborn.socbot.api.Channel;
import me.entityreborn.socbot.api.SocBot;
import me.entityreborn.socbot.api.Numerics.Numeric;
import me.entityreborn.socbot.api.ServerInfo;
import me.entityreborn.socbot.api.Target;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.api.UserChannelMap;
import me.entityreborn.socbot.api.events.*;

/**
 * Implementation for non-connection-specific type stuff. Subclasses for the bot
 * should subclass this class.
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Core extends Engine implements SocBot, Listener {

    static String VERSION = "";
    UserChannelMap userChannelMap;
    Map<String, User> userMap;
    Map<String, Channel> channelMap;
    ServerInfo serverInfo;

    static {
        // Get the compiled version info from the manifest.
        Package p = Core.class.getPackage();
        String v = null;

        if (p == null) {
            p = Package.getPackage("me.entityreborn.socbot.core");
        }

        if (p != null) {
            v = p.getImplementationVersion();
        }

        // We're probably running from class files,
        // or something messed up.
        if (v == null) {
            v = "(unknown)";
        }

        VERSION = v;
    }

    /**
     * Create a new instance of Core
     */
    public Core() {
        userChannelMap = new IRCUserChannelMap();
        userMap = new HashMap<String, User>();
        channelMap = new HashMap<String, Channel>();
        serverInfo = new IRCServerInfo();
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public String getVersion() {
        return VERSION;
    }

    public Channel getChannel(String channel) {
        if (!channelMap.containsKey(channel.toLowerCase())) {
            channelMap.put(channel.toLowerCase(), new IRCChannel(channel, this));
        }

        return channelMap.get(channel.toLowerCase());
    }

    public User getUser(String nick) {
        String host = "";

        // Lets clean up the name to get the actual nick
        // instead of the full hostmask
        if (nick.contains("!")) {
            String[] parts = nick.split("!", 2);
            nick = parts[0];
            host = parts[1];
        }

        // Lets also remove any prefixes
        // Prefixes will be applied to channels elsewhere.
        String statusmsg = getServerInfo().getSupport("PREFIX");
        if (statusmsg != null) {
            String prefixes = statusmsg.split("[)]", 2)[1];

            if (prefixes.contains(nick.subSequence(0, 1))) {
                nick = nick.substring(1);
            }
        }

        if (!userMap.containsKey(nick.toLowerCase())) {
            User u = new IRCUser(nick, this);
            userMap.put(nick.toLowerCase(), u);
        }

        User u = userMap.get(nick.toLowerCase());

        //Update the hostmask if we are given it.
        if (!host.isEmpty()) {
            u.setHostmask(host);
        }
        //TODO: Arrays.asList(host.split("")));

        return u;
    }

    @Override
    public void handleLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        IRCPacket packet = new IRCPacket(line, this);

        EventManager.callEvent(new PacketReceivedEvent(packet), this);

        if (packet.getNumeric() != Numeric.UNKNOWN) {
            EventManager.callEvent(new NumericEvent(packet), this);
        }

        if (packet.getCommand().equals("PING")) {
            sendLine("PONG :" + packet.getMessage());
        }

        String command = packet.getCommand().toUpperCase();
        String message = packet.getMessage();

        AbstractEvent evt = null;

        if (packet.getNumeric() != Numeric.UNKNOWN) {
            evt = handleNumeric(packet);
        } else if (command.equals("JOIN")) {
            JoinEvent jn = new JoinEvent(packet);

            userChannelMap.add(packet.getSender(), jn.getChannel());
            jn.getChannel().trackUser(packet.getSender());

            evt = jn;
        } else if (command.equals("PART")) {
            PartEvent pt = new PartEvent(packet);
            Channel chan = pt.getChannel();
            chan.untrackUser(packet.getSender());

            if (packet.getSender().getName().equals(getNickname())) {
                userChannelMap.removeAllMappingsForChannel(chan);
            } else {
                userChannelMap.remove(packet.getSender(), chan);
            }

            evt = pt;
        } else if (command.equals("KICK")) {
            KickEvent ke = new KickEvent(packet);
            Set<Channel> chans = userChannelMap.getChannels(ke.getKicked());

            for (Channel chan : chans) {
                chan.untrackUser(ke.getKicked());
            }

            if (packet.getSender().getName().equals(getNickname())) {
                userChannelMap.removeAllMappingsForChannel(ke.getChannel());
            } else {
                userChannelMap.remove(packet.getSender(), ke.getChannel());
            }

            evt = ke;
        } else if (command.equals("QUIT")) {
            QuitEvent qt = new QuitEvent(packet);
            Set<Channel> chans = userChannelMap.getChannels(qt.getUser());

            for (Channel chan : chans) {
                chan.untrackUser(qt.getUser());
            }

            userChannelMap.removeAllMappingsForUser(packet.getSender());
            userMap.remove(qt.getUser().getName().toLowerCase());

            evt = qt;
        } else if (command.equals("PRIVMSG")) {
            if (message.startsWith("\01") && message.endsWith("\01")) {
                evt = new CTCPEvent(packet);
            } else {
                evt = new PrivmsgEvent(packet);
            }
        } else if (command.equals("NOTICE")) {
            if (message.startsWith("\01") && message.endsWith("\01")) {
                evt = new CTCPReplyEvent(packet);
            } else {
                evt = new NoticeEvent(packet);
            }
        } else if (command.equals("NICK")) {
            NickEvent ne = new NickEvent(packet);

            User u = ne.getSender();
            u.setName(ne.getNewNick());

            userMap.put(ne.getNewNick(), userMap.remove(ne.getOldNick()));

            evt = ne;
        } else if (command.equals("MODE")) {
            if (packet.getArgs().size() > 2) {
                ChannelUserModeChangeEvent cumce = new ChannelUserModeChangeEvent(packet);
                Channel chan = (Channel) cumce.getTarget();

                for (Map.Entry<User, String> entry : cumce.getAddedModes().entrySet()) {
                    chan.addUserMode(entry.getKey(), entry.getValue());
                }

                for (Map.Entry<User, String> entry : cumce.getRemovedModes().entrySet()) {
                    chan.remUserMode(entry.getKey(), entry.getValue());
                }

                evt = cumce;
            } else {
                ModeChangeEvent mce = new ModeChangeEvent(packet);
                Target tgt = mce.getTarget();

                tgt.addModes(mce.getAddedModes());
                tgt.removeModes(mce.getRemovedModes());

                evt = mce;
            }
        }

        if (evt != null) {
            EventManager.callEvent(evt, this);
        }
    }

    /**
     * Handle a {@link Numeric} packet that the bot received.
     *
     * @param packet the parsed numeric packet.
     * @return an {@link AbstractEvent} to be fired.
     */
    protected AbstractEvent handleNumeric(IRCPacket packet) {
        Numeric numeric = packet.getNumeric();

        switch (numeric) {
            case RPL_WELCOME:
                return new WelcomeEvent(packet);

            case RPL_NAMREPLY:
                String[] names = packet.getMessage().split(" ");
                int last = packet.getArgs().size() - 1;
                String channelname = packet.getArgs().get(last);

                for (String name : names) {
                    User user = getUser(name);
                    Channel channel = getChannel(channelname);
                    userChannelMap.add(user, channel);
                }

                break;
            default:
                return null;
        }

        return null;
    }

    @Override
    public void handleException(Throwable t) {
        t.printStackTrace();
    }

    public void quit(String message) {
        sendLine("QUIT :" + message);
    }

    public void quit() {
        quit("");
    }

    public UserChannelMap getUserChannelMap() {
        return userChannelMap;
    }

    public Set<User> getUsers() {
        return userChannelMap.getUsers();
    }

    public Set<Channel> getChannels() {
        return userChannelMap.getChannels();
    }

    public void join(String channel) {
        sendLine("JOIN " + channel);
    }
    
    public void join(String channel, String pass) {
        sendLine("JOIN " + channel + " :" + pass);
    }

    public void part(String channel) {
        sendLine("PART " + channel);
    }
    
    public void part(String channel, String message) {
        sendLine("PART " + channel + " :" + message);
    }
}
