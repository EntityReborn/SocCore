/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.events.HandlerList;
import me.entityreborn.socbot.api.Channel;
import me.entityreborn.socbot.api.Packet;
import me.entityreborn.socbot.api.Target;
import me.entityreborn.socbot.api.User;

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
        Target target = getSender();
        
        if (target instanceof User) {
            return (User)target;
        }
        
        return null;
    }
}
