package com.infosys.osdm.ds.ingestionservices.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This pojo class contains private variable and public getter setter for Url
 * path and map of camel-kafka options of routes.
 * 
 * 
 */
public class KafkaURLDefinition {

    private String routeId;
    private Source source;
    private final Map<String, String> logValue = new HashMap<>();
    private String routeEnabled;
    private Destination destination;
    
    
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }



    public Map<String, String> getLogValue() {
        return logValue;
    }

    public String getRouteEnabled() {
        return routeEnabled;
    }

    public void setRouteEnabled(String routeEnabled) {
        this.routeEnabled = routeEnabled;
    }

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

    
}
