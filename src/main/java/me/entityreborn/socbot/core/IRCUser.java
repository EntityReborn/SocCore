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

import java.util.Set;
import me.entityreborn.socbot.api.Channel;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.events.Listener;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class IRCUser extends IRCTarget implements User, Listener {

    private String nick;
    private String lastNick;
    private String hostmask;

    public IRCUser(String userline, Core b) {
        super(b);
        
        nick = userline;

        if (userline.contains("!")) {
            String[] split = userline.split("!", 2);
            nick = split[0];
            hostmask = split[1];
        }
        
        lastNick = nick;
    }

    public Set<Channel> getChannels() {
        return bot.getUserChannelMap().getChannels(this);
    }

    @Override
    public String toString() {
        return nick;
    }

    public String getName() {
        return nick;
    }
    
    public String getLastNick() {
        return lastNick;
    }

    public String getHostmask() {
        return hostmask;
    }

    public void setName(String n) {
        lastNick = nick;
        nick = n;
    }

    public void setHostmask(String h) {
        hostmask = h;
    }
}
