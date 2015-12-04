package net.fordok.node.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.fordok.generator.core.LoadGenerator;
import net.fordok.generator.core.LoadGeneratorImpl;
import net.fordok.node.configuration.NodeConfiguration;
import net.fordok.node.resource.GeneratorResource;

/**
 * Created by fordok on 12/4/2015.
 */
public class NodeService extends Application<NodeConfiguration> {

    private LoadGenerator loadGenerator;

    public static void main(String[] args) throws Exception {
        new NodeService().run(args);
    }

    @Override
    public void initialize(Bootstrap<NodeConfiguration> bootstrap) {
        loadGenerator = new LoadGeneratorImpl();
    }

    @Override
    public void run(NodeConfiguration nodeConfiguration, Environment environment) throws Exception {
        final GeneratorResource generatorResource = new GeneratorResource(loadGenerator);
        environment.jersey().register(generatorResource);
        ObjectMapper mapper = environment.getObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
}
