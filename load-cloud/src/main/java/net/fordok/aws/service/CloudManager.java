package net.fordok.aws.service;

import com.amazonaws.services.ec2.model.Instance;

import java.util.List;

/**
 * Created by fordok on 12/2/2015.
 */
public interface CloudManager {
    void init(String endpoint);
    List<Instance> launchInstances(int count);
    void terminateInstance(String instanceId);
    void terminateAllInstances();
}
