package com.jin.demo.dubbo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author wangjin
 */
@Activate(group = {CommonConstants.CONSUMER,CommonConstants.PROVIDER})
public class TransportIPFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        //如果是消费者，就把浏览器ip放入RpcContext
        if (context.isConsumerSide()){
            context.getAttachments().put("browserIp",String.valueOf(invocation.getArguments()[0]));
        }
        //如果是生产者，就把浏览器ip从RpcContext中取出，并打印
        if (context.isProviderSide()){
            System.out.println("browserIp==>>"+context.getAttachment("browserIp"));
        }
        return invoker.invoke(invocation);
    }
}
