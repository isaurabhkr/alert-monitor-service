package com.alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alerts.model.Event;
import com.alerts.model.EventStore;
import com.alerts.model.EventType;

class EventServiceTest {
    @Mock
    private EventStore eventStore;

    @Mock
    private MonitoringService monitoringService;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEvent() {
        String client = "testClient";
        String eventType = "testEventType";

        eventService.createEvent(client, eventType);

        verify(eventStore).addEvent(client, eventType);
        verify(monitoringService).handleEvent(client, eventType);
    }

    @Test
    public void testGetEvents() {
        String client = "client1";
        String eventType = "PAYMENT_EXCEPTION";

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event("client1", EventType.PAYMENT_EXCEPTION));
        expectedEvents.add(new Event("client1", EventType.PAYMENT_EXCEPTION));

        when(eventStore.getEvents(client, eventType)).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.getEvents(client, eventType);

        assertEquals(expectedEvents, actualEvents);
        verify(eventStore).getEvents(client, eventType);
    }

    @Test
    public void testClearEvents() {
        String client = "client1";
        String eventType = "PAYMENT_EXCEPTION";

        eventService.clearEvents(client, eventType);

        verify(eventStore).clearEvents(client, eventType);
    }
}
