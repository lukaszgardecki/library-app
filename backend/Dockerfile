FROM openjdk:18-alpine3.14
ADD target/library-app.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-app.jar"]