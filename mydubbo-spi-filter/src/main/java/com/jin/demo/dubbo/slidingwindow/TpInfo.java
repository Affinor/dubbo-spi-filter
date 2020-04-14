package com.jin.demo.dubbo.slidingwindow;

/**
 * @author wangjin
 */
public class TpInfo {
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 当前时间
     */
    private Long currentTime;
    /**
     * 耗时
     */
    private Long useTime;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }
}
