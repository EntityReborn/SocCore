/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import com.entityreborn.socbot.Packet;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class NickInUseEvent extends AbstractPacketEvent {
    private static final HandlerList handlers = new HandlerList(AbstractPacketEvent.getHandlerList());
    
    public NickInUseEvent(Packet p) {
        super(p);
    }
    
    public String getNick() {
        if (packet.getArgs().size() != 1) {
            return "<unknown>";
        }
        
        String nick = packet.getArgs().get(0);
        return nick;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
