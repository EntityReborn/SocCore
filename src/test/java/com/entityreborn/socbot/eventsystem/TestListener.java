package com.entityreborn.socbot.eventsystem;

import com.entityreborn.socbot.eventsystem.EventHandler;
import com.entityreborn.socbot.eventsystem.Listener;

public class TestListener implements Listener {

    private boolean hasBeenCalled = false;

    @EventHandler
    public void onTestEvent(TestEvent event) {
        hasBeenCalled = true;
    }

    public boolean hasBeenCalled() {
        return hasBeenCalled;
    }
}
