IMG_NAME=jenkins-slave-jdk11
IMAG_TAG=v1
docker build -t ${IMG_NAME}:${IMAG_TAG} .
docker tag ${IMG_NAME}:${IMAG_TAG} core.harbor.cz.shenlan.com/dbp-edu/${IMG_NAME}:${IMAG_TAG}
docker tag ${IMG_NAME}:${IMAG_TAG} core.harbor.cz.shenlan.com/dbp-edu/${IMG_NAME}:latest
docker push core.harbor.cz.shenlan.com/dbp-edu/${IMG_NAME}:${IMAG_TAG}
docker push core.harbor.cz.shenlan.com/dbp-edu/${IMG_NAME}:latest