/*
 * The MIT License
 *
 * Copyright 2013 Jason Unger <entityreborn@gmail.com>.
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import me.entityreborn.socbot.api.Channel;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.events.Listener;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class IRCChannel extends IRCTarget implements Channel, Listener {
    String name;
    String topic = "";
    Map<User, String> userModes = new HashMap<User, String>();
    
    public IRCChannel(String n, Core b) {
        super(b);
        name = n;
    }
    
    public Set<User> getUsers() {
        return bot.getUserChannelMap().getUsers(this);
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public void addUserMode(User user, String mode) {
        if (!userModes.containsKey(user)) {
            return;
        }
        
        for (char c : mode.toCharArray()) {
            String m = Character.toString(c);
            
            if (!userModes.get(user).contains(m)) {
                userModes.put(user, userModes.get(user) + m);
            }
        }
    }

    public void remUserMode(User user, String mode) {
        if (!userModes.containsKey(user)) {
            return;
        }
        
        for (char c : mode.toCharArray()) {
            String m = Character.toString(c);
            
            userModes.put(user, userModes.get(user).replace(m, ""));
        }
    }
    
    public boolean userHasMode(User user, String mode) {
        if (userModes.containsKey(user) && userModes.get(user).contains(mode)) {
            return true;
        }
        
        return false;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String t) {
        topic = t;
    }

    public Map<User, String> getUserModes() {
        return userModes;
    }

    public String getUserModes(User user) {
        return userModes.get(user);
    }

    public void trackUser(User user) {
        userModes.put(user, "");
    }

    public void untrackUser(User user) {
        userModes.remove(user);
    }
}
