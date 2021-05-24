FROM sebp/elk:791

COPY 02-beats-input-cloud.conf /etc/logstash/conf.d/02-beats-input.conf

RUN rm -rf /etc/logstash/conf.d/30-output.conf

