FROM openjdk:18.0.2-jdk-slim

WORKDIR /app

COPY ../../gradlew .
COPY ../../gradle ./gradle
COPY ../../build.gradle .
COPY ../../settings.gradle .
RUN chmod +x gradlew

RUN ./gradlew build || return 0

CMD ["./gradlew", "bootRun"]
