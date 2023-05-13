package com.alerts.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class EventStore {

    private final Map<String, List<Event>> eventMap;

    public EventStore() {
        eventMap = new HashMap<>();
    }

    public void addEvent(String client, String eventType) {
        String key = client + "_" + eventType;
        eventMap.putIfAbsent(key, new ArrayList<>());
        Event event = new Event(client, EventType.valueOf(eventType));
        eventMap.get(key).add(event);
    }

    public List<Event> getEvents(String client, String eventType) {
        String key = client + "_" + eventType;
        return eventMap.getOrDefault(key, new ArrayList<>());
    }

    public void clearEvents(String client, String eventType) {
        String key = client + "_" + eventType;
        eventMap.remove(key);
    }

}