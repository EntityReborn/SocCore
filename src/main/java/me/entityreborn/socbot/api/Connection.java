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
package me.entityreborn.socbot.api;

import javax.net.SocketFactory;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface Connection {

    public void connect(String server);

    public void connect(String server, int port);

    public void connect(String server, int port, String password);
    
    public void connect(String server, int port, String password, SocketFactory factory);

    public void disconnect();

    public String getNickname();

    public String getRealname();

    public String getUsername();

    public boolean isConnected();
    
    public boolean isConnecting();

    public void sendLine(String line);

    public void sendLineNow(String line);
    
    public void sendPing();

    public void setNickname(String nickname);

    public void setRealname(String realname);

    public void setUsername(String username);
}
