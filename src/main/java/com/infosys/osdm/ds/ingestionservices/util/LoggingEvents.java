package com.infosys.osdm.ds.ingestionservices.util;

/**
 * 
 *  Events to be logged in Splunk
 */
public enum LoggingEvents {
    APPLICATIONSTARTED(
            "event=FileRouterApplicationStarted",
            "DataHub FileRouter Application has been started"),
    NEWFILEERROR(
            "event=NewFileError, Error trying to copy New file, cause={}",
            "Logs when new file arrives and cannot be picked for processing"),
    NEWFILEARRIVED(
            "event=NewFileArrived, routeId={}, exchangeId={}, sourceDir={}, fileName={}, destinationDir={}",
            "Print source and destination when new file arrives"),
    CAMELCONTEXTSTARTED(
            "event=CamelContextStarted, CamelContext: {} started successfully",
            "Event to describe when camel context started"),
    CAMELCONTEXTSTARTUPFAILURE(
            "event=CamelContextStartupFailureEvent, Failed to Start Camel Context {} cause is {}",
            "Failed Starting Camel Context"),
    ROUTECREATED(
            "event=RouteCreated, Created Route with routeId={}",
            "Route has been created"),
    ROUTESTARTED(
            "event=RouteStarted, routeId={}, Polling for files from source={}",
            "Route has been started"),
    ROUTESTOPPED(
            "event=CamelRouteStopped, Camel Route with routeId={} stopped, routeEndPoint={}",
            "Camel Route was stopped"),
    ROUTEREMOVED(
            "event=CamelRouteRemoved, Camel Route with routeId={} was removed. routeEndPoint={}",
            "Camel Route was removed");




    private String logMessage;
    private String eventDescription;// For informational purpose only, describe
                                    // what
    // the event is for.

    /**
     * Constructor for the enum
     *
     * @param logMessage
     * @param eventDescription
     */
    private LoggingEvents(String logMessage, String eventDescription) {
        this.logMessage = logMessage;
        this.eventDescription = eventDescription;
    }

    /**
     * @return Message to be logged corresponding to the Event
     */
    public String getLogMessage() {
        return logMessage;
    }

}
