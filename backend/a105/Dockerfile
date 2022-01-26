FROM openjdk:11-jdk
WORKDIR /server

COPY ./build/libs/a105-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
