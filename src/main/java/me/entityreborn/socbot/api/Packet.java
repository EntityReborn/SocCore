/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.List;
import me.entityreborn.socbot.api.Numerics.Numeric;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface Packet {
    
    public SocBot getBot();

    public List<String> getArgs();

    public String getCommand();

    public String getMessage();

    public Numeric getNumeric();

    public String getOriginalLine();

    public String getSender();

    public void setArgs(List<String> args);

    public void setCommand(String command);

    public void setMessage(String message);

    public void setNumeric(Numeric numeric);

    public void setSender(String sender);
    
}
