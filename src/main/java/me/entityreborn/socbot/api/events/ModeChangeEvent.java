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
public class ModeChangeEvent extends TargetedEvent {
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    private String modesAdded = "";
    private String modesRemoved = "";
    
    public ModeChangeEvent(Packet p) {
        super(p);
        
        String modes = packet.getMessage();
        
        if (modes.isEmpty()) {
            modes = packet.getArgs().get(0);
        }
        
        boolean adding = true;
        
        for (char c: modes.toCharArray()) {
            if (c == '+') {
                adding = true;
            } else if (c == '-') {
                adding = false;
            } else if (adding) {
                modesAdded += c;
            } else {
                modesRemoved += c;
            }
        }
    }
    
    public String getAddedModes() {
        return modesAdded;
    }
    
    public String getRemovedModes() {
        return modesRemoved;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
