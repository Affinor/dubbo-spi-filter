package com.jin.demo.dubbo.consumer;

import com.jin.demo.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author wangjin
 */
@Component
public class HelloServiceConsumer {

    @Reference(filter={"ipmonitor","transportip"})
    HelloService helloService;

    public String sayHello1(String name){
        return helloService.hello1(name);
    }

    public String sayHello2(String name){
        return helloService.hello2(name);
    }

    public String sayHello3(String name){
        return helloService.hello3(name);
    }
}
