package net.fordok.service.core;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.fordok.service.configuration.ServiceConfiguration;
import net.fordok.service.resource.StatusResource;

/**
 * Created by fordok on 7/4/2015.
 */
public class LoadManagerService extends Application<ServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new LoadManagerService().run(new String[] { "server" });
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {

    }

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) throws Exception {
        final StatusResource statusResource = new StatusResource();
        environment.jersey().register(statusResource);
    }
}
