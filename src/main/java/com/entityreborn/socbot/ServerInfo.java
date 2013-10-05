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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.entityreborn.socbot.events.NumericEvent;
import com.entityreborn.socbot.eventsystem.EventHandler;
import com.entityreborn.socbot.eventsystem.EventManager;
import com.entityreborn.socbot.eventsystem.Listener;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class ServerInfo implements Listener{

    String yourHost;
    String created;
    Map<String, String> supports;
    List<String> motd;

    public ServerInfo(SocBot bot) {
        yourHost = "";
        created = "";
        supports = new HashMap<String, String>();
        motd = new ArrayList<String>();

        EventManager.registerEvents(this, bot);
    }

    @EventHandler
    public void handleEvent(NumericEvent event) {
        switch (event.getNumeric()) {
            case RPL_YOURHOST:
                yourHost = event.getMessage();
                break;
            case RPL_CREATED:
                created = event.getMessage();
                break;
            case RPL_ISUPPORT:
                for (String parts : event.getPacket().getArgs()) {
                    if (parts.contains("=")) {
                        String[] chunk = parts.split("=", 2);
                        supports.put(chunk[0], chunk[1]);
                    } else {
                        supports.put(parts, "");
                    }
                }
                break;
            case RPL_MOTDSTART:
            case ERR_NOMOTD:
                motd.clear();
                break;
            case RPL_MOTD:
                motd.add(event.getMessage());
                break;
        }
    }

    public String getYourHost() {
        return yourHost;
    }

    public String getCreated() {
        return created;
    }

    public Map<String, String> getSupports() {
        return supports;
    }

    public String getSupport(String key) {
        return supports.get(key);
    }
    
    public boolean isPrefixed(String nick) {
        if (getSupport("PREFIX") == null) {
            return false;
        }
        
        String[] parts = getSupport("PREFIX").split("[)]");
        String keys = parts[1];
        
        return keys.contains(nick.substring(0, 1));
    }
    
    public String getModeByPrefix(String prefix) {
        if (getSupport("PREFIX") == null) {
            return null;
        }
        
        String[] parts = getSupport("PREFIX").split("[)]");
        String values = parts[0].replace("(", "");
        String keys = parts[1];
        
        int index = keys.indexOf(prefix.substring(0, 1));
        if (index == -1) {
            return null;
        }
        
        return String.valueOf(values.charAt(index));
    }

    public boolean hasMOTD() {
        return !motd.isEmpty();
    }

    public List<String> getMOTD() {
        return new ArrayList<String>(motd);
    }
}
