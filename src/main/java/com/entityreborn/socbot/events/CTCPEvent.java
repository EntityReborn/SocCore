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
public class CTCPEvent extends TargetedEvent {

    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    String type;

    public CTCPEvent(Packet p) {
        super(p);
        
        String message = p.getMessage().substring(1,
                p.getMessage().length() - 1);
        String[] parts = message.split(" ", 2);
        
        type = parts[0].toUpperCase();

        p.setMessage(parts[1]);
    }

    public String getType() {
        return type;
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
