nacos部署

# 规划
创建namespace: 
````
kubectl create namespace nacos
````
# 存储
搭建mysql数据库，创建nacos用户，授予nacos_devtest库权限:
````
create user 'nacos'@'%' identified by 'nacos';
grant all privileges on nacos_devtest.* to 'nacos' @'%' identified by 'nacos';
flush privileges;
````
mysql数据库中创建nacos_devtest库，使用nacos-mysql.sql创建数据表.

