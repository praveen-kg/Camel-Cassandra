package com.infosys.osdm.ds.ingestionservices.util;

import java.util.HashMap;
import java.util.Map;



/**
 * this is config class for source definition
 */
public class Source {

	 	private String topic;
	    private final Map<String, String> camelKafkaOptions = new HashMap<>();
	    private final Map<String, String> options = new HashMap<>();
	    

		

		public String getTopic() {
			return topic;
		}
		public void setTopic(String topic) {
			this.topic = topic;
		}
		public Map<String, String> getOptions() {
			return options;
		}
		public Map<String, String> getCamelKafkaOptions() {
			return camelKafkaOptions;
		}
		
}
