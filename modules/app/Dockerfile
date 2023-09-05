FROM adoptopenjdk/openjdk16 AS builder

COPY ./gradlew .
COPY ./gradle gradle
COPY ./build.gradle .
COPY ./settings.gradle .
COPY ./modules modules

RUN chmod +x ./gradlew
RUN ./gradlew :app:clean :app:bootJar

FROM adoptopenjdk/openjdk16
COPY --from=builder /modules/app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar

