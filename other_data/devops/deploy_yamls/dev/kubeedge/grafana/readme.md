docker build -t grafana:v1.3 .

docker tag grafana:v1.3 core.harbor.cz.shenlan.com/dbp-edu/grafana:v1.3

docker login -u dbp-edu -p D8jWTYOI2q core.harbor.cz.shenlan.com

docker push core.harbor.cz.shenlan.com/dbp-edu/grafana:v1.3

