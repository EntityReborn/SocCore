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
package com.entityreborn.socbot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class Target {
    
    public static class Util {

        public static boolean isUser(String name, SocBot bot) {
            String chantypes = bot.getServerInfo().getSupport("CHANTYPES");

            if (chantypes != null) {
                if (chantypes.contains(name.subSequence(0, 1))) {
                    return false;
                }
            } else {
                // The server hasn't sent RPL_ISUPPORT yet. Lets use a sane default.
                if (name.startsWith("#")) {
                    return false;
                }
            }

            return true;
        }
    }

    protected final SocBot bot;
    private String modes;
    private final Map<String, Object> metadata = new HashMap<String, Object>();

    public Target(SocBot b) {
        bot = b;
        modes = "";
    }
    
    public boolean hasMetaData(String id) {
        return metadata.containsKey(id.toLowerCase());
    }
    
    public Object getMetaData(String id) {
        return metadata.get(id.toLowerCase());
    }
    
    public void setMetaData(String id, Object data) {
        metadata.put(id.toLowerCase(), data);
    }
    
    public Object remMetaData(String id) {
        return metadata.remove(id.toLowerCase());
    }
    
    public Map<String, Object> getMetaData() {
        return Collections.unmodifiableMap(metadata);
    }

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }

    public SocBot getBot() {
        return bot;
    }

    public void sendMsg(String... message) {
        for (String msg : message) {
            if (msg == null) {
                continue;
            }
            
            String[] parts = msg.split("\r?\n");
            
            for (String part : parts) {
                bot.sendLine("PRIVMSG " + getName() + " :" + part);
            }
        }
    }

    public void sendCTCP(String type, String... message) {
        for (String msg : message) {
            if (msg == null) {
                continue;
            }
            
            String[] parts = msg.split("\r?\n");
            
            for (String part : parts) {
                sendMsg("\01" + type + " " + part + "\01");
            }
        }
    }

    public void sendNotice(String... message) {
        for (String msg : message) {
            if (msg == null) {
                continue;
            }
            
            String[] parts = msg.split("\r?\n");
            
            for (String part : parts) {
                bot.sendLine("NOTICE " + getName() + " :" + part);
            }
        }
    }

    public void sendCTCPReply(String type, String... message) {
        for (String msg : message) {
            if (msg == null) {
                continue;
            }
            
            String[] parts = msg.split("\r?\n");
            
            for (String part : parts) {
                sendNotice("\01" + type + " " + part + "\01");
            }
        }
    }

    public void addModes(String m) {
        for (char c : m.toCharArray()) {
            if (!modes.contains(Character.toString(c))) {
                modes += c;
            }
        }
    }

    public void removeModes(String m) {
        for (char c : m.toCharArray()) {
            modes = modes.replace(Character.toString(c), "");
        }
    }

    public String getModes() {
        return modes;
    }
}
