package com.entityreborn.socbot.eventsystem;

import com.entityreborn.socbot.eventsystem.HandlerList;
import com.entityreborn.socbot.eventsystem.Event;

public class TestEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
