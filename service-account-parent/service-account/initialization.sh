#!/bin/sh
#cp /etc/apk/repositories /etc/apk/repositories.bak
#echo "http://mirrors.aliyun.com/alpine/v3.4/main/" > /etc/apk/repositories
#apk add --upgrade apk-tools
#apk add -U tzdata chrony ttf-dejavu
echo "${TIME_ZONE}" > /etc/timezone
ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime
#chronyd
#apk del tzdata