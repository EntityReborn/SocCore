/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import com.entityreborn.socbot.Packet;
import com.entityreborn.socbot.Target;
import com.entityreborn.socbot.User;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class NoticeEvent extends TargetedEvent {
    private boolean isReply = false;
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());

    public NoticeEvent(Packet p) {
        super(p);
        
        String message = p.getMessage();
        
        if (message.startsWith("\01") && message.endsWith("\01")) {
            isReply = true;
            p.setMessage(message.substring(1, message.length() - 1));
        }
    }
    
    public boolean isNoticeReply() {
        return isReply;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public User getUser() {
        Target target = getSender();
        
        if (target instanceof User) {
            return (User)target;
        }
        
        return null;
    }
}
