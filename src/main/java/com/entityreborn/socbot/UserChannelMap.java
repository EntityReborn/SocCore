/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* Originally taken from 
 * http://grepcode.com/snapshot/repo1.maven.org/maven2/org.wicketstuff/wicketstuff-security-hive/6.0.0-beta1.1/
 * and modified to suit SocAPI's needs.
 * 
 * All credit for original code to the author below.
 */
package com.entityreborn.socbot;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Maps keys to lists of values and values to lists of keys. The whole concept
 * of key value is a bit vague here because each value is also a key. Consider
 * the following example: A maps to B and C, B maps to D. get(A) would return B
 * and C, get(B) would return A and D, get(C) would return A, get(D) would
 * return B. Each mapping is bidirectional.
 *
 * @author marrink
 */
public class UserChannelMap {

    private final Map<User, Set<Channel>> userToChannelMappings;
    private final Map<Channel, Set<User>> channelToUserMappings;

    /**
     * Creates map with default initial size and load factor.
     */
    public UserChannelMap() {
        userToChannelMappings = new HashMap<User, Set<Channel>>();
        channelToUserMappings = new HashMap<Channel, Set<User>>();
    }

    /**
     * Creates map with default load factor and specified initial size.
     *
     * @param initialCapacity
     */
    public UserChannelMap(int initialCapacity) {
        userToChannelMappings = new HashMap<User, Set<Channel>>(initialCapacity);
        channelToUserMappings = new HashMap<Channel, Set<User>>(initialCapacity);
    }

    /**
     * Creates map with specified initial size and load factor. For more
     * information about these see {@link HashMap}
     *
     * @param initialCapacity
     * @param loadFactor
     */
    public UserChannelMap(int initialCapacity, float loadFactor) {
        userToChannelMappings = new HashMap<User, Set<Channel>>(initialCapacity, loadFactor);
        channelToUserMappings = new HashMap<Channel, Set<User>>(initialCapacity, loadFactor);
    }

    /**
     * Adds a key value mapping in this map. Since this maps many to many
     * relations no previous mappings will be overridden. The size of the map
     * may or may not change depending on whether both objects are already
     * present or not
     *
     * @param user
     * @param channel
     */
    public void add(User user, Channel channel) {
        if (user == null) {
            throw new NullPointerException("user must not be null.");
        }
        
        if (channel == null) {
            throw new NullPointerException("channel must not be null.");
        }

        Set<Channel> channels = userToChannelMappings.get(user);
        
        if (channels == null) {
            channels = new HashSet<Channel>();
        }
        channels.add(channel);
        userToChannelMappings.put(user, channels);

        Set<User> users = channelToUserMappings.get(channel);
        
        if (users == null) {
            users = new HashSet<User>();
        }
        users.add(user);
        channelToUserMappings.put(channel, users);
    }

    /**
     * Removes a many to many mapping between two objects. The size of the map
     * may or may not change depending on on whether or not both objects have
     * other mappings.
     *
     * @param user left side of the mapping
     * @param channel right side of the mapping
     * @return false if the mapping did not exist, true otherwise
     */
    public boolean remove(User user, Channel channel) {
        Set<Channel> channels = userToChannelMappings.get(user);
        
        if (channels != null) {
            if (channels.remove(channel)) {
                if (channels.isEmpty()) {
                    userToChannelMappings.remove(user);
                }

                Set<User> users = channelToUserMappings.get(channel);
                users.remove(user);
                
                if (users.isEmpty()) {
                    channelToUserMappings.remove(channel);
                }
                
                return true;
            }
        }
        return false;
    }

    /**
     * Remove all mappings for an object. The size of the map will at least
     * decrease by one (if the object is present) but possibly more.
     *
     * @param user
     * @return the mappings that will be removed by this action
     */
    public Set<Channel> removeAllMappingsForUser(User user) {
        Set<Channel> channels = userToChannelMappings.remove(user);
        
        if (channels != null) {
            for (Channel curChannel : channels) {
                Set<User> curUsers = channelToUserMappings.get(curChannel);
                curUsers.remove(user);
                
                if (curUsers.isEmpty()) {
                    channelToUserMappings.remove(curChannel);
                }
            }
        }
        
        return channels;
    }

    /**
     * Remove all mappings for an object. The size of the map will at least
     * decrease by one (if the object is present) but possibly more.
     *
     * @param channel the right side of the many to many mapping
     * @return the mappings that will be removed by this action
     */
    public Set<User> removeAllMappingsForChannel(Channel channel) {
        Set<User> users = channelToUserMappings.remove(channel);
        
        if (users != null) {
            for (User curUser : users) {
                Set<Channel> curChannels = userToChannelMappings.get(curUser);
                curChannels.remove(channel);
                
                if (curChannels.isEmpty()) {
                    userToChannelMappings.remove(curUser);
                }
            }
        }
        return users;
    }
    
    public Set<User> getUsers() {
        return userToChannelMappings.keySet();
    }
    
    public Set<Channel> getChannels() {
        return channelToUserMappings.keySet();
    }

    /**
     * Gets the bidirectional mappings for this object.
     *
     * @param user
     * @return the many to many mappings for this object
     */
    public Set<Channel> getChannels(User user) {
        Set<Channel> set = userToChannelMappings.get(user);
        
        if (set == null) {
            return Collections.emptySet();
        }
        
        return Collections.unmodifiableSet(set);
    }

    /**
     * Gets the bidirectional mappings for this object.
     *
     * @param channel
     * @return the many to many mappings for this object
     */
    public Set<User> getUsers(Channel channel) {
        Set<User> set = channelToUserMappings.get(channel);
        
        if (set == null) {
            return Collections.emptySet();
        }
        
        return Collections.unmodifiableSet(set);
    }

    /**
     * Returns the number of mapped values, left or right
     *
     * @return the number of mapped values
     */
    public int size() {
        return userToChannelMappings.size() + channelToUserMappings.size();
    }

    /**
     * Returns the number of keys mapped to a value.
     *
     * @param user
     * @return the number of keys mapped to this value
     */
    public int numberOfMappingsForUser(User user) {
        Set<Channel> set = userToChannelMappings.get(user);
        
        if (set == null) {
            return 0;
        }
        
        return set.size();
    }

    /**
     * Returns the number of keys mapped to a value.
     *
     * @param channel
     * @return the number of keys mapped to this value
     */
    public int numberOfMappingsForChannel(Channel channel) {
        Set<User> set = channelToUserMappings.get(channel);
        
        if (set == null) {
            return 0;
        }
        
        return set.size();
    }

    /**
     * Check if this map contains a key.
     *
     * @param user a mapped object
     * @return true if this map contains the key, false otherwise
     */
    public boolean containsUser(User user) {
        return userToChannelMappings.containsKey(user);
    }

    /**
     * Check if this map contains a key.
     *
     * @param channel a mapped object
     * @return true if this map contains the key, false otherwise
     */
    public boolean containsChannel(Channel channel) {
        return channelToUserMappings.containsKey(channel);
    }

    /**
     * Check if this map contains any mappings. If this map does is empty size
     * will be 0.
     *
     * @return true if no mappings are present, false otherwise
     */
    public boolean isEmpty() {
        return userToChannelMappings.isEmpty();
    }

    /**
     * Removes all mappings.
     */
    public void clear() {
        userToChannelMappings.clear();
        channelToUserMappings.clear();
    }

    /**
     * Returns an <tt>Iterator</tt> over every left hand mapping in this map. In
     * no particular order.
     *
     * @return an iterator over this map
     */
    public Iterator<User> userIterator() {
        return userToChannelMappings.keySet().iterator();
    }

    /**
     * Returns an <tt>Iterator</tt> over every rightt hand mapping in this map.
     * In no particular order.
     *
     * @return an iterator over this map
     */
    public Iterator<Channel> channelIterator() {
        return channelToUserMappings.keySet().iterator();
    }

    /**
     * @return 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserChannelMap) {
            UserChannelMap other = (UserChannelMap) obj;
            
            return userToChannelMappings.equals(other.userToChannelMappings)
                    && channelToUserMappings.equals(other.userToChannelMappings);
        }
        return false;
    }

    /**
     * @return 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return 7 * channelToUserMappings.hashCode() ^ 37 * userToChannelMappings.hashCode() + 1979;
    }

    /**
     * @return 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return userToChannelMappings.toString() + '\n' + channelToUserMappings.toString();
    }
}
