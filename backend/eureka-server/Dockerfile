FROM eclipse-temurin:24-jdk
ADD target/eureka-server.jar .
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]