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
public class LineSendEvent extends AbstractEvent {

    private static final HandlerList handlers = new HandlerList(AbstractEvent.getHandlerList());
    private String line;
    private boolean immediate = false;

    public LineSendEvent(SocBot bot, String l) {
        super(bot);
        
        line = l;
    }
    
    public LineSendEvent(SocBot bot, String l, boolean immediate) {
        this(bot, l);
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
