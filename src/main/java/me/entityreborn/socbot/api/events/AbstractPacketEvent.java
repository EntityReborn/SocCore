/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.api.Packet;
import me.entityreborn.socbot.api.SocBot;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class AbstractPacketEvent extends AbstractEvent {
    Packet packet;
    
    private static final HandlerList handlers = new HandlerList(AbstractEvent.getHandlerList());
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public AbstractPacketEvent(Packet p) {
        packet = p;
    }

    public SocBot getBot() {
        return packet.getBot();
    }

    public Packet getPacket() {
        return packet;
    }
    
    public String getMessage() {
        return packet.getMessage();
    }
    
    public String getSender() {
        return packet.getSender();
    }
}
