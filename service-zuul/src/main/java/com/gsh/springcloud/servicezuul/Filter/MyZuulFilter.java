package com.gsh.springcloud.servicezuul.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
    * @Title: MyZuulFilter
    * @Package com.gsh.springcloud.servicezuul.Filter
    * @Description: Zuul网关拦截器
    * @author gsh
    * @date 2018/7/20 16:24
    */
@Configuration
public class MyZuulFilter extends ZuulFilter {

        private static  Logger log = LoggerFactory.getLogger(MyZuulFilter.class);
        @Override
        public String filterType() {
            return "pre";
        }
        @Override
        public int filterOrder() {
            return 0;
        }
        @Override
        public boolean shouldFilter() {
            return true;
        }
        @Override
        public Object run() {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            log.info(String.format("send %s request to %s", request.getMethod(), request.getRequestURL().toString()));
            //假设每个业务之前都得验证accessToken（存在并且有效）
            Object accessToken = request.getParameter("accessToken");
            //不为空则放行，后期可以自定义规则
            if (accessToken != null){
                return  null;
            }
            log.warn("token is empty ");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }