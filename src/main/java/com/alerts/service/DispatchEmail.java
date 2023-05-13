package com.alerts.service;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class DispatchEmail  {
	private static final Logger LOG = getLogger(DispatchEmail.class.getName());

	public void dispatch(String message, String subject) {
		LOG.info("EMAIL sent with : "+subject);
	}
}