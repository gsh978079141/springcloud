FROM jenkins/slave:latest-jdk11

USER root
# 安装 mavne 和 kubernetes-client
RUN apt update && apt install -y maven && mkdir /opt/kubernetes && cd /opt/kubernetes \
  && wget https://dl.k8s.io/v1.19.0/kubernetes-client-linux-amd64.tar.gz \
  && tar -zxv -f kubernetes-client-linux-amd64.tar.gz \
  && cp kubernetes/client/bin/kube* /usr/bin/ \
  && chmod a+x /usr/bin/kube* \
  && rm -rf kubernetes kubernetes-client-linux-amd64.tar.gz

COPY settings.xml /home/jenkins/.m2/settings.xml

USER jenkins