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
package com.entityreborn.socbot;

import com.entityreborn.socbot.Numerics.Numeric;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

/**
 * Provide low level connection and lifetime management for an IRC session. <p>
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

    /**
     * Increment and return a counter for use in naming threads.
     *
     * @return id number
     */
    protected static int getCountAndIncrement() {
        return enginecount++;
    }

    public void disconnect(boolean clean) {
        if (!isConnected && !isConnecting) {
            return;
        }
        
        isConnected = false;

        try {
            if (in != null) in.interrupt();
            if (out != null) out.interrupt();
        } catch (Exception e) {
            handleException(e);
        }

        if (sock != null && !sock.isClosed()) {
            try {
                sock.shutdownInput();
                sock.shutdownOutput();
                sock.close();
            } catch (IOException e) {
                handleException(e);
            }
        }
        
        fireDisconnected(clean, this);
    }
    
    protected abstract void fireDisconnected(boolean wasClean, Engine e);
    protected abstract void fireConnecting(String server, int port, Engine e);
    protected abstract void fireConnected(String server, int port, Engine e);
    
    public void connect(String srvr) throws IOException {
        connect(srvr, 6667, "", null);
    }

    public void connect(String server, int port) throws IOException {
        connect(server, port, "", null);
    }

    public void connect(String server, int port, String password) throws IOException {
        connect(server, port, password, sockfactory);
    }

    public void connect(String server, int port, String password, SocketFactory factory) throws UnknownHostException, IOException {
        sockfactory = factory;

        isConnecting = true;
        sock = null;

        fireConnecting(server, port, this);

        if (factory != null) {
            try {
                for (InetAddress addr : InetAddress.getAllByName(server)) {
                    sock = sockfactory.createSocket(server, port, addr, 0);
                    break;
                }
            } catch (IOException e) {
                isConnecting = false;
                throw e;
            }
        } else {
            sock = new Socket(server, port, null, 0);
        }

        if (sock == null) {
            isConnecting = false;
            throw new RuntimeException("Could not connect to a server.");
        }

        in = new InputThread(sock.getInputStream(), this);
        out = new OutputThread(sock.getOutputStream(), this);

        int count = getCountAndIncrement();
        
        in.setName("input-" + count);
        out.setName("output-" + count);
        
        isConnected = true;
        
        out.start();

        fireConnected(server, port, this);

        if (password != null && !password.trim().equals("")) {
            sendLine("PASS " + password.trim());
        }

        sendLine("NICK " + getNickname());
        sendLine("USER " + getUsername() + " 8 * :" + getRealname());

        in.start();

        isConnecting = false;
    }

    /**
     * Exception callback for when something bad happens in the core.
     * <p>
     * This doesn't pertain to error numerics or the ERROR IRC message
     * sent from the server.
     * 
     * @param t the exception that was thrown.
     */
    public abstract void handleException(Throwable t);

    /**
     * Handle any input the bot receives.
     * <p>
     * This method will fire off appropriate events,
     * depending on the type of line.
     * <p>
     * Numeric lines will be sent to handleNumeric.
     * 
     * @param packet the packet the bot received.
     */
    public abstract void handlePacket(Packet packet);
    
    public final void handleLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        Packet packet = new Packet(line, this);
        
        if (packet.getNumeric() == Numeric.RPL_WELCOME) {
            // Update our nickname, incase the server changed it.
            // IE, if connecting to a bouncer.
            String[] args = packet.getMessage().split(" ");
            String nick = args[args.length - 1];
            nickname = nick.split("!")[0];
        }
        
        handlePacket(packet);
    }

    public abstract void fireSendLine(String line, Engine e);
    
    public void sendLine(String line) {
        if (out != null) {
            fireSendLine(line, this);
            
            out.send(line);
        }
    }
    
    public abstract void fireSendLineNow(String line, Engine e);

    public void sendLineNow(String line) {
        if (out != null) {
            fireSendLineNow(line, this);
            
            out.sendNow(line);
        }
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

    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        if (isConnected() || isConnecting()) {
            sendLine("NICK " + nickname);
        }
        
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        if (!isConnected()) {
            this.username = username;
        }
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        if (!isConnected()) {
            this.realname = realname;
        }
    }
}
