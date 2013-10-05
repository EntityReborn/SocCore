/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entityreborn.socbot.events;

import java.util.List;
import com.entityreborn.socbot.Channel;
import com.entityreborn.socbot.Packet;
import com.entityreborn.socbot.Target;
import com.entityreborn.socbot.User;
import com.entityreborn.socbot.eventsystem.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class KickEvent extends TargetedEvent {
    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    private final Channel channel;
    private final User kickee;
    private User kicker;
    
    public KickEvent(Packet p) {
        super(p);
        
        channel = (Channel)getTarget();
        List<String> args = getPacket().getArgs();
        kickee = getBot().getUser(args.remove(0));
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public User getKicked() {
        return kickee;
    }
    
    public User getKicker() {
        Target target = getSender();
        
        if (target instanceof User) {
            return (User)target;
        }
        
        return null;
    }
    
    public String getKickMessage() {
        return packet.getMessage();
    }
}
