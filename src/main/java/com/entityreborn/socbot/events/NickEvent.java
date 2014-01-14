/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import com.entityreborn.socbot.Packet;
import com.entityreborn.socbot.Target;
import com.entityreborn.socbot.User;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class NickEvent extends TargetedEvent {
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    
    public NickEvent(Packet p) {
        super(p);
    }
    
    public String getNewNick() {
        return packet.getMessage();
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public User getUser() {
        Target target = getSender();
        
        if (target instanceof User) {
            return (User)target;
        }
        
        return null;
    }
}
