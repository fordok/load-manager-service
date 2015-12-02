package net.fordok.service.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.fordok.aws.service.CloudManager;
import net.fordok.aws.service.CloudManagerAWS;
import net.fordok.generator.core.LoadGenerator;
import net.fordok.generator.core.LoadGeneratorImpl;
import net.fordok.service.configuration.ServiceConfiguration;
import net.fordok.service.resource.CloudResource;
import net.fordok.service.resource.RunResource;
import net.fordok.service.resource.TaskResource;
import net.fordok.service.resource.TypeResource;
import net.fordok.service.storage.Storage;
import net.fordok.service.storage.StorageImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fordok on 7/4/2015.
 */
public class LoadManagerService extends Application<ServiceConfiguration> {

    private LoadGenerator loadGenerator;
    private Storage storage;
    private CloudManager cloudManager;

    public static void main(String[] args) throws Exception {
        new LoadManagerService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/html", "/"));
        LoadGenerator loadGenerator = new LoadGeneratorImpl();
        List<String> seeds = new ArrayList<>();
        seeds.add("127.0.0.1");
        loadGenerator.init("127.0.0.1", "2551", seeds);
        this.loadGenerator = loadGenerator;
        this.storage = new StorageImpl();
        CloudManager cloudManager = new CloudManagerAWS();
        cloudManager.init("ec2.eu-west-1.amazonaws.com");
        this.cloudManager = cloudManager;
    }

    @Override
    public void run(ServiceConfiguration serviceConfiguration, Environment environment) throws Exception {
        final TaskResource taskResource = new TaskResource(storage);
        environment.jersey().register(taskResource);
        final TypeResource typeResource = new TypeResource(storage);
        environment.jersey().register(typeResource);
        final RunResource runResource = new RunResource(storage, loadGenerator);
        environment.jersey().register(runResource);
        final CloudResource cloudResource = new CloudResource(cloudManager);
        environment.jersey().register(cloudResource);
        ObjectMapper mapper = environment.getObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
