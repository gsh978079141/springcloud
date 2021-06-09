FROM jenkins/inbound-agent:4.7-1
USER root
# 安装 kubernetes-client
RUN mkdir /opt/kubernetes && cd /opt/kubernetes \
  && wget https://dl.k8s.io/v1.19.0/kubernetes-client-linux-amd64.tar.gz \
  && tar -zxv -f kubernetes-client-linux-amd64.tar.gz \
  && cp kubernetes/client/bin/kube* /usr/bin/ \
  && chmod a+x /usr/bin/kube* \
  && rm -rf kubernetes kubernetes-client-linux-amd64.tar.gz

# 安装 maven 和 kubernetes-client
RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list \ 
  && apt update && apt install -y maven && apt-get clean all

COPY settings.xml /usr/share/maven/conf/settings.xml

USER jenkins