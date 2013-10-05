/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.api.SocBot;
import me.entityreborn.socbot.events.Event;
import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class AbstractEvent extends Event {
    private long timestamp;
    private static final HandlerList handlers = new HandlerList();
    protected SocBot bot;
    
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public SocBot getBot() {
        return bot;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public AbstractEvent(SocBot b) {
        timestamp = System.currentTimeMillis();
        bot = b;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
}
