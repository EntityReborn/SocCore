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
package me.entityreborn.socbot.api.events;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import me.entityreborn.socbot.api.Channel;
import me.entityreborn.socbot.api.Numerics.Numeric;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.core.Core;
import me.entityreborn.socbot.events.EventHandler;
import me.entityreborn.socbot.events.EventManager;
import me.entityreborn.socbot.events.Listener;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class TestEventParsing implements Listener {

    AbstractEvent event;
    List<AbstractEvent> events;
    Core bot;
    TestUtils.Config conf;

    @EventHandler
    public void handlePacket(AbstractEvent e) {
        event = e;
        events.add(e);
    }

    @BeforeMethod
    public void before() throws Exception {
        conf = new TestUtils.Config();
        
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        bot = TestUtils.init(input, output, conf);
        events = new ArrayList<AbstractEvent>();
        
        EventManager.registerEvents(this, bot);
    }

    @AfterMethod
    public void after() {
        event = null;
        events = new ArrayList<AbstractEvent>();
    }

    @Test
    public void testWelcome() {
        bot.handleLine(":localhost 001 " + conf.getNick()
                + " :Welcome to the LocalHost IRC Network "
                + conf.getNick() + "!SocPuppet@localhost.com");

        assertTrue(event instanceof WelcomeEvent);
        WelcomeEvent we = (WelcomeEvent) event;

        assertEquals(we.getServerName(), "localhost");
        assertEquals(we.getMessage(), "Welcome to the LocalHost IRC Network "
                + we.getBot().getNickname() + "!SocPuppet@localhost.com");
        assertEquals(we.getPacket().getCommand(), Numeric.RPL_WELCOME.name());
        assertEquals(we.getPacket().getNumeric(), Numeric.RPL_WELCOME);
    }

    @Test
    public void testModeChange() {
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        bot.handleLine(":localhost MODE " + conf.getNick() + " +i");

        assertTrue(event instanceof ModeChangeEvent);
        ModeChangeEvent we = (ModeChangeEvent) event;

        assertEquals(we.getSender().getName(), "localhost");
        assertEquals(we.getTarget().getName(), conf.getNick());

        assertEquals(we.getAddedModes().length(), 1);
        assertTrue(we.getAddedModes().contains("i"));

        assertTrue(we.getRemovedModes().isEmpty());
        
        after();
        
        bot.handleLine(":localhost MODE #testing +o");

        assertTrue(event instanceof ModeChangeEvent);
        we = (ModeChangeEvent) event;

        assertEquals(we.getSender().getName(), "localhost");
        assertEquals(we.getTarget().getName(), "#testing");

        assertEquals(we.getAddedModes().length(), 1);
        assertTrue(we.getAddedModes().contains("o"));

        assertTrue(we.getRemovedModes().isEmpty());
        
        after();
        
        bot.handleLine(":" + conf.getNick() + "!test@test.com JOIN :#testing");
        bot.handleLine(":localhost MODE #testing +o " + conf.getNick());

        assertTrue(event instanceof ChannelUserModeChangeEvent);
        ChannelUserModeChangeEvent e = (ChannelUserModeChangeEvent) event;

        assertEquals(e.getSender().getName(), "localhost");
        assertEquals(e.getChannel().getName(), "#testing");

        assertEquals(e.getAddedModes().size(), 1);
        assertTrue(e.getChannel().getUserModes().containsKey(bot.getUser(conf.getNick())));
        assertTrue(e.getChannel().getUserModes(bot.getUser(conf.getNick())).contains("o"));

        assertTrue(e.getRemovedModes().isEmpty());
    }

    @Test
    public void testNotice() {
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        bot.handleLine(":tester!test@test.com NOTICE #testing :Test!");

        assertTrue(event instanceof NoticeEvent);
        NoticeEvent e = (NoticeEvent) event;

        assertEquals(e.getMessage(), "Test!");
        assertEquals(e.getSender().getName(), "tester");
        assertEquals(e.getTarget().getName(), "#testing");
    }

    @Test
    public void testCTCPReply() {
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        bot.handleLine(":tester!test@test.com NOTICE #testing :\01PING Test!\01");

        assertTrue(event instanceof CTCPReplyEvent);
        CTCPReplyEvent e = (CTCPReplyEvent) event;

        assertEquals(e.getMessage(), "Test!");
        assertEquals(e.getType(), "PING");
        assertEquals(e.getSender().getName(), "tester");
        assertEquals(e.getTarget().getName(), "#testing");
    }

    @Test
    public void testPrivmsg() {
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        bot.handleLine(":tester!test@test.com PRIVMSG #testing :Test!");

        assertTrue(event instanceof PrivmsgEvent);
        PrivmsgEvent e = (PrivmsgEvent) event;

        assertEquals(e.getMessage(), "Test!");
        assertEquals(e.getSender().getName(), "tester");
        assertEquals(e.getTarget().getName(), "#testing");
    }

    @Test
    public void testCTCP() {
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        bot.handleLine(":tester!test@test.com PRIVMSG #testing :\01ACTION Test!\01");

        assertTrue(event instanceof CTCPEvent);
        CTCPEvent e = (CTCPEvent) event;

        assertEquals(e.getMessage(), "Test!");
        assertEquals(e.getType(), "ACTION");
        assertEquals(e.getSender().getName(), "tester");
        assertEquals(e.getTarget().getName(), "#testing");
    }

    @Test
    public void testJoin() {
        // Test it with the channel given as a message
        bot.handleLine(":tester!test@test.com JOIN :#testing");

        assertTrue(event instanceof JoinEvent);
        JoinEvent e = (JoinEvent) event;

        assertEquals(e.getChannel().getName(), "#testing");
        assertEquals(e.getSender().getName(), "tester");

        // Test it with the channel given as an arg
        bot.handleLine(":tester!test@test.com JOIN #testing");

        assertTrue(event instanceof JoinEvent);
        e = (JoinEvent) event;

        assertEquals(e.getChannel().getName(), "#testing");
        assertEquals(e.getSender().getName(), "tester");
    }

    @Test
    public void testPart() {
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        // Test it with message
        bot.handleLine(":tester!test@test.com PART #testing :Bye!");

        assertTrue(event instanceof PartEvent);
        PartEvent e = (PartEvent) event;

        assertEquals(e.getChannel().getName(), "#testing");
        assertEquals(e.getSender().getName(), "tester");
        assertEquals(e.getPartMessage(), "Bye!");

        // Test it without message
        bot.handleLine(":tester!test@test.com PART #testing");

        assertTrue(event instanceof PartEvent);
        e = (PartEvent) event;

        assertEquals(e.getChannel().getName(), "#testing");
        assertEquals(e.getSender().getName(), "tester");
        assertEquals(e.getPartMessage(), "");
    }

    @Test
    public void testQuit() {
        // Test it with message
        bot.handleLine(":tester!test@test.com QUIT :Bye!");

        assertTrue(event instanceof QuitEvent);
        QuitEvent e = (QuitEvent) event;

        assertEquals(e.getUser().getName(), "tester");
        assertEquals(e.getQuitMessage(), "Bye!");

        // Test it without message
        bot.handleLine(":tester!test@test.com QUIT #testing");

        assertTrue(event instanceof QuitEvent);
        e = (QuitEvent) event;

        assertEquals(e.getUser().getName(), "tester");
        assertEquals(e.getQuitMessage(), "");
    }

    @Test
    public void testKick() {
        // Test it with message
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        bot.handleLine(":kicker!test@test.com KICK #testing kickee :Bye!");

        assertTrue(event instanceof KickEvent);
        KickEvent e = (KickEvent) event;

        assertEquals(e.getKicker().getName(), "kicker");
        assertEquals(e.getChannel().getName(), "#testing");
        assertEquals(e.getKicked().getName(), "kickee");
        assertEquals(e.getKickMessage(), "Bye!");

        // Test it without message
        bot.handleLine(":tester!test@test.com JOIN :#testing");
        bot.handleLine(":kicker!test@test.com KICK #testing kickee");

        assertTrue(event instanceof KickEvent);
        e = (KickEvent) event;

        assertEquals(e.getKicker().getName(), "kicker");
        assertEquals(e.getChannel().getName(), "#testing");
        assertEquals(e.getKicked().getName(), "kickee");
        assertEquals(e.getKickMessage(), "");
    }
    
    @Test
    public void testNamList() {
        // Test it with message
        bot.handleLine(":localhost 005 Testing PREFIX=(qaohv)~&@%+ :are supported by this server");
        bot.handleLine(":Testing!test@test.com JOIN :#testing");
        bot.handleLine(":localhost 353 Testing = #testing :Testing @__import__");

        assertTrue(event instanceof NumericEvent);
        NumericEvent e = (NumericEvent) event;
        
        assertEquals(e.getNumeric(), Numeric.RPL_NAMREPLY);
        assertEquals(e.getSender().getName(), "localhost");
        assertEquals(e.getTarget().getName(), "Testing");
        
        List<String> args = e.getPacket().getArgs();
        
        Channel chan = bot.trackChannel(args.get(args.size() - 1));
        User u = bot.getUser("__import__", true);
        
        assertTrue(chan.getUserModes().containsKey(u));
        assertTrue(chan.getUserModes(u).contains("o"));
    }
}
