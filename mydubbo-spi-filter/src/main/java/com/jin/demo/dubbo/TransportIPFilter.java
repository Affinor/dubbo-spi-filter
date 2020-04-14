package com.jin.demo.dubbo;

import org.apache.dubbo.common.URL;
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
        URL url = invoker.getUrl();
        if (context.isConsumerSide()){
            context.getAttachments().put("realIp",url.getIp()+":"+url.getPort());
        }
        if (context.isProviderSide()){
            System.out.println("realIp==>>"+context.getAttachment("realIp"));
        }
        return invoker.invoke(invocation);
    }
}
