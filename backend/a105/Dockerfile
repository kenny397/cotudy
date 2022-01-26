FROM openjdk:11-jdk
WORKDIR /server

COPY ./build/libs/jenkins-cicd-todo-0.0.1-SNAPSHOT.jar todo-server.jar

ENTRYPOINT ["java", "-jar", "todo-server.jar"]
