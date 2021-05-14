FROM adoptopenjdk/openjdk8:alpine
MAINTAINER deepblue-edu
WORKDIR /opt/app

ENV TIME_ZONE=Asia/Shanghai \
  LANG=en_US.UTF-8 \
  LANGUAGE=en_US.UTF-8 \
  LC_ALL=en_US.UTF-8 \
  JAVA_OPTS="-Duser.timezone=GMT+08" \
  SKYWALKING_AGENT_SERVICE_NAME="" \
  SKYWALKING_COLLECTOR_BACKEND_SERVICE=""

RUN apk add --no-cache tzdata \
  && echo "${TIME_ZONE}" > /etc/timezone \
  && ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime

ADD target/gateway-api.jar app.jar
RUN touch app.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]
EXPOSE 8001
