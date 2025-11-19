FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY customer-server/build/libs/customer-server-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]

