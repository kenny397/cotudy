FROM openjdk:11-jdk AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x gradlew
RUN ["./gradlew", "clean", "build", "--stacktrace", "--exclude-task", "test"]

# 2. 빌드 된 파일을 배포용 이미지에 복사
FROM openjdk:11-jdk
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar
