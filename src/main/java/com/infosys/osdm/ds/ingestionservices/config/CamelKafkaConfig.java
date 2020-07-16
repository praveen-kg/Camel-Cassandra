package com.infosys.osdm.ds.ingestionservices.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.infosys.osdm.ds.ingestionservices.util.KafkaURLDefinition;

/**
 * used to map Camel Kafka Configs
 *
 */
@Component("kafkaconfig")
@ConfigurationProperties(prefix = "kafka")
public class CamelKafkaConfig {

    private Map<String, KafkaURLDefinition> config = new HashMap<>();

    public Map<String, KafkaURLDefinition> getConfig() {
        return config;
    }

    @Override
    public String toString() {
        return config.toString();
    }

}
