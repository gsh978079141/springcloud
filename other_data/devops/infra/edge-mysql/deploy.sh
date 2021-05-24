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

echo "start building docker image, core.harbor.cz.shenlan.com/dbp-edu/edu-edge-mysql, tags: [latest, $IMAGE_TAG]"

echo "clear existing init sql"
rm ./sqls/*.sql
echo "copy init_sqls from backend/sqls/edge/current"
cp  ../../../backend/sqls/edge/current/*.sql  sqls/
echo "current init sqls:" 
echo `ls ./sqls`

docker build  -t core.harbor.cz.shenlan.com/dbp-edu/edu-edge-mysql:latest .
docker tag core.harbor.cz.shenlan.com/dbp-edu/edu-edge-mysql:latest core.harbor.cz.shenlan.com/dbp-edu/edu-edge-mysql:$IMAGE_TAG
echo "build docker image success..."

docker push core.harbor.cz.shenlan.com/dbp-edu/edu-edge-mysql:latest
docker push core.harbor.cz.shenlan.com/dbp-edu/edu-edge-mysql:$IMAGE_TAG
echo "push image success，tag:$IMAGE_TAG, latest"


