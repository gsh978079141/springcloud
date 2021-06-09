## mysql部署
### 安装方式
使用yum安装，选择5.7版本
````
rpm -ivh https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm
yum install mysql-community-server
rpm -qa | grep mysql
````
### 启动mysql服务,查看mysql启动状态
````
systemctl start mysqld
systemctl status mysqld
````
开机启动
````
systemctl enable mysqld
````

### 修改配置文件
打开mysql配置文件
````
vi /etc/my.cnf
````
重载所有修改过的配置文件
````
systemctl daemon-reload
````
### 修改默认root账户密码
获取mysql默认密码
````
grep 'temporary password' /var/log/mysqld.log
````
登录mysql后,修改密码:
````
alter user 'root'@'localhost' identified by 'deepblue';
grant all privileges on *.* to 'root' @'%' identified by 'deepblue';   # 允许远程访问MySQL
flush privileges;
````
账户/密码: root/deepblue,nacos/nacos


