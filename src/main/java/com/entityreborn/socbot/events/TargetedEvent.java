/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import java.util.List;
import com.entityreborn.socbot.Packet;
import com.entityreborn.socbot.Target;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class TargetedEvent extends AbstractPacketEvent {
    private static final HandlerList handlers = new HandlerList(AbstractPacketEvent.getHandlerList());
    Target target;
    
    public TargetedEvent(Packet p) {
        super(p);
        
        // getArgs returns a copy.
        List<String> parts = p.getArgs();

        if (parts.size() >= 1) {
            String tgt = parts.remove(0);
            
            if (Target.Util.isUser(tgt, getBot())) {
                target = getBot().getUser(tgt, true);
            } else {
                target = getBot().getChannel(tgt);
            }
            
            p.setArgs(parts);
        }
    }

    public Target getTarget() {
        return target;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
