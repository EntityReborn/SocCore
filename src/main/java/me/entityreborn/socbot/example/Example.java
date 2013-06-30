/*
 * The MIT License
 *
 * Copyright 2012 Jason Unger <entityreborn@gmail.com>.
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
package me.entityreborn.socbot.example;

import me.entityreborn.socbot.api.SocBot;
import me.entityreborn.socbot.api.events.LineSendEvent;
import me.entityreborn.socbot.api.events.PacketReceivedEvent;
import me.entityreborn.socbot.api.events.PrivmsgEvent;
import me.entityreborn.socbot.api.events.WelcomeEvent;
import me.entityreborn.socbot.core.Core;
import me.entityreborn.socbot.events.EventHandler;
import me.entityreborn.socbot.events.EventManager;
import me.entityreborn.socbot.events.Listener;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Example implements Listener {
    public static void main(String[] args) throws Exception {
        Core bot = new Core();
        
        EventManager.registerEvents(new Example(), bot);

        bot.setNickname("Testing");
        bot.connect("127.0.0.1");
    }
    
    public void debug(String s) {
        System.out.println(s);
    }
    
    @EventHandler
    public void handleLineIn(PacketReceivedEvent e) {
        debug(">>> " + e.getPacket().getOriginalLine());
    }
    
    @EventHandler
    public void handlePrivMsg(PrivmsgEvent e) {
        debug(e.getSender() + " said '" + e.getMessage() + "' to " + e.getTarget());
        
        if (e.getMessage().toLowerCase().startsWith("^ping")) {
            e.getTarget().sendMsg("Pong!");
        }
    }
    
    @EventHandler
    public void handleWelcomed(WelcomeEvent e) {
        SocBot bot = e.getBot();
        bot.join("#testing");
    }
    
    @EventHandler
    public void handleLineOut(LineSendEvent e) {
        debug("<<< " + e.getLine());
    }
}
