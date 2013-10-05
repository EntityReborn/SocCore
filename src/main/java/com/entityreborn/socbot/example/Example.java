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
package com.entityreborn.socbot.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.entityreborn.socbot.events.JoinEvent;
import com.entityreborn.socbot.events.LineSendEvent;
import com.entityreborn.socbot.events.PacketReceivedEvent;
import com.entityreborn.socbot.events.PartEvent;
import com.entityreborn.socbot.events.PrivmsgEvent;
import com.entityreborn.socbot.events.WelcomeEvent;
import com.entityreborn.socbot.Channel;
import com.entityreborn.socbot.SocBot;
import com.entityreborn.socbot.User;
import com.entityreborn.socbot.eventsystem.EventHandler;
import com.entityreborn.socbot.eventsystem.EventManager;
import com.entityreborn.socbot.eventsystem.Listener;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Example implements Listener {
    public static void main(String[] args) throws Exception {
        SocBot bot = new SocBot("test");
        
        EventManager.registerEvents(new Example(), bot);

        bot.setNickname("Testing123");
        bot.connect("irc.esper.net");
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
        debug(e.getUser().getName() + " said '" + e.getMessage() + "' to " + e.getTarget());
        
        List<String> args = new ArrayList<String>(Arrays.asList(e.getMessage().split(" ")));
        if (args.size() < 1) {
            return;
        }
        
        String command = args.remove(0);
        
        if (command.startsWith("^ping")) {
            e.getTarget().sendMsg("Pong!");
            
            return;
        } else if (command.startsWith("^data")) {
            Channel chan = (Channel)e.getTarget();
            
            for (User user : chan.getUsers()) {
                e.getTarget().sendMsg(user.getName() + " " + chan.getUserModes(user));
            }
            
            return;
        } else if (command.startsWith("^quit")) {
            e.getBot().quit();
            
            return;
        } else if (command.startsWith("^disconnect")) {
            e.getBot().disconnect(true);
            
            return;
        } else if (command.startsWith("^isop")) {
            if (args.isEmpty()) {
                e.getTarget().sendMsg("^isop <name>");
                return;
            } else if (e.getTarget() instanceof User) {
                e.getTarget().sendMsg("Not in a channel");
                return;
            }
            
            Channel chan = (Channel)e.getTarget();
            boolean isop = chan.userHasMode(e.getBot().getUser(args.get(0)), "o");
            
            if (isop) {
                e.getTarget().sendMsg("Yup");
            } else {
                e.getTarget().sendMsg("Nerp");
            }
            
            return;
        }
    }
    
    @EventHandler
    public void handleWelcomed(WelcomeEvent e) {
        SocBot bot = e.getBot();
        bot.join("#testing");
    }
    
    @EventHandler
    public void handleJoin(JoinEvent e) {
        e.getChannel().sendMsg("Howdy!");
    }
    
    @EventHandler
    public void handlePart(PartEvent e) {
        e.getChannel().sendMsg("Bye!");
    }
    
    @EventHandler
    public void handleLineOut(LineSendEvent e) {
        debug("<<< " + e.getLine());
    }
}
