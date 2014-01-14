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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Packet {
    private final String originalLine;
    private String sender;
    private String message = "";
    private List<String> args;
    private String command;
    private Numeric numeric;
    private final Engine engine;
    
    public Packet(String l, Engine e) {
        engine = e;
        originalLine = l;
        
        parse();
    }
    
    public SocBot getBot() {
        return (SocBot)engine;
    }
    
    protected final void parse() {
        String line = originalLine;
        
        // Get sender if given
        if (line.startsWith(":")) {
            String[] split = line.split(" ", 2);
            sender = split[0].substring(1);
            line = split[1];
        }
        
        // Split message from the command and args
        if (line.contains(":")) {
            String[] split = line.split(":", 2);
            message = split[1];
            line = split[0];
        }
        
        // Get the args then split the command from the args
        args = new ArrayList<String>(Arrays.asList(line.split(" ")));
        command = args.remove(0);
        
        // Grab the numeric if one is available
        numeric = Numerics.fromString(command);
        
        // If there is a numeric, and it is a known one, use it's name instead
        // if an integer for the command
        if (numeric != Numeric.UNKNOWN) {
            command = numeric.name();
        }
    }
    
    @Override
    public String toString() {
        return originalLine;
    }
    
    public String getOriginalLine() {
        return originalLine;
    }
    
    public String getSender() {
        return sender;
    }
    
    public String getMessage() {
        return message;
    }
    
    public List<String> getArgs() {
        // Send a copy, in case listeners
        // try .remove() or somesuch. They can modify it
        // explicitly thru setArgs
        return new ArrayList<String>(args);
    }
    
    public String getCommand() {
        return command;
    }
    
    public Numeric getNumeric() {
        return numeric;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setArgs(List<String> args) {
        this.args = args;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public void setNumeric(Numeric numeric) {
        this.numeric = numeric;
        
        // Keep the command synched with the numeric if it's 
        // a known numeric
        if (numeric != Numeric.UNKNOWN) {
            command = numeric.name();
        }
    }
}
