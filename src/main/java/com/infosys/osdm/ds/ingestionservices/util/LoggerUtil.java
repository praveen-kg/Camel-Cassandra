package com.infosys.osdm.ds.ingestionservices.util;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.infosys.osdm.ds.ingestionservices.config.CamelKafkaConfig;

/**
 * Common logger utility class
 *
 */

@Service("loggerUtil")
public class LoggerUtil {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory
            .getLogger(LoggerUtil.class.getName());
      
    @Resource
    private CamelKafkaConfig camelKafkaConfig;

    /**
     * On service start.
     *
     * @param soapBodyElements
     *            the soap body elements
     * @param inHeadersMap
     *            the in headers map
     * @param properties
     *            the properties
     * @param exchange
     *            the exchange
     */
    public void onServiceStart(@Body List<Object> soapBodyElements,
            @Headers Map<String, String> inHeadersMap,
            @ExchangeProperties Map<String, String> properties,
            Exchange exchange) {

        if (CollectionUtils.isEmpty(soapBodyElements)) {
            // This should never happen since the SOAP request is already
            logger.error(
                    "onServiceStart:Soap body does not contain RequestHeader and RequestBody");
            logger.error("onServiceStart error {}", soapBodyElements);
            return;
          }

        String endpoint = inHeadersMap
                .get(Constants.CAMEL_INTERCEPTOR_ENDPOINT).trim();
        properties.put(Constants.ENDPOINT, endpoint);
        
    }



   

    /**
     * On service complete.
     *
     * @param soapBodyElements
     *            the soap body elements
     * @param inHeadersMap
     *            the in headers map
     * @param properties
     *            the properties
     * @param exchange
     *            Camel exchange
     */
    public void onServiceComplete(@Body List<Object> soapBodyElements,
            @Headers Map<String, String> inHeadersMap, Exchange exchange) {
    		
    	  Date startTime = new Date(exchange.getCreated());
          Date now = new Date();
          LinkedHashMap<String, Object> loggerMap = new LinkedHashMap<String, Object>();

    	  createLogMap(exchange, startTime, now, loggerMap);
          loggerMap.put(Constants.EVENT, "FinishedMessageProcessing");

          logger.info(getLog(loggerMap));
      
    }




    /**
     * Create Plain MQ Message Log Map for generating event logs
     * 
     * @param exchange
     *            Camel Route exchange.
     * @param startTime
     *            Event Start time
     * @param now
     *            Event Finish Time
     * @param loggerMap
     *            Linked Map for Log generation.
     */
    private void createLogMap(Exchange exchange, Date startTime,
            Date now, LinkedHashMap<String, Object> loggerMap) {
        loggerMap.put(Constants.EVENT, null);
        if (exchange
                .getProperty(Constants.LOGPARAM_KEYVALUE_MAP) != null) {
            loggerMap.putAll((Map<? extends String, ? extends Object>) exchange
                    .getProperty(Constants.LOGPARAM_KEYVALUE_MAP));
        } 

       
        loggerMap.put(Constants.TIMESPENT,
                (now.getTime() - startTime.getTime()));

        loggerMap.put(Constants.ENDPOINT,
                exchange.getProperty(Constants.ENDPOINT, String.class));

        loggerMap.put(Constants.ROUTEID, exchange.getFromRouteId());
    }

    /**
     * On MQ process complete log the event metadata to log file
     * 
     * @param exchange
     *            Camel exchange
     * 
     */
    public void onProcessComplete(Exchange exchange) {
        Date startTime = new Date(exchange.getCreated());
        Date now = new Date();
        LinkedHashMap<String, Object> loggerMap = new LinkedHashMap<String, Object>();

        createLogMap(exchange, startTime, now, loggerMap);
        loggerMap.put(Constants.EVENT, "FinishedMessageParsing");

        logger.info(getLog(loggerMap));
    }

    /**
     * Method to generate MQPlainTest log message
     * 
     * @param loggerLinkedHashmap
     *            linked hashmap tp maintain the sequence of log parameters.
     * @return logMessage
     */
    private String getLog(LinkedHashMap<String, Object> loggerLinkedHashmap) {
        return loggerLinkedHashmap.entrySet().stream()
                .filter(e -> e.getValue() != StringUtils.EMPTY)
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", "));
    }

    

    /**
     * log all route errors to log file
     * 
     * @param exchange
     *            Camel exchange
     * 
     */
    public void onProcessError(Exchange exchange) {
       Date startTime = new Date(exchange.getCreated());
        Throwable ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
                Throwable.class);
       

            Date now = new Date();
            String endpoint = exchange.getProperty(Constants.ENDPOINT,
                    String.class);
            String routeId = exchange.getFromRouteId();
            logger.error(
                    "event=ProcessingError,timeSpent={}, endpoint= {}, routeId={}, exception={}",
                     (now.getTime() - startTime.getTime()), endpoint,
                    routeId, ex);
        
    }

  

    

}
