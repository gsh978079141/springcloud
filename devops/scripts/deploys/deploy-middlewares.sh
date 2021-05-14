echo "==================BEGIN DEPLOY MIDDLEWARES START=================="

source config.properties

echo "create name space education "
kubectl create -n education

echo "apply  docker-secret-default "
kubectl apply -f $TEMPOUTPUTDIR/docker-secret-default.yaml

echo "apply  docker-secret-education "
kubectl apply -f $TEMPOUTPUTDIR/docker-secret-education.yaml

echo "apply  cloud mysql "
kubectl apply -f $TEMPOUTPUTDIR/cloud-mysql.yaml

echo "apply  edge mysql "
kubectl apply -f $TEMPOUTPUTDIR/edge-mysql.yaml

echo "apply cloud postgres "
kubectl apply -f $TEMPOUTPUTDIR/cloud-postgres.yaml


echo "==================END DEPLOY MIDDLEWARES START=================="
