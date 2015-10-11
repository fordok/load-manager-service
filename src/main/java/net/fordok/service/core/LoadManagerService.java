package net.fordok.service.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.core.LoadGenerator;
import net.fordok.core.LoadGeneratorImpl;
import net.fordok.service.configuration.ServiceConfiguration;
import net.fordok.service.resource.ManagerResource;
import net.fordok.service.resource.SessionResource;
import net.fordok.service.resource.StatusResource;
import net.fordok.service.resource.TaskResource;
import net.fordok.service.storage.Storage;
import net.fordok.service.storage.StorageImpl;
import net.fordok.work.HttpWork;

/**
 * Created by fordok on 7/4/2015.
 */
public class LoadManagerService extends Application<ServiceConfiguration> {

    private LoadGenerator loadGenerator;
    private Storage storage;

    public static void main(String[] args) throws Exception {
        new LoadManagerService().run(new String[] { "server" });
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        LoadGenerator loadGenerator = new LoadGeneratorImpl();
        loadGenerator.init(new ConfigurationSystem(1, 1000, 100, new HttpWork("HttpWork", "http://google.com", "GET")));
        this.loadGenerator = loadGenerator;
        this.storage = new StorageImpl();
    }

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) throws Exception {
        final StatusResource statusResource = new StatusResource();
        environment.jersey().register(statusResource);
        final ManagerResource managerResource = new ManagerResource(loadGenerator);
        environment.jersey().register(managerResource);
        final SessionResource sessionResource = new SessionResource(storage);
        environment.jersey().register(sessionResource);
        final TaskResource taskResource = new TaskResource(storage);
        environment.jersey().register(taskResource);
        ObjectMapper mapper = environment.getObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
