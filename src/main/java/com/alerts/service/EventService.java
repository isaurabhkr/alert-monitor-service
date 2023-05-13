package com.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alerts.model.Event;
import com.alerts.model.EventStore;

@Service
public class EventService {

	@Autowired
	private final EventStore eventStore;

	@Autowired
	private final MonitoringService monitoringService;

	public EventService(EventStore eventStore, MonitoringService monitoringService) {
		this.eventStore = eventStore;
		this.monitoringService = monitoringService;
	}

	public void createEvent(String client, String eventType) {
		eventStore.addEvent(client, eventType);
		monitoringService.handleEvent(client, eventType);

	}

	public List<Event> getEvents(String client, String eventType) {
		return eventStore.getEvents(client, eventType);
	}

	public void clearEvents(String client, String eventType) {
		eventStore.clearEvents(client, eventType);
	}

}
