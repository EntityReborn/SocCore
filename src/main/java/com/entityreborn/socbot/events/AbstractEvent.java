/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import com.entityreborn.socbot.SocBot;
import com.entityreborn.socbot.eventsystem.Event;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class AbstractEvent extends Event {
    private final long timestamp;
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
