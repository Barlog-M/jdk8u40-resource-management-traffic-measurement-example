JDK 8u40 Resource Management Traffic Measurement Example
=====================

[Documentation](http://docs.oracle.com/javase/8/docs/jre/api/management/rm/jdk/management/resource/package-summary.html)

[JVMLS 2015 - Resource Tracking Techniques](http://www.youtube.com/watch?v=0WZpmylHbFM)

Resource Management available since 8u40 in Commercial Features

JVM Options for run: -XX:+UnlockCommercialFeatures -XX:+ResourceManagement

### Run from Command Line ###
```
java -XX:+UnlockCommercialFeatures -XX:+ResourceManagement -jar target/resource-management-maven-0.1-SNAPSHOT-jar-with-dependencies.jar
```

### Run from Maven ###
```
mvn compile exec:exec
```

### Create executable jar with dependencies ###
```
mvn package assembly:single
```

### Update plugins and dependencies
```
mvn versions:update-properties
```
