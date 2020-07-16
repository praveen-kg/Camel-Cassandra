package com.infosys.osdm.ds.ingestionservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * DataHubIntegrationServicesApplication to boot the application
 *
 */
@SpringBootApplication
public class DataIntegrationServicesApplication {

	/**
	 * 
	 * it will be called at spring start up
	 * @param args the application arguments (usually passed from a Java main method)
	 */
    public static void main(String[] args) {
        SpringApplication.run(DataIntegrationServicesApplication.class,
                args);
    }
    
  
}
