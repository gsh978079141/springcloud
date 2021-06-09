IMAGE_TAG="v2.0.1"

docker build  -t core.harbor.cz.shenlan.com/dbp-edu/edu-domain-proxy:latest  .
docker tag core.harbor.cz.shenlan.com/dbp-edu/edu-domain-proxy:latest core.harbor.cz.shenlan.com/dbp-edu/edu-domain-proxy:$IMAGE_TAG
docker push core.harbor.cz.shenlan.com/dbp-edu/edu-domain-proxy:latest
docker push core.harbor.cz.shenlan.com/dbp-edu/edu-domain-proxy:$IMAGE_TAG
