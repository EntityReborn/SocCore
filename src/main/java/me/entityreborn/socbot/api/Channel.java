/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface Channel extends Target {

    /**
     * Get the topic currently set in this channel.
     *
     * @return the topic
     */
    public String getTopic();

    /**
     * Set the topic for this channel.
     *
     * @param topic the topic
     */
    public void setTopic(String topic);

    /**
     * Get the users that are associated with this channel.
     *
     * @return set of {@link User}s
     */
    public Set<User> getUsers();

    /**
     * Get the user modes for all the {@link User}s in this channel. Modes are
     * stored as a single string.
     *
     * @return
     */
    public Map<User, String> getUserModes();

    /**
     * Get the user modes for a single {@link User} in this channel.
     *
     * @param user the user.
     * @return modes string.
     */
    public String getUserModes(User user);

    /**
     * Test to see if a given user has a given mode.
     * {@code mode} is expected to be a single character.
     * @param user
     * @param mode
     * @return
     */
    public boolean userHasMode(User user, String mode);
}
