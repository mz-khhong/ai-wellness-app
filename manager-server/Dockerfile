FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY manager-server/build/libs/manager-server-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

