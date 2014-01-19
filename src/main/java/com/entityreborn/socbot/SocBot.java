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
package com.entityreborn.socbot;

import com.entityreborn.socbot.Numerics.Numeric;
import com.entityreborn.socbot.events.AbstractEvent;
import com.entityreborn.socbot.events.CTCPEvent;
import com.entityreborn.socbot.events.CTCPReplyEvent;
import com.entityreborn.socbot.events.ChannelUserModeChangeEvent;
import com.entityreborn.socbot.events.ConnectedEvent;
import com.entityreborn.socbot.events.ConnectingEvent;
import com.entityreborn.socbot.events.DisconnectedEvent;
import com.entityreborn.socbot.events.ErrorEvent;
import com.entityreborn.socbot.events.JoinEvent;
import com.entityreborn.socbot.events.KickEvent;
import com.entityreborn.socbot.events.LineSendEvent;
import com.entityreborn.socbot.events.ModeChangeEvent;
import com.entityreborn.socbot.events.NickEvent;
import com.entityreborn.socbot.events.NoticeEvent;
import com.entityreborn.socbot.events.NumericEvent;
import com.entityreborn.socbot.events.PacketReceivedEvent;
import com.entityreborn.socbot.events.PartEvent;
import com.entityreborn.socbot.events.PrivmsgEvent;
import com.entityreborn.socbot.events.QuitEvent;
import com.entityreborn.socbot.events.WelcomeEvent;
import com.entityreborn.socbot.eventsystem.EventManager;
import com.entityreborn.socbot.eventsystem.Listener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation for non-connection-specific type stuff. Subclasses for the bot
 * should subclass this class.
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class SocBot extends Engine implements Listener {
    static String VERSION = "";
    UserChannelMap userChannelMap;
    Map<String, User> userMap;
    Map<String, Channel> channelMap;
    ServerInfo serverInfo;
    String id;
    UserFactory userFactory;

    static {
        // Get the compiled version info from the manifest.
        Package p = SocBot.class.getPackage();
        String v = null;

        if (p == null) {
            p = Package.getPackage("com.entityreborn.socbot");
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
     * @param identifier
     */
    public SocBot(String identifier) {
        id = identifier;
        userChannelMap = new UserChannelMap();
        userMap = new HashMap<String, User>();
        channelMap = new HashMap<String, Channel>();
        serverInfo = new ServerInfo(this);
        userFactory = new SimpleUserFactory();
    }
    
    public String getID() {
        return id;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public String getVersion() {
        return VERSION;
    }
    
    public Channel trackChannel(String channel) {
        Channel chan = channelMap.get(channel.toLowerCase());
        
        if (chan == null) {
            chan = new Channel(channel, this);
            
            channelMap.put(channel.toLowerCase(), chan);
        }
        
        return chan;
    }
    
    public Channel getChannel(String channel) {
        return channelMap.get(channel.toLowerCase());
    }
    
    public User getUser(String nick, boolean track) {
        String host = null;
        
        if (nick == null || nick.trim().isEmpty()) {
            return null;
        }

        // Lets clean up the name to get the actual nick
        // instead of the full hostmask.
        // We check for the nick starting with ! because 
        // NAM_REPLY might return nicks starting with !
        // depending on the PREFIX settings for the server,
        // which is a case that doesn't apply here.
        if (nick.contains("!") && !nick.startsWith("!")) {
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
        
        User u = userMap.get(nick.toLowerCase());
        
        if (u == null && track) {
            u = userFactory.createUser(nick, this);
            userMap.put(nick.toLowerCase(), u);
        } else if (u == null) {
            u = userFactory.createUser(nick, this);
        }
        
        if (host != null) {
            u.setHostmask(host);
        }
        
        return u;
    }

    public User getUser(String nick) {
       return getUser(nick, false);
    }

    public void handlePacket(Packet packet) {
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
        
        if (command.equals("JOIN")) {
            String channel;
            
            if (packet.getMessage().isEmpty()) {
                channel = packet.getArgs().get(0);
            } else {
                channel = packet.getMessage();
            }
            
            trackChannel(channel);
        }

        if (packet.getNumeric() != Numeric.UNKNOWN) {
            evt = handleNumeric(packet);
        } else if (command.equals("JOIN")) {
            JoinEvent jn = new JoinEvent(packet);
            Channel chan = trackChannel(jn.getChannel().getName());
            User usr = getUser(packet.getSender(), true);
            
            userChannelMap.add(usr, chan);
            chan.trackUser(usr);

            evt = jn;
        } else if (command.equals("PART")) {
            PartEvent pt = new PartEvent(packet);
            Channel chan = (Channel)pt.getChannel();
            
            if (chan != null) {
                chan.untrackUser(getUser(packet.getSender()));

                if (packet.getSender().equalsIgnoreCase(getNickname())) {
                    userChannelMap.removeAllMappingsForChannel(chan);
                } else {
                    userChannelMap.remove(getUser(packet.getSender()), chan);
                }
            }
            
            evt = pt;
        } else if (command.equals("KICK")) {
            KickEvent ke = new KickEvent(packet);
            Set<Channel> chans = userChannelMap.getChannels(ke.getKicked());

            for (Channel chan : chans) {
                Channel thechan = (Channel)ke.getChannel();
                
                if (thechan != null) {
                    thechan.untrackUser(ke.getKicked());
                }
            }

            if (packet.getSender().equalsIgnoreCase(getNickname())) {
                userChannelMap.removeAllMappingsForChannel(ke.getChannel());
            } else {
                userChannelMap.remove(getUser(packet.getSender()), ke.getChannel());
            }

            evt = ke;
        } else if (command.equals("QUIT")) {
            QuitEvent qt = new QuitEvent(packet);
            Set<Channel> chans = userChannelMap.getChannels(qt.getUser());
            
            for (Channel chan : chans) {
                Channel thechan = getChannel(chan.getName());
                
                if (thechan != null) {
                    thechan.untrackUser(qt.getUser());
                }
            }

            userChannelMap.removeAllMappingsForUser(getUser(packet.getSender()));
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

            User u = getUser(ne.getSender().getName());
            u.setName(ne.getNewNick());
            
            userMap.remove(ne.getUser().getLastNick());
            userMap.put(ne.getNewNick(), u);

            evt = ne;
        } else if (command.equals("MODE")) {
            if (packet.getArgs().size() > 2) {
                ChannelUserModeChangeEvent cumce = new ChannelUserModeChangeEvent(packet);
                Channel chan = (Channel)cumce.getChannel();

                if (chan != null) {
                    for (Map.Entry<User, String> entry : cumce.getAddedModes().entrySet()) {
                        chan.addUserMode(entry.getKey(), entry.getValue());
                    }

                    for (Map.Entry<User, String> entry : cumce.getRemovedModes().entrySet()) {
                        chan.remUserMode(entry.getKey(), entry.getValue());
                    }
                }
                
                evt = cumce;
            } else {
                ModeChangeEvent mce = new ModeChangeEvent(packet);
                Target tgt = (Target)mce.getTarget();

                tgt.addModes(mce.getAddedModes());
                tgt.removeModes(mce.getRemovedModes());

                evt = mce;
            }
        } else if (command.equals("TOPIC")) {
            String topic = packet.getMessage();
                
            getChannel(packet.getArgs().get(0)).setTopic(topic);
            
            // No need for an event, yet.
        } else if (command.equals("ERROR")) {
            evt = new ErrorEvent(packet);
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
    protected AbstractEvent handleNumeric(Packet packet) {
        Numeric numeric = packet.getNumeric();

        switch (numeric) {
            case RPL_WELCOME:
                return new WelcomeEvent(packet);
                
            case RPL_TOPIC:
                String topic = packet.getMessage();
                
                getChannel(packet.getArgs().get(0)).setTopic(topic);
                
                return null;

            case RPL_NAMREPLY:
                String[] names = packet.getMessage().split(" ");
                int last = packet.getArgs().size() - 1;
                String channelname = packet.getArgs().get(last);

                for (String name : names) {
                    User user = getUser(name, true);
                    Channel channel = getChannel(channelname);
                    channel.trackUser(user);
                    userChannelMap.add(user, channel);
                    
                    if (serverInfo.isPrefixed(name)) {
                        channel.addUserMode(user, serverInfo.getModeByPrefix(name));
                    }
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

    @Override
    protected void fireDisconnected(boolean wasClean, Engine e) {
        EventManager.callEvent(new DisconnectedEvent(wasClean, this), e);
    }
    
    @Override
    protected void fireConnected(String server, int port, Engine e) {
        EventManager.callEvent(new ConnectedEvent(server, port, this), e);
    }

    @Override
    protected void fireConnecting(String server, int port, Engine e) {
        EventManager.callEvent(new ConnectingEvent(this, server, port), e);
    }

    @Override
    public void fireSendLine(String line, Engine e) {
        EventManager.callEvent(new LineSendEvent(this, line), this);
    }

    @Override
    public void fireSendLineNow(String line, Engine e) {
        EventManager.callEvent(new LineSendEvent(this, line), this);
    }

    /**
     * @return the userFactory
     */
    public UserFactory getUserFactory() {
        return userFactory;
    }

    /**
     * @param userFactory the userFactory to set
     */
    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }
    
    public static class SimpleUserFactory implements UserFactory {
        public User createUser(String nick, SocBot bot) {
            return new User(nick, bot);
        }
    }
}
