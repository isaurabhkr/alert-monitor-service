package com.alerts.model;

import java.util.List;

import com.alerts.service.DispatchStrategy;

public class EventDTO {
    private String client;
    private String eventType;
    private ClientConfig alertConfig;
    private List<DispatchStrategy> dispatchStrategyList;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public ClientConfig getAlertConfig() {
        return alertConfig;
    }

    public void setAlertConfig(ClientConfig alertConfig) {
        this.alertConfig = alertConfig;
    }

    public List<DispatchStrategy> getDispatchStrategyList() {
        return dispatchStrategyList;
    }

    public void setDispatchStrategyList(List<DispatchStrategy> dispatchStrategyList) {
        this.dispatchStrategyList = dispatchStrategyList;
    }


}
