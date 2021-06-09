IMAGE_TAG=8.5.0
IMAGE_NAME=jdk8-skywalking-agent

docker build  -t core.harbor.cz.shenlan.com/dbp-edu/$IMAGE_NAME:latest .
docker tag core.harbor.cz.shenlan.com/dbp-edu/$IMAGE_NAME:latest core.harbor.cz.shenlan.com/dbp-edu/$IMAGE_NAME:$IMAGE_TAG
echo "build docker image [$IMAGE_NAME] success..."

docker push core.harbor.cz.shenlan.com/dbp-edu/$IMAGE_NAME:latest
docker push core.harbor.cz.shenlan.com/dbp-edu/$IMAGE_NAME:$IMAGE_TAG
echo "push image [$IMAGE_NAME] success，tag:$IMAGE_TAG, latest"