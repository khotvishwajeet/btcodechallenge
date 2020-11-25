# For Java 8
FROM openjdk:8-jdk-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/springboot-lightbulb-crud-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /usr/local/runme

# cp target/springboot-lightbulb-crud-0.0.1-SNAPSHOT.jar /usr/local/runme/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /usr/local/runme/app.jar
ENTRYPOINT ["java","-jar","app.jar"]