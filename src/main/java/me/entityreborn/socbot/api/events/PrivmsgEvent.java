/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.api.Packet;
import me.entityreborn.socbot.api.Target;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class PrivmsgEvent extends TargetedEvent {
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());

    public PrivmsgEvent(Packet p) {
        super(p);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
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
