package com.alerts.model;

public enum DispatchType {
	CONSOLE("CONSOLE"), EMAIL("EMAIL");

	String value;

	DispatchType(String string) {
		this.value = string;
	}

	String showValue() {
		return value;
	}
}
