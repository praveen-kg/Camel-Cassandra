package com.infosys.osdm.ds.ingestionservices.processors;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import com.infosys.osdm.ds.ingestionservices.util.Constants;
import com.infosys.osdm.ds.ingestionservices.util.LoggerUtil;

/**
 * 
 * Generic Log class, log based on config
 * 
 */
@Service("logProcessor")
public class LogProcessor implements Processor {

    @Resource
    private LoggerUtil loggerUtil;

    @Override
    public void process(Exchange exchange) throws Exception {
        HashMap<String, String> loggerMap = (HashMap<String, String>) exchange
                .getProperty(Constants.LOGPARAM_KEY_LIST);

        if (loggerMap !=null && !loggerMap.isEmpty()) {
            String requestMessage = exchange.getMessage().getBody().toString();
            LinkedHashMap<String, String> requestParamValues = new LinkedHashMap<>();
            // 1. Iterate map and get values from Request XML and store into
            // LinkedHashMap
            loggerMap.forEach((k, v) -> {
                Pattern pattern = Pattern.compile(v);
                Matcher matcher = pattern.matcher(requestMessage);
                if (matcher.find()) {
                    requestParamValues.put(k, matcher.group(1));
                }
            });

            // 2. Set requestParamValues to Exchange properties
            if (!requestParamValues.isEmpty()) {
                exchange.getProperties().put(
                        Constants.LOGPARAM_KEYVALUE_MAP,
                        requestParamValues);
            }
        }
        loggerUtil.onProcessComplete(exchange);

    }

}
