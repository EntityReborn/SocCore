/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api.events;

import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class LineSendEvent extends AbstractEvent {

    private static final HandlerList handlers = new HandlerList(AbstractEvent.getHandlerList());
    private String line;
    private boolean immediate = false;

    public LineSendEvent(String l) {
        line = l;
    }
    
    public LineSendEvent(String l, boolean immediate) {
        this(l);
        this.immediate = immediate;
    }
    
    public boolean isImmediate() {
        return immediate;
    }

    public String getLine() {
        return line;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
