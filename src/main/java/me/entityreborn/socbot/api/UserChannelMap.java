/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface UserChannelMap {

    /**
     * Adds a key value mapping in this map. Since this maps many to many
     * relations no previous mappings will be overridden. The size of the map
     * may or may not change depending on whether both objects are already
     * present or not
     *
     * @param user
     * @param channel
     */
    void add(User user, Channel channel);

    /**
     * Returns an <tt>Iterator</tt> over every right hand mapping in this map.
     * In no particular order.
     *
     * @return an iterator over this map
     */
    Iterator<Channel> channelIterator();

    /**
     * Removes all mappings.
     */
    void clear();

    /**
     * Check if this map contains a key.
     *
     * @param channel a mapped object
     * @return true if this map contains the key, false otherwise
     */
    boolean containsChannel(Channel channel);

    /**
     * Check if this map contains a key.
     *
     * @param user a mapped object
     * @return true if this map contains the key, false otherwise
     */
    boolean containsUser(User user);

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    boolean equals(Object obj);

    public Set<User> getUsers();
    
    public Set<Channel> getChannels();
    
    /**
     * Gets the bidirectional mappings for this object.
     *
     * @param user
     * @return the many to many mappings for this object
     */
    Set<Channel> getChannels(User user);

    /**
     * Gets the bidirectional mappings for this object.
     *
     * @param channel
     * @return the many to many mappings for this object
     */
    Set<User> getUsers(Channel channel);

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * Check if this map contains any mappings. If this map does is empty size
     * will be 0.
     *
     * @return true if no mappings are present, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of keys mapped to a value.
     *
     * @param channel
     * @return the number of keys mapped to this value
     */
    int numberOfMappingsForChannel(Channel channel);

    /**
     * Returns the number of keys mapped to a value.
     *
     * @param user
     * @return the number of keys mapped to this value
     */
    int numberOfMappingsForUser(User user);

    /**
     * Removes a many to many mapping between two objects. The size of the map
     * may or may not change depending on on whether or not both objects have
     * other mappings.
     *
     * @param user left side of the mapping
     * @param channel right side of the mapping
     * @return false if the mapping did not exist, true otherwise
     */
    boolean remove(User user, Channel channel);

    /**
     * Remove all mappings for an object. The size of the map will at least
     * decrease by one (if the object is present) but possibly more.
     *
     * @param channel the right side of the many to many mapping
     * @return the mappings that will be removed by this action
     */
    Set<User> removeAllMappingsForChannel(Channel channel);

    /**
     * Remove all mappings for an object. The size of the map will at least
     * decrease by one (if the object is present) but possibly more.
     *
     * @param left the left side of the many to many mapping
     * @return the mappings that will be removed by this action
     */
    Set<Channel> removeAllMappingsForUser(User user);

    /**
     * Returns the number of mapped values, left or right
     *
     * @return the number of mapped values
     */
    int size();

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    String toString();

    /**
     * Returns an <tt>Iterator</tt> over every left hand mapping in this map. In
     * no particular order.
     *
     * @return an iterator over this map
     */
    Iterator<User> userIterator();
    
}
