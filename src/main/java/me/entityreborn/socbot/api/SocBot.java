/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.Set;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface SocBot extends Connection {

    /**
     * Return the version of the current binary, as defined in the manifest of
     * the compiled jar.
     *
     * @return String defining the version of the binary.
     */
    public String getVersion();

    /**
     * Return the known users for this bot.
     *
     * @return set of {@link User}s
     */
    public Set<User> getUsers();

    /**
     * Return the channels this bot is in.
     *
     * @return set of {@link Channel}s
     */
    public Set<Channel> getChannels();

    /**
     * Get a reference to a {@link User} instance containing information about a
     * user. <p> If the user doesn't exist yet, a new one is created.
     *
     * @param nick the name of the user. Case is ignored.
     * @return instance of {@link User}
     */
    public User getUser(String nick);

    /**
     * Get a reference to a {@link Channel} instance containing information
     * about a channel the bot joined. <p> If the channel doesn't exist yet, a
     * new one is created.
     *
     * @param channel the name of the channel. Case is ignored.
     * @return instance of {@link Channel}
     */
    public Channel getChannel(String channel);

    /**
     * Return the many-to-many map relating {@link User}s and {@link Channel}s.
     *
     * @return map of users and channels
     */
    public UserChannelMap getUserChannelMap();

    /**
     * Get the server specific info for this bot. <p> Things like nick prefixes,
     * nick length, etc. These are provided by the RPL_ISUPPORT {@link Numeric}.
     *
     * @return
     */
    public ServerInfo getServerInfo();

    /**
     * Send a QUIT command to the server.
     *
     * @param message optional quit message to send.
     */
    public void quit(String message);

    /**
     * Send a QUIT command to the server, without a quit message.
     *
     */
    public void quit();
    
    public void join(String channel);
    public void part(String channel);
    public void join(String channel, String password);
    public void part(String channel, String message);
}
