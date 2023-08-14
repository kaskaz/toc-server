FROM openjdk:14-alpine
RUN ./gradlew clean build
COPY build/libs/tocserver-*-all.jar tocserver.jar
EXPOSE 8080
CMD ["java", "-Xmx128m", "-jar", "tocserver.jar"]
