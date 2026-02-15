FROM eclipse-temurin:24-jdk
ADD target/payment-service.jar .
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "payment-service.jar"]