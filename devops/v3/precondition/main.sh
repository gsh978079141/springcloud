# 创建namespace: 云端和边缘端

kubectl create namespace edu-cloud
kubectl create namespace edu-edge

# 创建 docker-registry secret: harbor-secret
kubectl create secret docker-registry harbor-secret --docker-server=core.harbor.cz.shenlan.com --docker-username=dbp-edu --docker-password=D8jWTYOI2q -n edu-cloud
kubectl create secret docker-registry harbor-secret --docker-server=core.harbor.cz.shenlan.com --docker-username=dbp-edu --docker-password=D8jWTYOI2q -n edu-edge


# 创建serviceaccount: education
# kubectl apply -f serviceaccount-education.yaml

