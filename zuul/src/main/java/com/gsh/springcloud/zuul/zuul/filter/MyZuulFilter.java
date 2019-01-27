package com.gsh.springcloud.zuul.zuul.filter;
  
import javax.servlet.http.HttpServletRequest;  
  
import com.netflix.zuul.ZuulFilter;  
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
    * @Title: MyZuulFilter
    * @Package com.gsh.springcloud.zuul.zuul.filter
    * @Description: zuul过滤器
    * zuul还提供了一类特殊的过滤器，分别为：StaticResponseFilter和SurgicalDebugFilter
    * StaticResponseFilter：StaticResponseFilter允许从Zuul本身生成响应，而不是将请求转发到源。
    * SurgicalDebugFilter：SurgicalDebugFilter允许将特定请求路由到分隔的调试集群或主机。
    *
    * @author gsh
    * @date 2019/1/27 10:43
    */
@Component
public class MyZuulFilter extends ZuulFilter {
    /**
     *run：过滤器的具体逻辑。
     * 注意:这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由.
     * 然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码
     * @return
     */
    @Override  
    public Object run() {

        //对该请求进行路由
        boolean zuulResponse = true;
        //设值，让下一个Filter看到上一个Filter的状态
        boolean isSuccess = true;
        //返回filter状态码
        int responseStatusCode = 200 ;
        //提示信息
        String msg = "";
        //request请求对象
        RequestContext ctx = RequestContext.getCurrentContext();  
        HttpServletRequest request = ctx.getRequest();  
        System.out.println(String.format("%s MyZuulFilter request to %s", request.getMethod(), request.getRequestURL().toString()));
        //获取请求头
        String accessToken = request.getHeader("accessToken");
        // accessToken解析
        if (null == accessToken || !"accessToken".equals(accessToken)){
            //
            zuulResponse = false;
            responseStatusCode = 401;
            isSuccess = false;
            msg = "accessToken 错误";
            ctx.setResponseBody("{\"result\":\""+msg+"\"}");
        }
        // 如果请求的参数不为空，gsh，则通过
        ctx.setSendZuulResponse(zuulResponse);
        ctx.setResponseStatusCode(responseStatusCode);
        ctx.set("isSuccess", isSuccess);
        return null;
    }

    /**
     *  是否执行该过滤器，此处为true，说明需要过滤
     *  返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。
     *  在上例中，我们直接返回true，所以该过滤器总是生效
     * @return
     */
    @Override  
    public boolean shouldFilter() {
        //RequestContext ctx = RequestContext.getCurrentContext();
        //return (boolean) ctx.get("isSuccess");// 如果前一个过滤器的结果为true，
        // 则说明上一个过滤器成功了，需要进入当前的过滤，如果前一个过滤器的结果为false，
        // 则说明上一个过滤器没有成功，则无需进行下面的过滤动作了，直接跳过后面的所有过滤器并返回结果
        return true;
    }

    /**
     * 通过int值来定义过滤器的执行顺序
     * 优先级为0，数字越大，优先级越低
     * @return
     */
    @Override  
    public int filterOrder() {  
        return 0;
    }

    /**
     * 前置过滤器
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     *
     * pre：  可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post： 在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     * Zuul的主要请求生命周期包括“pre”，“route”和“post”等阶段。对于每个请求，都会运行具有这些类型的所有过滤器。
     * @return
     */
    @Override  
    public String filterType() {  
        return "pre";
    }  
}