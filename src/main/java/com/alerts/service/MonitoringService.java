package com.alerts.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alerts.model.Alert;
import com.alerts.model.AlertType;
import com.alerts.model.ClientConfig;
import com.alerts.model.Event;
import com.alerts.model.EventStore;
import com.alerts.util.ClientLoader;

@Service
public class MonitoringService {
	private final Map<String, Map<String, ClientConfig>> alertConfigMap; // client -> eventType -> alertConfig
	private final Map<String, Map<String, Queue<Long>>> eventTimeMap; // client -> eventType -> eventTime
	private final Map<String, Map<String, Integer>> eventCountMap; // client -> eventType -> eventCount

	@Autowired
	private EventStore eventStore;

	@Autowired
	private AlertingService alertService;

	private static final Logger LOG = getLogger(MonitoringService.class.getName());

	public MonitoringService(List<ClientConfig> alertConfigs, List<DispatchStrategy> dispatchStragy) throws Exception {
		this.alertConfigMap = new ConcurrentHashMap<>();
		this.eventTimeMap = new ConcurrentHashMap<>();
		this.eventCountMap = new ConcurrentHashMap<>();

		alertConfigs = ClientLoader.loadClientConfigs("src/main/resources/client.properties");

		// Initialize alertConfigMap with given alertConfigs
		for (ClientConfig alertConfig : alertConfigs) {
			String client = alertConfig.getClient();
			String eventType = alertConfig.getEventType().toString();
			alertConfigMap.computeIfAbsent(client, k -> new ConcurrentHashMap<>()).put(eventType, alertConfig);
		}
	}

	public void handleEvent(String client, String eventType) {
		long currentTime = System.currentTimeMillis();
		Map<String, Queue<Long>> clientEventTimeMap = eventTimeMap.computeIfAbsent(client,
				k -> new ConcurrentHashMap<>());
		Map<String, Integer> clientEventCountMap = eventCountMap.computeIfAbsent(client,
				k -> new ConcurrentHashMap<>());

		// Update eventTimeMap
		clientEventTimeMap.computeIfAbsent(eventType, k -> new LinkedList<>()).add(currentTime);
		ClientConfig alertConfig = alertConfigMap.get(client).get(eventType);

		// Handle SIMPLE_COUNT alert type
		if (alertConfig.getAlertType() == AlertType.SIMPLE_COUNT) {
			int eventCount = clientEventCountMap.compute(eventType, (k, v) -> (v == null ? 0 : v) + 1);

			if (eventCount >= alertConfig.getCount()) {
				dispatchAlert(client, eventType, alertConfig);
				clientEventCountMap.put(eventType, 0); // Reset event count
			}
		}

		// Handle TUMBLING_WINDOW alert type
		if (alertConfig.getAlertType() == AlertType.TUMBLING_WINDOW) {
		    Queue<Long> eventTimeQueue = clientEventTimeMap.get(eventType);
		    long windowStartTime = (currentTime / (60 * 60 * 1000)) * (60 * 60 * 1000); // Round off to the nearest hour
		    long windowEndTime = windowStartTime + alertConfig.getWindowSizeInSecs() * 1000; // Calculate the end time of the window

		    // Check the number of events in the current window
		    int eventCount = 0;
		    for (long eventTime : eventTimeQueue) {
		        if (eventTime < windowEndTime && eventTime > windowStartTime) {
		            eventCount++;
		        }
		    }
		    
		    // If the number of events exceeds the alert threshold, trigger an alert
		    if (eventCount >= alertConfig.getCount()) {
		        dispatchAlert(client, eventType, alertConfig);
		    }
		}


		// Handle SLIDING_WINDOW alert type
		if (alertConfig.getAlertType() == AlertType.SLIDING_WINDOW) {
		    Queue<Long> eventTimeQueue = clientEventTimeMap.get(eventType);
		    long windowEndTime = currentTime;
		    long windowStartTime = windowEndTime - alertConfig.getWindowSizeInSecs() * 1000;
		    
		    // Remove events that occurred before the start time of the current window
		    while (!eventTimeQueue.isEmpty() && eventTimeQueue.peek() < windowStartTime) {
		        eventTimeQueue.poll();
		    }
		    
		    // Check the number of events in the current window
		    int eventCount = eventTimeQueue.size();
		    
		    // If the number of events exceeds the alert threshold, trigger an alert
		    if (eventCount >= alertConfig.getCount()) {
		        dispatchAlert(client, eventType, alertConfig);
		    }
		}
	}

	private void dispatchAlert(String client, String eventType, ClientConfig alertConfig) {
		LOG.info(String.format("Client %s %s %s starts", client, eventType, alertConfig.getAlertType()));
		Alert alert = new Alert(client, eventType, alertConfig.getAlertType());
		int eventCount = getEventCount(client, eventType, alertConfig);

		if (eventCount >= alertConfig.getCount()) {
			LOG.info(String.format("Client %s %s threshold breached", client, eventType));
			for (DispatchStrategy dispatchStrategy : alertConfig.getDispatchStrategies()) {
				alertService.dispatch(dispatchStrategy.getDispatchType(), dispatchStrategy.getMessage(),
						dispatchStrategy.getSubject());
			}
		}
		LOG.info(String.format("Client %s %s %s ends", client, eventType, alertConfig.getAlertType()));
	}

	/**
	 * Returns the count of events for a given client and eventType within the
	 * specified time window based on the alert configuration type
	 **/
	private int getEventCount(String client, String eventType, ClientConfig alertConfig) {
		List<Event> eventList = eventStore.getEvents(client, eventType);
		long currentTime = System.currentTimeMillis() / 1000;

		if (alertConfig.getAlertType() == AlertType.SIMPLE_COUNT) {
			return eventList.size();
		} else if (alertConfig.getAlertType() == AlertType.TUMBLING_WINDOW) {
			int count = 0;
			long windowStartTime = currentTime - currentTime % alertConfig.getWindowSizeInSecs();
			for (Event event : eventList) {
				if (event.getTimestamp() >= windowStartTime) {
					count++;
				}
			}
			return count;
		} else if (alertConfig.getAlertType() == AlertType.SLIDING_WINDOW) {
			int count = 0;
			long windowStartTime = currentTime - alertConfig.getWindowSizeInSecs();
			for (Event event : eventList) {
				if (event.getTimestamp() >= windowStartTime) {
					count++;
				}
			}
			return count;
		} else {
			throw new IllegalArgumentException("Invalid alert type: " + alertConfig.getAlertType());
		}
	}

}