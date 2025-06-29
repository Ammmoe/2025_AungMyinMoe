package com.oracle.assessment.backend;

import java.util.List;

import io.dropwizard.core.Configuration;

public class BackendConfiguration extends Configuration {

	private List<String> allowedOrigins;

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
