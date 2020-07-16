package com.infosys.osdm.ds.ingestionservices.util;

import java.util.HashMap;
import java.util.Map;

public class Destination {

	
	private final Map<String, String> options = new HashMap<>();
	private String hosts;
	private int port;
	private String keyspace;
	private String sql;
	
	
	public Map<String, String> getOptions() {
		return options;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	

	
	
}
