FROM nginx:1.15.2-alpine

RUN mkdir /var/log/nginx/pid

RUN chmod -R 777 /var/log/nginx

RUN chmod -R 777 /etc/nginx

COPY nginx.conf /etc/nginx/nginx.conf

WORKDIR /usr/share/nginx/html

ADD ./build .

COPY env.sh .

COPY .env .

RUN chmod -R 777 .

RUN cp /etc/apk/repositories /etc/apk/repositories.bak

RUN echo "http://mirrors.aliyun.com/alpine/v3.4/main/" > /etc/apk/repositories

RUN apk add --upgrade apk-tools

RUN apk add --no-cache bash

ENTRYPOINT ["/bin/bash", "-c",  "/usr/share/nginx/html/env.sh && nginx -g \"daemon off;\""]
