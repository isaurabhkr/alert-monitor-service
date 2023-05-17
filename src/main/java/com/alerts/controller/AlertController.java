package com.alerts.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alerts.model.Event;
import com.alerts.model.EventDTO;
import com.alerts.service.EventService;

@RestController
@RequestMapping("/api")
public class AlertController {

	@Autowired
	private final EventService eventService;

	private static final Logger LOG = getLogger(AlertController.class.getName());

	public AlertController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/event")
	public ResponseEntity<Void> createEvent(@RequestBody EventDTO eventDTO) {
		eventService.createEvent(eventDTO.getClient(), eventDTO.getEventType());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/events/{client}/{eventType}")
	public ResponseEntity<List<Event>> getEvents(@PathVariable String client, @PathVariable String eventType) {
		List<Event> events = eventService.getEvents(client, eventType);
		return ResponseEntity.ok(events);
	}

	@DeleteMapping("/events/{client}/{eventType}")
	public ResponseEntity<Void> clearEvents(@PathVariable String client, @PathVariable String eventType) {
		eventService.clearEvents(client, eventType);
		return ResponseEntity.noContent().build();
	}

}
