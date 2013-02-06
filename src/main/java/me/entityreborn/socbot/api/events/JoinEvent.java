/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.events.HandlerList;
import me.entityreborn.socbot.api.Channel;
import me.entityreborn.socbot.api.Packet;
import me.entityreborn.socbot.api.User;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class JoinEvent extends TargetedEvent {
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    private Channel channel;
    
    public JoinEvent(Packet p) {
        super(p);
        
        // Some servers apparently send the channel name in the message
        // instead of as an arg.
        if (getTarget() != null) {
            channel = (Channel)getTarget();
        } else {
            channel = getBot().getChannel(getMessage());
        }
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public User getUser() {
        return getSender();
    }
}
