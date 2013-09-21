/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.api.Packet;
import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class PacketReceivedEvent extends AbstractPacketEvent {

    private static final HandlerList handlers = new HandlerList(AbstractPacketEvent.getHandlerList());

    public PacketReceivedEvent(Packet p) {
        super(p);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
