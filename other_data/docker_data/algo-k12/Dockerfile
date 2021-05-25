FROM k12:V3.5
MAINTAINER gsh
WORKDIR /datav/k12
ENV TIME_ZONE="Asia/Shanghai"
ADD requirements.txt requirements.txt
RUN pip install -r requirements.txt
ADD initialization.sh initialization.sh
RUN chmod 777 initialization.sh
RUN ./initialization.sh
ENTRYPOINT ["sh", "-c", "python /core/workspace/main.dev.py && python /core/workspace/logical.master.dev.py"]

