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
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;
import me.entityreborn.socbot.core.Core;
import static org.mockito.Mockito.*;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class TestUtils {
    static Socket sock;
    static SocketFactory sockFactory;
    static List<String> sent;
    
    public static List<String> getSent() {
        return sent;
    }
    
    public static Socket getSocket() {
        return sock;
    }
    
    public static SocketFactory getSockFactory() {
        return sockFactory;
    }

    public static Core init(ByteArrayInputStream input, 
            ByteArrayOutputStream output, Config conf) throws Exception {
        Core bot = new Core() {
            @Override
            public void sendLine(String line) {
                sent.add(line);
                super.sendLine(line);
            }
        };

        sent = new ArrayList<String>();

        bot.setNickname(conf.getNick());

        sock = mock(Socket.class);
        when(sock.isConnected()).thenReturn(true);
        when(sock.getInputStream()).thenReturn(input);
        when(sock.getOutputStream()).thenReturn(output);

        sockFactory = mock(SocketFactory.class);
        InetAddress addr = InetAddress.getByName(conf.getServer());
        when(sockFactory.createSocket(conf.getServer(), conf.getPort(), addr, 0)).thenReturn(sock);
        return bot;
    }

    public static byte[] getBytes(String... strings) {
        String out = "";
        
        for (String s : strings) {
            if (!s.endsWith("\r\n")) {
                s += "\r\n";
            }
            out += s;
        }
        
        return out.getBytes();
    }
    
    
    public static class Config{

        public String getNick() {
            return "tester";
        }

        public int getPort() {
            return 6667;
        }

        public String getServer() {
            return "localhost";
        }
    }
}
