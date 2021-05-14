FROM java:8-jdk-alpine
MAINTAINER gate
WORKDIR /tmp
ADD target/service-account.jar app.jar
RUN sh -c 'touch /app.jar'
ADD initialization.sh initialization.sh
RUN chmod 777 initialization.sh
RUN ./initialization.sh
ENV JAVA_OPTS="-Duser.timezone=GMT+08"
ENV TIME_ZONE="Asia/Shanghai"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
EXPOSE 6001