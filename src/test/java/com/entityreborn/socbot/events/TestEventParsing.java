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

import com.entityreborn.socbot.Channel;
import com.entityreborn.socbot.Numerics.BuiltinNumeric;
import com.entityreborn.socbot.SocBot;
import com.entityreborn.socbot.User;
import com.entityreborn.socbot.eventsystem.EventHandler;
import com.entityreborn.socbot.eventsystem.EventManager;
import com.entityreborn.socbot.eventsystem.Listener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class TestEventParsing implements Listener {

    AbstractEvent event;
    List<AbstractEvent> events;
    SocBot bot;
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
    public void testUserEquality() {
        assertEquals(new User("test!test@test.com", null), new User("test!test@test.com", null));
    }
    
    @Test
    public void testGetRandomUser() {
        bot.getUser("someoneunknown").sendMsg("hi");
        assertTrue(!TestUtils.getSent().isEmpty());
    }
    
    @Test
    public void testChannelEquality() {
        assertEquals(new Channel("#test", null), new Channel("#test", null));
    }
    
    @Test
    public void testNickChange() {
        bot.handleLine(":localhost 001 " + conf.getNick()
                + " :Welcome to the LocalHost IRC Network "
                + conf.getNick() + "__!SocPuppet@localhost.com");
        assertEquals(bot.getNickname(), conf.getNick() + "__");
    }
    
    @Test
    public void testAll() {
        // This test brought to you by CyaNox :)
        // Actual log from a server, cleansed for public consumpion, except nicks.
        bot.handleLine(":localhost NOTICE AUTH :*** Looking up your hostname...");
        bot.handleLine(":localhost NOTICE AUTH :*** Checking Ident");
        bot.handleLine(":localhost NOTICE AUTH :*** No Ident response");
        bot.handleLine(":localhost NOTICE AUTH :*** Found your hostname");
        bot.handleLine(":localhost 001 QT :Welcome to the localhost network QT!~QT@localhost.com");
        bot.handleLine(":localhost 002 QT :Your host is localhost[@0.0.0.0], running UltimateIRCd(Tsunami)-3.0(02).m5");
        bot.handleLine(":localhost 003 QT :This server was last (re)started on Mon Oct 4 2010 at 15:19:42 CEST and is located in SomePlace, Somewhere");
        bot.handleLine(":localhost 004 QT localhost UltimateIRCd(Tsunami)-3.0(02).m5 adhioprxDOPRSWZ acehimnopqstvAKMNORS");
        bot.handleLine(":localhost 005 QT SAFELIST SILENCE KNOCK FNC WATCH=128 CHANLIMIT=#&:30 MAXLIST=be:60 NICKLEN=30 TOPICLEN=307 KICKLEN=307 CHANNELLEN=32 :are available on this server");
        bot.handleLine(":localhost 005 QT EXCEPTS=e CHANTYPES=#& PREFIX=(aohv)!@%+ CHANMODES=be,k,l,cimnpqstAKMNORS STATUSMSG=!@%+ NETWORK=localhost CASEMAPPING=ascii STD=i-d :are available on this server");
        bot.handleLine(":localhost 251 QT :There are 2 users and 3206 invisible on 1 servers");
        bot.handleLine(":localhost 252 QT 36 :IRC Operators online");
        bot.handleLine(":localhost 253 QT 6570 :unknown connection(s)");
        bot.handleLine(":localhost 254 QT 1563 :channels formed");
        bot.handleLine(":localhost 265 QT :Current local users: 3208 Max: 12760");
        bot.handleLine(":localhost 266 QT :Current global users: 3208 Max: 12760");
        bot.handleLine(":localhost NOTICE QT :*** Notice -- motd was last changed at 11/3/2010 13:37");
        bot.handleLine(":localhost 375 QT :- localhost Message of the Day - ");
        bot.handleLine(":localhost 376 QT :End of /MOTD.");
        bot.handleLine(":LagServ!services@localhost PRIVMSG QT :\u0001LAG\u0001");
        bot.handleLine(":PingServ!services@localhost PRIVMSG QT :\u0001PING BottlerTest PleaseIgnore\u0001");
        bot.handleLine(":QT MODE QT :+r");
        bot.handleLine(":QT!~QT@6a63aef.540cf85.localhost.com JOIN :#SomeChannel");
        bot.handleLine(":localhost 332 QT #SomeChannel :Welcome to the localhost test channel!");
        bot.handleLine(":localhost 333 QT #SomeChannel Sumguy 1382810142");
        bot.handleLine(":localhost 353 QT = #SomeChannel :QT Ab +Ceds Agrsv asdf57 sag45 sve375 %Sfves asfveve asfv4r4i svrv4443 !Sves @KJHkfe gdsv47 %dvs_ !zxe333 %Jslkfevi sdfdsasdhf !Lnkdvue` Goober @SDKhje jsvnu %SJHduv @sx sdevsev +sfvrr ");
        bot.handleLine(":localhost 366 QT #SomeChannel :End of /NAMES list.");
        bot.handleLine(":ChanServ!services@localhost MODE #SomeChannel +h QT");
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
        assertEquals(we.getPacket().getCommand(), BuiltinNumeric.RPL_WELCOME.name());
        assertEquals(we.getPacket().getNumeric(), BuiltinNumeric.RPL_WELCOME);
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
        
        bot.handleLine(":tester!test@test.com NICK Bob");
        bot.handleLine(":Bob!test@test.com PRIVMSG #testing :Test!");

        assertTrue(event instanceof PrivmsgEvent);
        e = (PrivmsgEvent) event;

        assertEquals(e.getMessage(), "Test!");
        assertEquals(e.getSender().getName(), "Bob");
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
        assertEquals(e.getBot().getChannels().size(), 1);

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
        bot.handleLine(":kicker!test@test.com JOIN :#testing");
        bot.handleLine(":kickee!test@test.com JOIN :#testing");
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
        
        assertEquals(e.getNumeric(), BuiltinNumeric.RPL_NAMREPLY);
        assertEquals(e.getSender().getName(), "localhost");
        assertEquals(e.getTarget().getName(), "Testing");
        
        List<String> args = e.getPacket().getArgs();
        
        Channel chan = bot.trackChannel(args.get(args.size() - 1));
        User u = bot.getUser("__import__", true);
        
        assertTrue(chan.getUserModes().containsKey(u));
        assertTrue(chan.getUserModes(u).contains("o"));
    }
}
