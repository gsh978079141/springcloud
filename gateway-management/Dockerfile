FROM java:8-jdk-alpine
MAINTAINER edu
WORKDIR /tmp
ADD target/gateway-management.jar app.jar
RUN sh -c 'touch /app.jar'
ADD initialization.sh initialization.sh
RUN chmod 777 initialization.sh
RUN ./initialization.sh
ENV JAVA_OPTS="-Duser.timezone=GMT+08"
ENV TIME_ZONE="Asia/Shanghai"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
EXPOSE 8002