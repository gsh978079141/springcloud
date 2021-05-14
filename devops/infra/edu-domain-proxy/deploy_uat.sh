IMAGE_TAG="v3.0.2"

docker build  -t core.harbor.cz.shenlan.com/dbp-edu/edu-uat-domain-proxy:latest -f Dockerfile_uat  .
docker tag core.harbor.cz.shenlan.com/dbp-edu/edu-uat-domain-proxy:latest core.harbor.cz.shenlan.com/dbp-edu/edu-uat-domain-proxy:$IMAGE_TAG
docker push core.harbor.cz.shenlan.com/dbp-edu/edu-uat-domain-proxy:latest
docker push core.harbor.cz.shenlan.com/dbp-edu/edu-uat-domain-proxy:$IMAGE_TAG
