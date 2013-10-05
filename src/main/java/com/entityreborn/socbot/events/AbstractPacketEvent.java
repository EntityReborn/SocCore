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
public abstract class AbstractPacketEvent extends AbstractEvent {
    Packet packet;
    Target sender;
    
    private static final HandlerList handlers = new HandlerList(AbstractEvent.getHandlerList());
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public AbstractPacketEvent(Packet p) {
        super(p.getBot());
        
        packet = p;
        String name = p.getSender();
        
        if (name != null) {
            if (User.Util.isUser(name, p.getBot())) {
                sender = p.getBot().getUser(name, true);
            } else {
                sender = p.getBot().getChannel(name);
            }
        }
    }

    public Packet getPacket() {
        return packet;
    }
    
    public String getMessage() {
        return packet.getMessage();
    }
    
    public Target getSender() {
        return sender;
    }
}
