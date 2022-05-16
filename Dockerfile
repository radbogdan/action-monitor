FROM openjdk:17-oracle
COPY target/action-monitor-1.0.0.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]