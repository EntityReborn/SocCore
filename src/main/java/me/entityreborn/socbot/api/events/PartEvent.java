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
public class PartEvent extends TargetedEvent {
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    private Channel channel;
    
    public PartEvent(Packet p) {
        super(p);
        
        channel = (Channel)getTarget();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public User getUser() {
        Target target = getSender();
        
        if (target instanceof User) {
            return (User)target;
        }
        
        return null;
    }
    
    public String getPartMessage() {
        return packet.getMessage();
    }
}
