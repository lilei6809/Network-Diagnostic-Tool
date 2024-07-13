FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/network-diagnostic-tool-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]

HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8080/actuator/health || exit 1

LABEL maintainer="LL"
LABEL version="1.0"
LABEL description="Network Diagnostic Tool Docker Image"