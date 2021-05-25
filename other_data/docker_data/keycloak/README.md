<h4>**1：docker run模式启动：**</h4>
docker network create keycloak-network<br/>
docker run -d --name keycloak -p 8088:8080 -p 9990:9990 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin  --net keycloak-network jboss/keycloak<br/>
docker exec 4a89775187d0 /opt/jboss/keycloak/bin/add-user-keycloak.sh -u admin -p admin<br/>
docker run -d -p 8080:8080 --name keycloak -e DB_VENDOR=mysql -e DB_USER=root -e DB_PASSWORD=root -e DB_ADDR=192.168.18.134 -e DB_PORT=3306 -e DB_DATABASE=keycloak -e JDBC_PARAMS='connectTimeout=30' jboss/keycloak<br/>
