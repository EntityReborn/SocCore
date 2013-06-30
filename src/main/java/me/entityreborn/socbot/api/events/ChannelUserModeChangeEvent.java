/*
 * The MIT License
 *
 * Copyright 2013 Jason Unger <entityreborn@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.entityreborn.socbot.api.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.entityreborn.socbot.api.Channel;
import me.entityreborn.socbot.api.Packet;
import me.entityreborn.socbot.api.User;
import me.entityreborn.socbot.events.HandlerList;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class ChannelUserModeChangeEvent extends TargetedEvent {

    private static final HandlerList handlers = new HandlerList(TargetedEvent.getHandlerList());
    private Map<User, String> modesAdded;
    private Map<User, String> modesRemoved;

    public ChannelUserModeChangeEvent(Packet p) {
        super(p);

        List<String> args = packet.getArgs();

        String modes = args.remove(0);

        modesAdded = new HashMap<User, String>();
        modesRemoved = new HashMap<User, String>();

        boolean adding = true;

        for (char c : modes.toCharArray()) {
            if (c == '+') {
                adding = true;
            } else if (c == '-') {
                adding = false;
            } else if (adding) {
                User u = getBot().getUser(args.remove(0));
                String oldModes = modesAdded.get(u);
                
                if (oldModes == null) {
                    oldModes = "";
                }
                
                modesAdded.put(u, oldModes + c);
            } else {
                User u = getBot().getUser(args.remove(0));
                String oldModes = modesAdded.get(u);
                
                if (oldModes == null) {
                    oldModes = "";
                }
                
                modesRemoved.put(u, oldModes + c);
            }
        }
    }

    public Channel getChannel() {
        return (Channel) getTarget();
    }
    
    public Map<User, String> getAddedModes() {
        return new HashMap<User, String>(modesAdded);
    }
    
    public Map<User, String> getRemovedModes() {
        return new HashMap<User, String>(modesRemoved);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
