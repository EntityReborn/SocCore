/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import com.entityreborn.socbot.Channel;
import com.entityreborn.socbot.Packet;
import com.entityreborn.socbot.Target;
import com.entityreborn.socbot.User;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class JoinEvent extends TargetedEvent {
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    
    public JoinEvent(Packet p) {
        super(p);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public Channel getChannel() {
        // Some servers apparently send the channel name in the message
        // instead of as an arg.
        if (getTarget() != null) {
            return (Channel)getTarget();
        } else {
            return getBot().getChannel(getMessage());
        }
    }
    
    public User getUser() {
        Target targ = getSender();
        
        if (targ instanceof User) {
            return (User)targ;
        }
        
        return null;
    }
}
