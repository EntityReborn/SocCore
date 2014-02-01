package com.entityreborn.socbot.eventsystem;

import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 *
 * Test for the EventManager
 */
public class EventManagerTest {

    @Test
    public void testEventCalling() {
        final TestListener testListener = new TestListener();
        EventManager.registerEvents(testListener, this);
        EventManager.callEvent(new TestEvent());
        assertTrue(testListener.hasBeenCalled());
        HandlerList.unregisterAll();
    }

    @Test
    public void testInstanceCalling() {
        Object other = new Object();
        TestSubListener testListener = new TestSubListener();
        TestSubListener testListener2 = new TestSubListener();
        
        EventManager.registerEvents(testListener, this);
        EventManager.registerEvents(testListener2, other);
        
        EventManager.callEvent(new TestEvent(), this);
        EventManager.callEvent(new TestEvent(), other);
        
        assertEquals(testListener.getParentCallCount(), 1);
        assertEquals(testListener.getChildCallCount(), 0);
        
        assertEquals(testListener2.getParentCallCount(), 1);
        assertEquals(testListener2.getChildCallCount(), 0);
        
        EventManager.callEvent(new TestSubEvent(), this);

        assertEquals(testListener.getParentCallCount(), 2);
        assertEquals(testListener.getChildCallCount(), 1);
        
        assertEquals(testListener2.getParentCallCount(), 1);
        assertEquals(testListener2.getChildCallCount(), 0);
        
        EventManager.callEvent(new TestSubEvent());
        
        assertEquals(testListener.getParentCallCount(), 3);
        assertEquals(testListener.getChildCallCount(), 2);
        
        assertEquals(testListener2.getParentCallCount(), 2);
        assertEquals(testListener2.getChildCallCount(), 1);
        
        HandlerList.unregisterAll();
    }
    
    @Test
    public void testSubEventCalling() {
        final TestSubListener testListener = new TestSubListener();
        EventManager.registerEvents(testListener, this);
        EventManager.callEvent(new TestEvent());
        EventManager.callEvent(new TestSubEvent());

        assertEquals(2, testListener.getParentCallCount());
        assertEquals(1, testListener.getChildCallCount());

        HandlerList.unregisterAll();
    }

    @Test
    public void testEventPriorities() {
        final List<Order> calledOrders = new ArrayList<Order>();

        for (final Order order : Order.values()) {
            EventManager.registerEvent(TestEvent.class, order, new EventExecutor() {
                public void execute(Event event) throws EventException {
                    calledOrders.add(order);
                }
            }, this);
        }
        EventManager.callEvent(new TestEvent());

        assertEquals(calledOrders.size(), Order.values().length);
        for (Order order : Order.values()) {
            assertTrue(calledOrders.indexOf(order) >= 0);
            assertEquals(calledOrders.get(order.getIndex()), order);
        }

        HandlerList.unregisterAll();
    }
}
