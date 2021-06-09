#!/bin/bash

showUsage() {
    echo "Usage:"
    echo "  debloy.sh [-t IMAGE_TAG]"
    echo "Description:"
    echo "  IMAGE_TAG: the tag of docker image."
    exit -1
}

while getopts 't:' OPT; do
    case $OPT in
        t) IMAGE_TAG="$OPTARG";;
        h) showUsage;;
        ?) showUsage;;
    esac
done

if [ ! $IMAGE_TAG ]; then  
  echo "请用-t参数指定要构建的镜像标签" 
  showUsage
fi

docker build  -t core.harbor.cz.shenlan.com/dbp-edu/edge-nginx-proxy:latest  .
docker tag core.harbor.cz.shenlan.com/dbp-edu/edge-nginx-proxy:latest  core.harbor.cz.shenlan.com/dbp-edu/edge-nginx-proxy:$IMAGE_TAG
docker push core.harbor.cz.shenlan.com/dbp-edu/edge-nginx-proxy:latest
docker push core.harbor.cz.shenlan.com/dbp-edu/edge-nginx-proxy:$IMAGE_TAG
