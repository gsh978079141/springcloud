FROM openjdk:8
WORKDIR /usr/local/docker/sentinel
ADD ./jar/sentinel-dashboard-1.8.2.jar sentinel.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "sentinel.jar", "--server.port=8088"]
