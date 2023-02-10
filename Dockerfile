FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=./target/xm-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./xm-app.jar
ENTRYPOINT ["java","-jar","/xm-app.jar"]
