package net.fordok.aws.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fordok on 12/2/2015.
 */
public class CloudManagerAWS implements CloudManager {

    AmazonEC2Client ec2 = null;

    @Override
    public void init(String endpoint) {
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        ec2 = new AmazonEC2Client(credentials);
        ec2.setEndpoint(endpoint);
    }

    @Override
    public List<Instance> launchInstances(int count) {
        try {
            RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
            runInstancesRequest.withImageId("ami-d9a505aa")
                    .withInstanceType("t1.micro")
                    .withMinCount(count)
                    .withMaxCount(count)
                    .withKeyName("amazon");
            RunInstancesResult runInstancesResult = ec2.runInstances(runInstancesRequest);
            for (Instance instance : runInstancesResult.getReservation().getInstances()) {
                System.out.println("instance : " + instance.getInstanceId() + " state : " + instance.getState());
            }
            return runInstancesResult.getReservation().getInstances();
        } catch (AmazonServiceException ase) {
            System.out.println("Caught Exception: " + ase.getMessage());
            System.out.println("Response Status Code: " + ase.getStatusCode());
            System.out.println("Error Code: " + ase.getErrorCode());
            System.out.println("Request ID: " + ase.getRequestId());
        }
        return null;
    }

    @Override
    public List<Instance> getAllInstancesInfo() {
        DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
        List<Reservation> reservations = describeInstancesResult.getReservations();
        List<Instance> instances = new ArrayList<>();
        for (Reservation reservation : reservations) {
            instances.addAll(reservation.getInstances());
        }
        return instances;
    }

    @Override
    public void terminateInstance(String instanceId) {
        ArrayList<String> instanceIds = new ArrayList<>();
        instanceIds.add(instanceId);
        try {
            TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest(instanceIds);
            ec2.terminateInstances(terminateRequest);
            System.out.println("Terminating : " + instanceIds);
        } catch (AmazonServiceException e) {
            System.out.println("Error terminating instances");
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Response Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
    }

    @Override
    public void terminateAllInstances() {
        ArrayList<String> instanceIds = new ArrayList<>();
        try {
            DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
            List<Reservation> reservations = describeInstancesResult.getReservations();
            Set<Instance> instances = new HashSet<>();
            for (Reservation reservation : reservations) {
                instances.addAll(reservation.getInstances());
            }
            for (Instance instance : instances) {
                System.out.println("instance : " + instance.getInstanceId() + " state : " + instance.getState());
                instanceIds.add(instance.getInstanceId());
            }
            TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest(instanceIds);
            ec2.terminateInstances(terminateRequest);
            System.out.println("Terminating : " + instanceIds);
        } catch (AmazonServiceException e) {
            // Write out any exceptions that may have occurred.
            System.out.println("Error terminating instances");
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Response Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
    }

    @Override
    public void executeCommandForInstance(String command, String publicIp) {
        JSch jsch = new JSch();
        try {
            File keyFile = new File( this.getClass().getResource("/amazon.pem").toURI());
            jsch.addIdentity(keyFile.getAbsolutePath());
            jsch.setConfig("StrictHostKeyChecking", "no");
            Session session = jsch.getSession("ec2-user", publicIp, 22);
            session.connect();
            ChannelExec channel = (ChannelExec)session.openChannel("exec");
            BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            channel.setCommand(command);
            channel.connect();
            String msg = null;
            while((msg = in.readLine()) != null){
                System.out.println(msg);
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
