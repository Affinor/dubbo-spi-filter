package com.jin.demo.dubbo;

import com.jin.demo.dubbo.slidingwindow.SlidingWindow;
import com.jin.demo.dubbo.slidingwindow.TpInfo;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjin
 */
@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter, Runnable {

    public static Map<String, SlidingWindow> WindowMap = new ConcurrentHashMap<>();

    private static final double TP90 = 0.90D;

    private static final double TP99 = 0.99D;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long start = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long end = System.currentTimeMillis();
        RpcContext context = RpcContext.getContext();
        String methodName = context.getMethodName();
        TpInfo tpInfo = new TpInfo();
        tpInfo.setCurrentTime(System.currentTimeMillis());
        tpInfo.setMethodName(methodName);
        tpInfo.setUseTime(end-start);
        if (WindowMap.containsKey(methodName)){
            WindowMap.get(methodName).addCount(tpInfo);
        }else {
            SlidingWindow slidingWindow = new SlidingWindow(5000,12);
            slidingWindow.addCount(tpInfo);
            WindowMap.put(methodName,slidingWindow);
        }
        return result;
    }

    public TPMonitorFilter() {
        // 每隔5s打印一次最近1分钟内每个方法的TP90、TP99的耗时情况
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(this, 1,5, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        WindowMap.forEach((k,v)->{
            try {
                System.out.println(k+":TP90====>>"+v.calculateTpCount(TP90));
                System.out.println(k+":TP99====>>"+v.calculateTpCount(TP99));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
