package com.gsh.springcloud.starter.mysql.plus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author gsh
 */
@Import({
        MyMetaObjectHandler.class
})
@Configuration
public class MybatisPlusConfiguration {
    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }
}