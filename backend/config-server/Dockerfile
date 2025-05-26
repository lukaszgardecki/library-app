FROM openjdk:24-jdk
ADD target/config-server.jar .
EXPOSE 8079
ENTRYPOINT ["java", "-jar", "config-server.jar"]