package com.alerts.model;

public class Alert {

	private final String client;
	private final String eventType;
	private final AlertType alertType;

	public Alert(String client, String eventType, AlertType alertType) {
		this.client = client;
		this.eventType = eventType;
		this.alertType = alertType;
	}

	public String getClient() {
		return client;
	}

	public String getEventType() {
		return eventType;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public String getMessage() {
		return String.format("Alert for client '%s' and event type '%s' with alert type '%s'", client, eventType,
				alertType);
	}

}