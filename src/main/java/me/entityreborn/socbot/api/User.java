/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.Set;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface User extends Target {

    public String getHostmask();
    
    public String getLastNick();

    public Set<Channel> getChannels();
}
