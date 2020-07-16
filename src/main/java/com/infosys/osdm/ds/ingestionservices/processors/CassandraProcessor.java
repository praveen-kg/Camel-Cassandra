package com.infosys.osdm.ds.ingestionservices.processors;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.osdm.ds.ingestionservices.util.Constants;

/**
 * 
 * Cassandra processor
 * 
 */
@Service("cassandraProcessor")
public class CassandraProcessor implements Processor {

	@Autowired
	private ProducerTemplate producerTemplate;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		 String cassandraEndpointURL =  (String) exchange.getProperty(Constants.CASSANDRA_ENDPOINT);
			producerTemplate.sendBody(cassandraEndpointURL, exchange.getIn().getBody());
	}

}
