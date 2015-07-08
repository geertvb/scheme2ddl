package com.googlecode.scheme2ddl;

import com.googlecode.scheme2ddl.aspect.StatAspect;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.*;
import org.springframework.transaction.PlatformTransactionManager;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = Application.class, scopedProxy = ScopedProxyMode.INTERFACES)
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

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .showBanner(false)
                .addCommandLineProperties(true)
                .sources(Application.class)
                .run(args);
    }

}
