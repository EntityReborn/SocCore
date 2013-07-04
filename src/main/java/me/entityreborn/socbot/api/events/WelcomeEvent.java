/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.api.Packet;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class WelcomeEvent extends TargetedEvent {

    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    private String srvName;
    
    public WelcomeEvent(Packet p) {
        super(p);

        srvName = p.getSender();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getServerName() {
        return srvName;
    }
}
