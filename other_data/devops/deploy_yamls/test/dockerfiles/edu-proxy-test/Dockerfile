FROM nginx:1.15.2-alpine

RUN mkdir /var/log/nginx/pid

RUN chmod -R 777 /var/log/nginx

RUN chmod -R 777 /etc/nginx

RUN chmod -R 777 /tmp

COPY nginx-proxy.conf /etc/nginx/nginx.conf

WORKDIR /usr/share/nginx/html



RUN chmod -R 777 .

RUN cp /etc/apk/repositories /etc/apk/repositories.bak

RUN echo "http://mirrors.aliyun.com/alpine/v3.4/main/" > /etc/apk/repositories

RUN apk add --upgrade apk-tools

RUN apk add --no-cache bash

ENTRYPOINT ["/bin/bash", "-c",  "nginx -g \"daemon off;\""]
