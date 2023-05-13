package com.alerts.service;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alerts.AlertController;

@Service
public class DispatchConsole {
	private static final Logger LOG = getLogger(DispatchConsole.class.getName());
	
	public void dispatch(String message, String subject) {
		LOG.warn(message);
	}
	
}