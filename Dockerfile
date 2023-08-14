# Build stage
FROM gradle:7.1.0-jdk11
COPY . .
RUN ./gradlew formatKotlin
RUN ./gradlew build -x :test
RUN dir

# Package stage
FROM openjdk:14-alpine
COPY --from=0 /build/libs/tocserver-*-all.jar tocserver.jar
EXPOSE 8080
CMD ["java", "-Xmx128m", "-jar", "tocserver.jar"]
