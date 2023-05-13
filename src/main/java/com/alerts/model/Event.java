package com.alerts.model;

public class Event {
    private final String client;
    private final EventType eventType;
    private final long timestamp;

    public Event(String client, EventType eventType) {
        this.client = client;
        this.eventType = eventType;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getClient() {
        return client;
    }

    public EventType getEventType() {
        return eventType;
    }

    public long getTimestamp() {
        return timestamp;
    }
}