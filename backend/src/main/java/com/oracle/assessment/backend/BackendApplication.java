package com.oracle.assessment.backend;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;

import java.util.EnumSet;
import java.util.List;
import java.util.StringJoiner;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.oracle.assessment.backend.resources.CoinResource;
import com.oracle.assessment.backend.service.CoinCalculatorService;

public class BackendApplication extends Application<BackendConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BackendApplication().run(args);
    }

    @Override
    public String getName() {
        return "backend";
    }

    @Override
    public void initialize(final Bootstrap<BackendConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BackendConfiguration configuration,
                    final Environment environment) {
        // Register service and jersey
    	CoinCalculatorService service = new CoinCalculatorService();
    	environment.jersey().register(new CoinResource(service));
    	
    	// CORS configuration
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        List<String> allowedOrigins = configuration.getAllowedOrigins();

        String allowedOriginsParam = "*"; // default allow all if list is empty

        if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            allowedOrigins.forEach(joiner::add);
            allowedOriginsParam = joiner.toString();
        }

        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, allowedOriginsParam);
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}
