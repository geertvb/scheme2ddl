package com.googlecode.scheme2ddl;

import com.googlecode.scheme2ddl.aspect.StatAspect;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = Application.class)
@ImportResource("scheme2ddl.config.xml")
public class Application {

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public StatAspect statAspect() {
        return new StatAspect();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
