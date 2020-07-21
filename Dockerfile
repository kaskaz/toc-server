FROM openjdk:14-alpine
COPY build/libs/tocserver-*-all.jar tocserver.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "tocserver.jar"]