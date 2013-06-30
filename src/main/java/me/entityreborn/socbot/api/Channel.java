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
     * Add one or more user modes to a {@link User} in this channel. The mode
     * string can contain more than one character.
     *
     * @param user the user to modify.
     * @param mode the mode(s) to add.
     */
    public void addUserMode(User user, String mode);

    /**
     * Remove one or more user modes from a {@link User} in this channel. The mode
     * string can contain more than one character.
     *
     * @param user the user to modify.
     * @param mode the mode(s) to remove.
     */
    public void remUserMode(User user, String mode);

    /**
     * Test to see if a given user has a given mode.
     * {@code mode} is expected to be a single character.
     * @param user
     * @param mode
     * @return
     */
    public boolean userHasMode(User user, String mode);

    public void trackUser(User user);

    public void untrackUser(User user);
}
