package com.infosys.osdm.ds.ingestionservices.routes;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.KafkaManualCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosys.osdm.ds.ingestionservices.config.CamelKafkaConfig;
import com.infosys.osdm.ds.ingestionservices.processors.CassandraProcessor;
import com.infosys.osdm.ds.ingestionservices.processors.LogProcessor;
import com.infosys.osdm.ds.ingestionservices.util.CamelRouteURLBuilder;
import com.infosys.osdm.ds.ingestionservices.util.Constants;
import com.infosys.osdm.ds.ingestionservices.util.KafkaURLDefinition;


@Component
public class KafkaRouteBuilder extends GenericRouteBuilder {

	private static final Logger logger = LoggerFactory.getLogger(KafkaRouteBuilder.class.getName());

	@Autowired
	private CamelKafkaConfig kafkaConfig;

	@Autowired
	private LogProcessor logProcessor;
	@Autowired
	private CassandraProcessor cassandraProcessor;
	@Override
	public void configure() throws Exception {
			super.configure();
			Map<String,KafkaURLDefinition> routs = kafkaConfig.getConfig();
			routs.forEach((routeId, kafka) -> {

			String kafkaURL = CamelRouteURLBuilder.getURL(kafka);
			String destinationURL  = CamelRouteURLBuilder.getDestinationURL(kafka);
			from(kafkaURL)
				.choice()
					.when(getManualCommitPredicate(kafka))
						.process(exchange -> doManualCommit(exchange))
					.otherwise()
					.end()
				.setProperty(Constants.LOGPARAM_KEY_LIST,
	                        constant(kafka.getLogValue()))
				.process(logProcessor)
				.log(LoggingLevel.INFO, "Downloaded Message Content: ${body}")
				.setProperty(Constants.CASSANDRA_ENDPOINT,
                        constant(destinationURL))
				.process(cassandraProcessor);
			});
		
	}

	private Predicate getManualCommitPredicate(KafkaURLDefinition kafkaSrcCfg) {
		Map<String,String> urlDefMap = kafkaSrcCfg.getSource().getCamelKafkaOptions();
		return PredicateBuilder.constant("true".equalsIgnoreCase(urlDefMap.get("allowManualCommit")));
	}
	
	
	
	private void doManualCommit(Exchange exchange) {
		Boolean lastRecordOfBatch = exchange.getIn().getHeader(KafkaConstants.LAST_RECORD_BEFORE_COMMIT, Boolean.class);
		if (lastRecordOfBatch != null && lastRecordOfBatch) {
			KafkaManualCommit kafkaManualCommit = exchange.getIn().getHeader(KafkaConstants.MANUAL_COMMIT,
					KafkaManualCommit.class);
			if (kafkaManualCommit != null) {
				logger.debug("Triggering manual commit");
				kafkaManualCommit.commitSync();
			}
		}
	}

	
}
