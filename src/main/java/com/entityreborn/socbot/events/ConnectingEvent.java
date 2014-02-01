/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import com.entityreborn.socbot.SocBot;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class ConnectingEvent extends AbstractEvent {
    private static final HandlerList handlers = new HandlerList(AbstractEvent.getHandlerList());
    private final String server;
    private final int port;
    
    public ConnectingEvent(SocBot bot, String server, int port) {
        super(bot);
        
        this.server = server;
        this.port = port;
    }
    
    public String getServer() {
        return server;
    }
    
    public int getPort() {
        return port;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
