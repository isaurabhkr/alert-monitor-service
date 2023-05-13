package com.alerts.model;

import java.util.List;

import com.alerts.service.DispatchStrategy;

public class ClientConfig {
	private final String client;
	private final EventType eventType;
	private final AlertType alertType;
	private final int count;
	private final int windowSizeInSecs;
	private final List<DispatchStrategy> dispatchStrategies;

	public ClientConfig(String client, EventType eventType, AlertType alertType, int count, int windowSizeInSecs,
			List<DispatchStrategy> dispatchStrategies) {
		this.client = client;
		this.eventType = eventType;
		this.alertType = alertType;
		this.count = count;
		this.windowSizeInSecs = windowSizeInSecs;
		this.dispatchStrategies = dispatchStrategies;
	}

	public String getClient() {
		return client;
	}

	public EventType getEventType() {
		return eventType;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public int getCount() {
		return count;
	}

	public int getWindowSizeInSecs() {
		return windowSizeInSecs;
	}

	public List<DispatchStrategy> getDispatchStrategies() {
		return dispatchStrategies;
	}

	@Override
	public String toString() {
		return "ClientConfig [client=" + client + ", eventType=" + eventType + ", alertType=" + alertType + ", count="
				+ count + ", windowSizeInSecs=" + windowSizeInSecs + ", dispatchStrategies=" + dispatchStrategies + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alertType == null) ? 0 : alertType.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + count;
		result = prime * result + ((dispatchStrategies == null) ? 0 : dispatchStrategies.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + windowSizeInSecs;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientConfig other = (ClientConfig) obj;
		if (alertType != other.alertType)
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (count != other.count)
			return false;
		if (dispatchStrategies == null) {
			if (other.dispatchStrategies != null)
				return false;
		} else if (!dispatchStrategies.equals(other.dispatchStrategies))
			return false;
		if (eventType != other.eventType)
			return false;
		if (windowSizeInSecs != other.windowSizeInSecs)
			return false;
		return true;
	}

}