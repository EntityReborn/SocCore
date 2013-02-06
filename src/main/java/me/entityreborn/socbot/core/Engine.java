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

import me.entityreborn.socbot.events.EventManager;
import me.entityreborn.socbot.api.Connection;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import me.entityreborn.socbot.api.events.ConnectedEvent;
import me.entityreborn.socbot.api.events.ConnectingEvent;
import me.entityreborn.socbot.api.events.LineSendEvent;

/**
 * Provide low level connection and lifetime management for an IRC session.
 *
 * Some of the /ideas/ behind how this works taken from PircBotX. No actual
 * PircBotX code used.
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class Engine implements Connection {

    private InputThread in;
    private OutputThread out;
    private Socket sock;
    private SocketFactory sockfactory;
    private String nickname;
    private String username = "SocPuppet";
    private String realname = "SocPuppet";
    private boolean isConnected = false;
    private boolean isConnecting = false;
    private static int enginecount = 1;

    protected static int getCountAndIncrement() {
        return enginecount++;
    }

    public void disconnect() {
        // Shut down *everything*
        isConnected = false;

        try {
            in.interrupt();
            out.interrupt();
        } catch (Exception e) {
            handleException(e);
        }

        if (!sock.isClosed()) {
            try {
                sock.shutdownInput();
                sock.shutdownOutput();
                sock.close();
            } catch (IOException e) {
                handleException(e);
            }
        }
    }

    public void connect(String server) {
        connect(server, 6667, null, null);
    }

    public void connect(String server, int port) {
        connect(server, port, null, null);
    }

    public void connect(String server, int port, String password) {
        connect(server, port, password, null);
    }

    public void connect(String server, int port, String password, SocketFactory factory) {
        sockfactory = factory;
        isConnecting = true;
        sock = null;
        
        EventManager.callEvent(new ConnectingEvent(server, port), this);
        
        try {
            for (InetAddress addr : InetAddress.getAllByName(server)) {
                try {
                    if (factory != null) {
                        sock = sockfactory.createSocket(server, port, addr, 0);
                    } else {
                        sock = new Socket(server, port, addr, 0);
                    }
                    
                    break;
                } catch (Throwable t) {
                    handleException(t);
                }
            }
            
            if (sock == null) {
                throw new RuntimeException("Could not connect to a server.");
            }

            in = new InputThread(sock.getInputStream(), this);
            out = new OutputThread(sock.getOutputStream(), this);

            int count = getCountAndIncrement();
            in.setName("input-" + count);
            out.setName("output-" + count);
            
            out.start();
            
            EventManager.callEvent(new ConnectedEvent(server, port), this);
            
            if (password != null && !password.trim().equals("")) {
                sendLine("PASS " + password.trim());
            }

            sendLine("NICK " + getNickname());
            sendLine("USER " + getUsername() + " 8 * :" + getRealname());
            
            in.start();
            
            isConnecting = false;
            isConnected = true;
        } catch (Throwable t) {
            handleException(t);
        }
    }

    public abstract void handleException(Throwable t);

    public abstract void handleLine(String line);

    public void sendLine(String line) {
        EventManager.callEvent(new LineSendEvent(line), this);
        out.send(line);
    }

    public void sendLineNow(String line) {
        EventManager.callEvent(new LineSendEvent(line, true), this);
        out.sendNow(line);
    }

    public void sendPing() {
        sendLine("PING " + (System.currentTimeMillis() / 1000));
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isConnecting() {
        return isConnecting;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        if (isConnected() || isConnecting()) {
            sendLine("NICK " + nickname);
        } else {
            this.nickname = nickname;
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        if (!isConnected()) {
            this.username = username;
        }
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        if (!isConnected()) {
            this.realname = realname;
        }
    }
}
