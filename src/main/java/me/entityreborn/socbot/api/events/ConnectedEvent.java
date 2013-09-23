/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.api.SocBot;
import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class ConnectedEvent extends AbstractEvent {
    private static final HandlerList handlers = new HandlerList(AbstractEvent.getHandlerList());
    private final String server;
    private final int port;
    private final SocBot bot;
    
    public ConnectedEvent(String server, int port, SocBot bot) {
        this.server = server;
        this.port = port;
        this.bot = bot;
    }
    
    public SocBot getBot() {
        return bot;
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
