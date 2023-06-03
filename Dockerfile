FROM openjdk:17-alpine3.14
ADD target/library-app-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-app-0.0.1-SNAPSHOT.jar"]