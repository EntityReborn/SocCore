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

import me.entityreborn.socbot.api.SocBot;
import me.entityreborn.socbot.api.Target;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class IRCTarget implements Target {

    Core bot;
    String modes;

    public IRCTarget(Core b) {
        bot = b;
        modes = "";
    }

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }

    public SocBot getBot() {
        return bot;
    }

    public void sendMsg(String message) {
        bot.sendLine("PRIVMSG " + getName() + " :" + message);
    }

    public void sendCTCP(String type, String message) {
        sendMsg("\01" + type + " " + message + "\01");
    }

    public void sendNotice(String message) {
        bot.sendLine("NOTICE " + getName() + " :" + message);
    }

    public void sendCTCPReply(String type, String message) {
        sendNotice("\01" + type + " " + message + "\01");
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
