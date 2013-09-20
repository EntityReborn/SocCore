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
import java.net.InetAddress;
import java.util.List;
import me.entityreborn.socbot.api.events.TestUtils.Config;
import me.entityreborn.socbot.core.Core;
import me.entityreborn.socbot.events.Listener;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class TestConnect implements Listener {
    
    @Test
    public void testConnect() throws Exception {
        Config conf = new TestUtils.Config();
        ByteArrayInputStream input = new ByteArrayInputStream("PING :2B16CDCD".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Core bot = TestUtils.init(input, output, conf);

        bot.connect(conf.getServer(), conf.getPort(), null, TestUtils.getSockFactory());
        Thread.sleep(200); // Catch up with threaded events.
        
        InetAddress addr = InetAddress.getByName(conf.getServer());
        verify(TestUtils.getSockFactory()).createSocket(conf.getServer(), conf.getPort(), addr, 0);
        
        List<String> sent = TestUtils.getSent();
        
        assertEquals(sent.contains("NICK tester"), true);
        assertEquals(sent.contains("USER SocPuppet 8 * :SocPuppet"), true);
        assertEquals(sent.contains("PONG :2B16CDCD"), true);
    }
}
