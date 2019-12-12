package com.gsh.springcloud.order.utils.aop;

import com.gsh.springcloud.order.config.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("!@annotation(com.gsh.springcloud.order.utils.annotation.Master) " +
            "&& (execution(* com.gsh.springcloud.order.service..*.select*(..)) " +
            "|| execution(* com.gsh.springcloud.order.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.gsh.springcloud.order.utils.annotation.Master) " +
            "|| execution(* com.gsh.springcloud.order.service..*.insert*(..)) " +
            "|| execution(* com.gsh.springcloud.order.service..*.add*(..)) " +
            "|| execution(* com.gsh.springcloud.order.service..*.update*(..)) " +
            "|| execution(* com.gsh.springcloud.order.service..*.edit*(..)) " +
            "|| execution(* com.gsh.springcloud.order.service..*.delete*(..)) " +
            "|| execution(* com.gsh.springcloud.order.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }


    /**
     * 另一种写法：if...else...  判断哪些需要读从数据库，其余的走主数据库
     */
//    @Before("execution(* com.gsh.springcloud.order.service.impl.*.*(..))")
//    public void before(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
//            DBContextHolder.slave();
//        }else {
//            DBContextHolder.master();
//        }
//    }
}