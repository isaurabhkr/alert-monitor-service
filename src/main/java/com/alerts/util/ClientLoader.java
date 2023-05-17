package com.alerts.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alerts.model.AlertType;
import com.alerts.model.ClientConfig;
import com.alerts.model.DispatchType;
import com.alerts.model.EventType;
import com.alerts.service.DispatchStrategy;

@Service
public class ClientLoader {
	private static final String CLIENT_PROP_PREFIX = "client.";
	private static final String ALERT_TYPE_PROP_PREFIX = ".alertType";
	private static final String ALERT_COUNT_PROP_PREFIX = ".alertCount";
	private static final String ALERT_WINDOW_SIZE_PROP_PREFIX = ".alertWindowSize";
	private static final String DISPATCH_STRATEGY_TYPE_PROP_PREFIX = ".dispatchStrategy.type";
	private static final String DISPATCH_STRATEGY_MESSAGE_PROP_PREFIX = ".dispatchStrategy.message";
	private static final String DISPATCH_STRATEGY_SUBJECT_PROP_PREFIX = ".dispatchStrategy.subject";

	public static List<ClientConfig> loadClientConfigs(String propsFilePath) throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream(propsFilePath));

		List<ClientConfig> clientConfigs = new ArrayList<>();
		for (String key : props.stringPropertyNames()) {

			if (key.startsWith(CLIENT_PROP_PREFIX)) {
				String client = key.substring(CLIENT_PROP_PREFIX.length()).split("[.]", 0)[0];
				String eventTypeStr = key.substring(CLIENT_PROP_PREFIX.length()).split("[.]", 0)[1];
				key = key.split("[.]", 0)[0];
				
				EventType eventType = EventType.valueOf(eventTypeStr);

				String alertTypeStr = props.getProperty(key + "." + client + "." + eventType + ALERT_TYPE_PROP_PREFIX);
				AlertType alertType = AlertType.valueOf(alertTypeStr);

				int alertCount = Integer
						.parseInt(props.getProperty(key + "." + client + "." + eventType + ALERT_COUNT_PROP_PREFIX));
				int alertWindowSize = Integer.parseInt(
						props.getProperty(key + "." + client + "." + eventType + ALERT_WINDOW_SIZE_PROP_PREFIX));

				List<DispatchStrategy> dispatchStrategies = new ArrayList<>();
				for (int i = 0;; i++) {

					String typeKey = key + "." + client + "." + eventType + DISPATCH_STRATEGY_TYPE_PROP_PREFIX + "[" + i
							+ "]";
					String messageKey = key + "." + client + "." + eventType + DISPATCH_STRATEGY_MESSAGE_PROP_PREFIX
							+ "[" + i + "]";
					String subjectKey = key + "." + client + "." + eventType + DISPATCH_STRATEGY_SUBJECT_PROP_PREFIX
							+ "[" + i + "]";

					if (!props.containsKey(typeKey)) {
						break;
					}

					DispatchType dispatchType = DispatchType.valueOf(props.getProperty(typeKey));
					String message = props.getProperty(messageKey, "");
					String subject = props.getProperty(subjectKey, "");
					dispatchStrategies.add(new DispatchStrategy(dispatchType, message, subject));
				}

				clientConfigs.add(new ClientConfig(client, eventType, alertType, alertCount, alertWindowSize,
						dispatchStrategies));
			}
		}
		return clientConfigs.stream().distinct().collect(Collectors.toList());
	}
}