FROM adoptopenjdk/openjdk8:alpine as builder
WORKDIR /tmp
RUN wget --no-check-certificate https://mirrors.bfsu.edu.cn/apache/skywalking/8.5.0/apache-skywalking-apm-es7-8.5.0.tar.gz && tar -zxvf apache-skywalking-apm-es7-8.5.0.tar.gz

FROM adoptopenjdk/openjdk8:alpine
COPY --from=builder /tmp/apache-skywalking-apm-bin-es7/agent /usr/skywalking/agent
RUN mkdir /opt/app

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