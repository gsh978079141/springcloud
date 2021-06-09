此目录存放 uat-cluster 基础设施的部署文件。
包括:
* jenkins集群
* ELK集群

```bash
kubectl create secret docker-registry harbor-secret --docker-server=core.harbor.cz.shenlan.com --docker-username=dbp-edu --docker-password=D8jWTYOI2q -n edu-edge
```