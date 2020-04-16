package com.jin.demo.dubbo;

import com.jin.demo.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wangjin
 */
@Service(filter = {"transportip"})
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello1(String name) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "hello1:"+name;
    }

    @Override
    public String hello2(String name) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "hello2:"+name;
    }

    @Override
    public String hello3(String name) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "hello3:"+name;
    }

    @Override
    public String browserIp(String browserIp) {
        return "browserIp:"+browserIp;
    }
}
