## dev环境部署
dev部署分两部分，
一部分在rancher集群，只部署了一个nginx转发服务， 
dev-edu.deepblueai.com/ 下的请求全都转发到 192.168.27.17 的nginx下

192.168.27.17 这台机器上以docker-compose方式部署了多个服务,
位于 /opt/docker_compose_yamls/ 目录下, 分为infra,app,web三个文件部署，
mysql单独部署在其它服务器，
redis部署在本地，以系统服务方式部署



