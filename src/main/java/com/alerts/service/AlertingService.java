package com.alerts.service;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alerts.model.DispatchType;

@Service
public class AlertingService {

	public static DispatchConsole dispatchConsole = new DispatchConsole();

	public static DispatchEmail dispatchEmail = new DispatchEmail();

	static final Logger LOG = getLogger(AlertingService.class.getName());

	public void dispatch(DispatchType dispatchType, String message, String subject) {
		switch (dispatchType) {
		case EMAIL:
			LOG.info("Dispatching to email");
			dispatchEmail.dispatch(message, subject);
			break;

		case CONSOLE:
			LOG.info("Dispatching to console");
			dispatchConsole.dispatch(message, subject);
			break;

		default:
			LOG.error("Invalid dispatch type");
			break;
		}
	}
}
