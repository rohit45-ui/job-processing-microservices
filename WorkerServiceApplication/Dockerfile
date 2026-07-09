FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/workerservice-0.0.1-SNAPSHOT.war app.war

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.war"]