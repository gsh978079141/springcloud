package com.gsh.springcloud.user.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentineExceptionUtil {

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public static String helloFallback(long s, Throwable ex) {
        log.error("fallbackHandler：" + s);

        return "Oops fallbackHandler, error occurred at " + s;
    }

    //默认的 fallback 函数名称
    public static String defaultFallback() {
        log.info("Go to default fallback");
        return "default_fallback";
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public static String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        return "Oops,exceptionHandler, error occurred at " + s;
    }

}
