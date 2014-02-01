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
package com.entityreborn.socbot.events;

import com.entityreborn.socbot.Numerics.BuiltinNumeric;
import com.entityreborn.socbot.Packet;
import com.entityreborn.socbot.SocBot;
import com.entityreborn.socbot.eventsystem.EventHandler;
import com.entityreborn.socbot.eventsystem.EventManager;
import com.entityreborn.socbot.eventsystem.Listener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class TestEventManager implements Listener {

    List<AbstractPacketEvent> events;

    @EventHandler
    public void handlePacket(PacketReceivedEvent event) {
        events.add(event);
    }

    @EventHandler
    public void handlePacket(WelcomeEvent event) {
        events.add(event);
    }

    @Test
    public void testManager() throws Exception {
        TestUtils.Config conf = new TestUtils.Config();
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        SocBot bot = TestUtils.init(input, output, conf);
        
        EventManager.registerEvents(this, bot);
        
        cleanup();
        bot.handleLine(":localhost 001 " + conf.getNick()
                + " :Welcome to the LocalHost IRC Network "
                + conf.getNick() + "!SocPuppet@localhost.com");

        assertEquals(events.size(), 2);
        
        int welcome = 0;
        int packetreceived = 0;
        
        for (AbstractPacketEvent e : events) {
            if (e instanceof WelcomeEvent) {
                WelcomeEvent we = (WelcomeEvent) e;

                assertEquals(we.getServerName(), "localhost");
                assertEquals(we.getMessage(), "Welcome to the LocalHost IRC Network "
                        + we.getBot().getNickname() + "!SocPuppet@localhost.com");
                assertEquals(we.getPacket().getCommand(), BuiltinNumeric.RPL_WELCOME.name());
                assertEquals(we.getPacket().getNumeric(), BuiltinNumeric.RPL_WELCOME);
                
                welcome++;
                
            } else if (e instanceof PacketReceivedEvent) {
                Packet p = e.getPacket();
                String botname = p.getBot().getNickname();

                assertEquals(p.getOriginalLine(),
                        ":localhost 001 " + botname
                        + " :Welcome to the LocalHost IRC Network "
                        + botname + "!SocPuppet@localhost.com");
                assertEquals(p.getCommand(), BuiltinNumeric.RPL_WELCOME.name());
                assertEquals(p.getNumeric(), BuiltinNumeric.RPL_WELCOME);
                assertEquals(p.getSender(), "localhost");
                assertEquals(p.getMessage(), "Welcome to the LocalHost IRC Network "
                        + botname + "!SocPuppet@localhost.com");
                
                packetreceived++;
            }
        }
        
        assertEquals(welcome, 1);
        assertEquals(packetreceived, 1);
    }

    private void cleanup() {
        events = new ArrayList<AbstractPacketEvent>();
    }
}
