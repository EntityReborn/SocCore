package me.entityreborn.socbot.events;

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
