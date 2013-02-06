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
    
    SocBot getBot();

    List<String> getArgs();

    String getCommand();

    String getMessage();

    Numeric getNumeric();

    String getOriginalLine();

    User getSender();

    void setArgs(List<String> args);

    void setCommand(String command);

    void setMessage(String message);

    void setNumeric(Numeric numeric);

    void setSender(User sender);
    
}
