docker build -t elk:v1.1 .

docker tag elk:v1.1 core.harbor.cz.shenlan.com/dbp-edu/elk:v1.1

docker login -u dbp-edu -p D8jWTYOI2q core.harbor.cz.shenlan.com

docker push core.harbor.cz.shenlan.com/dbp-edu/elk:v1.1







Cloud 

docker build -t elk_cloud:v1.0 .

docker tag elk_cloud:v1.0 core.harbor.cz.shenlan.com/dbp-edu/elk_cloud:v1.0

docker login -u dbp-edu -p D8jWTYOI2q core.harbor.cz.shenlan.com

docker push core.harbor.cz.shenlan.com/dbp-edu/elk_cloud:v1.0

