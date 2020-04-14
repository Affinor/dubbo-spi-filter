package com.jin.demo.dubbo;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author wangjin
 */
public class HelloServiceTest {
    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
            app.start();
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Configuration
    @EnableDubbo(scanBasePackages = "com.jin.demo.dubbo")
    @PropertySource("classpath:/dubbo-provider.properties")
    static class ProviderConfiguration {
        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://127.0.0.1:2181?timeout=10000");
            return registryConfig;
        }
    }
}
