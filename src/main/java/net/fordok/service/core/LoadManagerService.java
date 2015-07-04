package net.fordok.service.core;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.core.LoadGenerator;
import net.fordok.core.LoadGeneratorImpl;
import net.fordok.service.configuration.ServiceConfiguration;
import net.fordok.service.resource.ManagerResource;
import net.fordok.service.resource.StatusResource;
import net.fordok.work.HttpWork;

/**
 * Created by fordok on 7/4/2015.
 */
public class LoadManagerService extends Application<ServiceConfiguration> {

    private LoadGenerator loadGenerator;

    public static void main(String[] args) throws Exception {
        new LoadManagerService().run(new String[] { "server" });
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        LoadGenerator loadGenerator = new LoadGeneratorImpl();
        loadGenerator.init(new ConfigurationSystem(1, 1000, 100, new HttpWork("HttpWork", "http://google.com", "GET")));
        this.loadGenerator = loadGenerator;
    }

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) throws Exception {
        final StatusResource statusResource = new StatusResource();
        environment.jersey().register(statusResource);
        final ManagerResource managerResource = new ManagerResource(loadGenerator);
        environment.jersey().register(managerResource);
    }
}
