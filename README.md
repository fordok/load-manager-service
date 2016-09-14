# load-manager-service

REST service for managing of load generator based on Akka framework.

Components:

load-cloud: 
  wrapper arounf AWS SDK to manage AWS instances.
load-generator:
  load generator based on Akka framework
load-node:
  REST service for managing load generator workflow by HTTP.
load-service:
  REST service for managing tasks / runs / types / AWS instances
  Web part
  
Tech Stack:
  REST service - Dropwizard (Jetty, Jersey, Jackson)
  AWS SDK
  Akka framework 
  Java 8
  Joda time
  AngularJS
