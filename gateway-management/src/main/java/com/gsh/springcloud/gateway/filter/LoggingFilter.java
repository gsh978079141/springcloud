package com.gsh.springcloud.gateway.filter;

import com.gsh.springcloud.common.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局日志filter
 *
 * @author jun
 */
@Slf4j
@Configuration
public class LoggingFilter implements GlobalFilter, Ordered {

  private final static String START_TIME_KEY = "start_time";

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    exchange.getAttributes().put(START_TIME_KEY, System.currentTimeMillis());
    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
      Long startTime = exchange.getAttribute(START_TIME_KEY);
      long cost = -1L;
      if (startTime == null) {
        log.warn("not found [start_time] in exchange attributes, Statistical time consuming fail");
      } else {
        cost = System.currentTimeMillis() - startTime;
      }
      String ip = HttpRequestUtil.getClientIP(exchange.getRequest());


      if (exchange.getResponse().getStatusCode() == null || exchange.getResponse().getStatusCode().isError()) {
        log.error("REQUEST ERROR, requestId:[{}], ip:[{}], request:[{}][{}], query:[{}], response status:[{}], cost [{}] ms.",
                exchange.getRequest().getId(),
                ip,
                exchange.getRequest().getMethodValue(),
                exchange.getRequest().getPath().toString(),
                exchange.getRequest().getQueryParams().toString(),
                exchange.getResponse().getRawStatusCode(),
                cost
        );
      } else {
        log.info("REQUEST SUCCESS, requestId:[{}], ip:[{}], request:[{}][{}], query:[{}], response status:[{}], cost [{}] ms.",
                exchange.getRequest().getId(),
                ip,
                exchange.getRequest().getMethodValue(),
                exchange.getRequest().getPath().toString(),
                exchange.getRequest().getQueryParams().toString(),
                exchange.getResponse().getRawStatusCode(),
                cost
        );

      }


    }));
  }


}