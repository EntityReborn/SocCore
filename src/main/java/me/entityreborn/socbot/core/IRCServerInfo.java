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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.entityreborn.socbot.api.ServerInfo;
import me.entityreborn.socbot.api.events.NumericEvent;
import me.entityreborn.socbot.events.EventHandler;
import me.entityreborn.socbot.events.EventManager;
import me.entityreborn.socbot.events.Listener;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class IRCServerInfo implements Listener, ServerInfo {

    String yourHost;
    String created;
    Map<String, String> supports;
    List<String> motd;

    public IRCServerInfo() {
        yourHost = "";
        created = "";
        supports = new HashMap<String, String>();
        motd = new ArrayList<String>();

        EventManager.registerEvents(this, this);
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

    public boolean hasMOTD() {
        return !motd.isEmpty();
    }

    public List<String> getMOTD() {
        return new ArrayList<String>(motd);
    }
}
