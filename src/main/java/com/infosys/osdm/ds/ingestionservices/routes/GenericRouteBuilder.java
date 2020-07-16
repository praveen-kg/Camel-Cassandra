/**
 * 
 */
package com.infosys.osdm.ds.ingestionservices.routes;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosys.osdm.ds.ingestionservices.util.LoggerUtil;

/**
 *  This is a generic route builder class, should be extended by rest of the route builders.
 *  It addresses cross-cutting concerns for logging through camel interceptors.
 *
 */
public abstract class GenericRouteBuilder extends RouteBuilder {
    @Resource
    private LoggerUtil loggerUtil;


    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void configure() throws Exception {
        interceptFrom().to("bean:loggerUtil?method=onServiceStart");
        onCompletion().to("bean:loggerUtil?method=onServiceComplete");
        onException(Exception.class).process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
            	loggerUtil.onProcessError(exchange);
                
            }
        });
    }

    
}
