package edu.study.giya.controller.validator;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("edu.study.giya");
    }

}
