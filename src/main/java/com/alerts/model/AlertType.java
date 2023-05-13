package com.alerts.model;

public enum AlertType {
	 SIMPLE_COUNT("SIMPLE_COUNT"),
	 TUMBLING_WINDOW("TUMBLING_WINDOW"),
	 SLIDING_WINDOW("SLIDING_WINDOW");
	 
	 
	String value;
	AlertType(String string) {
		this.value=string;
	}
	String showValue() {
	      return value;
	} 
}