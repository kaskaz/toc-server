ARG JAR_FILE=/home/gradle/build/libs/tocserver-*-all.jar

# Build stage
FROM gradle:7.1.0-jdk11
COPY . .
RUN ./gradlew formatKotlin
RUN ./gradlew build -x :test

# Package stage
FROM openjdk:14-alpine
COPY --from=0 ${JAR_FILE} tocserver.jar
EXPOSE 8080
CMD ["java", "-Xmx128m", "-jar", "tocserver.jar"]
