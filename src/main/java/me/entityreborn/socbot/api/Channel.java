/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface Channel extends Target {

    public String getTopic();

    public void setTopic(String topic);
    
    public Set<User> getUsers();

    public Map<User, String> getUserModes();

    public String getUserModes(User user);

    public void addUserMode(User user, String mode);

    public void remUserMode(User user, String mode);
    
    public boolean userHasMode(User user, String mode);
    
    public void trackUser(User user);
    
    public void untrackUser(User user);
}
