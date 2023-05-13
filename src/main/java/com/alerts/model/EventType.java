package com.alerts.model;

public enum EventType {
    PAYMENT_EXCEPTION("PAYMENT_EXCEPTION"),
    USERSERVICE_EXCEPTION("USERSERVICE_EXCEPTION");

	String value;
	EventType(String string) {
		this.value=string;
	}
	String showValue() {
	      return value;
	} 
    // add more event types
}