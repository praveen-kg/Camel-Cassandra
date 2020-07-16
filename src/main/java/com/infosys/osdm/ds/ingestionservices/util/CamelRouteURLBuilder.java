package com.infosys.osdm.ds.ingestionservices.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class has one static method which is basically building URL for route in
 * format what camel understand.
 *
 */
public final class CamelRouteURLBuilder {
    private static Logger logger = LoggerFactory
            .getLogger(CamelRouteURLBuilder.class);

    /**
     * This is constructor for CamelRouteURLBuilder class which will
     * automatically invoke when instance of this class gets created.
     */
    private CamelRouteURLBuilder() {
        logger.error("Trying to instantiate a class with only static members ");
    }

    /**
     * This method appends camel options to the route URL path and convert the
     * URL in camel Source or destination format.
     *
     * @param routeDefinition
     *            the object which contains route path and map of camel options.
     * @return url this is the apache camel source or destination URL format
     *         specific to camel.
     * 
     */
    public static String getURL(KafkaURLDefinition routeDefinition) {
        StringBuilder url = new StringBuilder(
                "kafka:" + routeDefinition.getSource().getTopic());
        if (!routeDefinition.getSource().getCamelKafkaOptions().isEmpty()) {
            url.append("?");
            routeDefinition.getSource().getCamelKafkaOptions().forEach((key, value) -> {
                if (StringUtils.isNotBlank(value)) {
                    url.append(key).append("=").append(value).append("&");
                }
            });
        }

        return StringUtils.stripEnd(StringUtils.stripEnd(url.toString(), "&"),"?");

    } 
    

    /**
     * This method appends camel options to the route URL path and convert the
     * URL in camel destination format.
     *
     * @param routeDefinition
     *            the object which contains route path and map of camel options.
     * @return url this is the apache camel  destination URL format
     *         specific to camel.
     * 
     */
    public static String getDestinationURL(KafkaURLDefinition routeDefinition) {
        StringBuilder url = new StringBuilder(
                "cql://" + routeDefinition.getDestination().getHosts());
        
        int port = (routeDefinition.getDestination().getPort() == 0) ? 9042 : routeDefinition.getDestination().getPort();
        url.append(":").append(port);
        url.append("/").append(routeDefinition.getDestination().getKeyspace());
        url.append("?cql=").append(routeDefinition.getDestination().getSql());
        return url.toString();

    } 
    

    
   
  
}
