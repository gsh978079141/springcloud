IMAGE_TAG=8.5.0

docker build  -t core.harbor.cz.shenlan.com/dbp-edu/jdk11-skywalking-agent:latest .
docker tag core.harbor.cz.shenlan.com/dbp-edu/jdk11-skywalking-agent:latest core.harbor.cz.shenlan.com/dbp-edu/jdk11-skywalking-agent:$IMAGE_TAG
echo "build docker image [jdk11-skywalking-agent] success..."

docker push core.harbor.cz.shenlan.com/dbp-edu/jdk11-skywalking-agent:latest
docker push core.harbor.cz.shenlan.com/dbp-edu/jdk11-skywalking-agent:$IMAGE_TAG
echo "push image [jdk11-skywalking-agent] success，tag:$IMAGE_TAG, latest"