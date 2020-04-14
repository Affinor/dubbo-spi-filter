package com.jin.demo.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangjin
 */
public class ConsumerTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        HelloServiceConsumer service = context.getBean(HelloServiceConsumer.class);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        while (true) {
            System.in.read();
            try {
                for (int i = 0; i < 10; i++) {
                    /*executorService.execute(()->{
                        String hello1 = service.sayHello1("world1");
                        System.out.println("result :" + hello1);
                        String hello2 = service.sayHello2("world2");
                        System.out.println("result :" + hello2);
                        String hello3 = service.sayHello3("world3");
                        System.out.println("result :" + hello3);
                    });*/
                    String hello1 = service.sayHello1("world1");
                    System.out.println("result :" + hello1);
                    String hello2 = service.sayHello2("world2");
                    System.out.println("result :" + hello2);
                    String hello3 = service.sayHello3("world3");
                    System.out.println("result :" + hello3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan("com.jin.demo.dubbo.consumer")
    @EnableDubbo
    static class ConsumerConfiguration {

    }
}
