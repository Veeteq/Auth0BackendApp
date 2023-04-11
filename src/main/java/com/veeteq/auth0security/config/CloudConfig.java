package com.veeteq.auth0security.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth0")
public class CloudConfig {
	
	private String message;
	private Map<String, String> msgPattern;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getMsgPattern() {
		return msgPattern;
	}

	public void setMsgPattern(Map<String, String> msgPattern) {
		this.msgPattern = msgPattern;
	}
	
}
