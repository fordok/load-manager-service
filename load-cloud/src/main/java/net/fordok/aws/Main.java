package net.fordok.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fordok on 11/19/2015.
 */
public class Main {

    static AmazonEC2 ec2;

    private static void init() throws Exception {
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
        ec2.setEndpoint("ec2.eu-west-1.amazonaws.com");
//        AmazonAutoScalingClient autoScaling = new AmazonAutoScalingClient(credentials);
//        autoScaling.setEndpoint("ec2.eu-west-1.amazonaws.com");
    }


    public static void main(String[] args) throws Exception {
        init();
        ArrayList<String> instanceIds = new ArrayList<>();
        try {

            RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
            runInstancesRequest.withImageId("ami-6053f613")
                    .withInstanceType("t1.micro")
                    .withMinCount(2)
                    .withMaxCount(2)
                    .withKeyName("amazon");
            RunInstancesResult runInstancesResult = ec2.runInstances(runInstancesRequest);
            System.out.println(runInstancesResult.getReservation().getInstances());

            DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
            System.out.println("You have access to " + availabilityZonesResult.getAvailabilityZones());

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

            System.out.println("You have " + instances.size() + " Amazon EC2 instance(s) running.");
        } catch (AmazonServiceException ase) {
            System.out.println("Caught Exception: " + ase.getMessage());
            System.out.println("Reponse Status Code: " + ase.getStatusCode());
            System.out.println("Error Code: " + ase.getErrorCode());
            System.out.println("Request ID: " + ase.getRequestId());
        }



        //============================================================================================//
        //=================================== Terminating any Instances ==============================//
        //============================================================================================//
        try {
            // Terminate instances.
            TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest(instanceIds);
            ec2.terminateInstances(terminateRequest);
            System.out.println("Terminating : " + instanceIds);
        } catch (AmazonServiceException e) {
            // Write out any exceptions that may have occurred.
            System.out.println("Error terminating instances");
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Reponse Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
    }
}
