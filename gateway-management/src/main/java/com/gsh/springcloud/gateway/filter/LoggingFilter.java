package com.gsh.springcloud.gateway.filter;

import com.gsh.springcloud.gateway.domain.SysLog;
import com.gsh.springcloud.gateway.util.WebFluxRequestUtil;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

/**
 * 全局日志filter
 *
 * @author gsh
 */
@Slf4j
@Configuration
public class LoggingFilter implements GlobalFilter, Ordered {

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    preLog(exchange);
    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
      Long startTime = exchange.getAttribute("startTime");
      if (startTime == null) {
        log.error(" =======requestTimeStart fail ====非法请求==========");
        return;
      }
      SysLog sysLog = new SysLog();
      sysLog.setCreateTime(new Date());
      sysLog.setUserName(exchange.getAttribute("username"));
//      sysLog.setReqBody(exchange.getAttribute("reqBody"));
      sysLog.setRemoteAddr(exchange.getAttribute("ip"));
      sysLog.setReqMethod(exchange.getAttribute("scope"));
      sysLog.setReqUri(exchange.getAttribute("url"));

      if (null != startTime) {
        sysLog.setExeTime(System.currentTimeMillis() - startTime);
      }
      if (null != exchange.getAttribute("contentType")) {
        sysLog.setContentType(exchange.getAttribute("contentType").toString());
      }
      sysLog.setReqParams(exchange.getAttribute("reqParams"));
      log.info(sysLog.toString());
    }));
  }


  private void preLog(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    Long startTime = System.currentTimeMillis();
    exchange.getAttributes().put("startTime", startTime);
    String url = request.getPath().value();
    exchange.getAttributes().put("url", url);
    String scope = request.getMethodValue();
    exchange.getAttributes().put("scope", scope);
    Optional.ofNullable(request.getHeaders().getContentType()).ifPresent(x -> {
      exchange.getAttributes().put("contentType", x);
    });
    String ip = WebFluxRequestUtil.getIp(request);
    exchange.getAttributes().put("ip", ip);
  }


  /**
   * string转成 buffer
   *
   * @param value
   * @return
   */
  private DataBuffer stringBuffer(String value) {
    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
    DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
    buffer.write(bytes);
    return buffer;
  }


}