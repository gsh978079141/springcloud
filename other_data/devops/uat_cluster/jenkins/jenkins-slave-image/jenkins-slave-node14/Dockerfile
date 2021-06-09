FROM jenkins/inbound-agent:4.7-1
USER root
# 安装 kubernetes-client
RUN mkdir /opt/kubernetes && cd /opt/kubernetes \
  && wget https://dl.k8s.io/v1.19.0/kubernetes-client-linux-amd64.tar.gz \
  && tar -zxv -f kubernetes-client-linux-amd64.tar.gz \
  && cp kubernetes/client/bin/kube* /usr/bin/ \
  && chmod a+x /usr/bin/kube* \
  && rm -rf kubernetes kubernetes-client-linux-amd64.tar.gz

RUN apt update && apt-get install -y curl software-properties-common \
  && curl -sL https://deb.nodesource.com/setup_14.x | bash - \
  && apt-get install -y nodejs 

RUN npm install --global yarn \
  && yarn config set registry https://registry.npm.taobao.org/ \
  && yarn config set cache-folder /home/jenkins/.yarn_cache

USER jenkins