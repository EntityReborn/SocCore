/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.Set;
import me.entityreborn.socbot.api.config.ServerConfig;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface SocBot extends Connection {
    public String getVersion();
    
    public ServerConfig getBaseConfig();
    
    public Set<User> getUsers();
    
    public Set<Channel> getChannels();
    
    public User getUser(String nick);
    
    public Channel getChannel(String channel);
    
    public UserChannelMap getUserChannelMap();
    
    public ServerInfo getServerInfo();
}
