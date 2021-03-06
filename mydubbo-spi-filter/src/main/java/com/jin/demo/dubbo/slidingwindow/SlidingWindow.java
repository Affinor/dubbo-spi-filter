package com.jin.demo.dubbo.slidingwindow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @author wangjin
 */
public class SlidingWindow {
    /**
     * 循环队列，就是装多个窗口用
     * 循环数组+链表实现时间滑动窗口
     */
    private AtomicReferenceArray<CopyOnWriteArrayList<TpInfo>> timeSlices;
    /**
     * 队列的总长度
     */
    private int timeSliceSize;
    /**
     * 每个时间片的时长，以毫秒为单位
     */
    private int timeMillisPerSlice;
    /**
     * 共有多少个时间片（即窗口长度）
     */
    private int windowSize;
    /**
     * 该滑窗的起始创建时间，也就是第一个数据
     */
    private long beginTimestamp;
    /**
     * 最后一个数据的时间戳
     */
    private long lastAddTimestamp;

    public SlidingWindow(int timeMillisPerSlice, int windowSize) {
        this.timeMillisPerSlice = timeMillisPerSlice;
        //窗口大小要加1，因为有个一时间片总是空的
        this.windowSize = windowSize+1;
        this.timeSliceSize = windowSize+1;
        reset();
    }

    /**
     * 初始化
     */
    private void reset() {
        beginTimestamp = System.currentTimeMillis();
        //窗口个数
        AtomicReferenceArray<CopyOnWriteArrayList<TpInfo>> localTimeSlices = new AtomicReferenceArray<>(timeSliceSize);
        for (int i = 0; i < timeSliceSize; i++) {
            localTimeSlices.set(i,new CopyOnWriteArrayList<TpInfo>());
        }
        timeSlices = localTimeSlices;
    }

    /**
     * 计算当前所在的时间片的位置
     */
    private int locationIndex() {
        long now = System.currentTimeMillis();
        //如果当前的key已经超出一整个时间片了，那么就直接初始化就行了，不用去计算了
        if (now - lastAddTimestamp > timeMillisPerSlice * windowSize) {
            reset();
        }
        return (int) (((now - beginTimestamp) / timeMillisPerSlice) % timeSliceSize);
    }

    /**
     * 增加count个数量
     */
    public void addCount(TpInfo tpInfo) {
        //当前自己所在的位置，是哪个小时间窗
        int index = locationIndex();
        //清空下个index
        clearFromIndex(index);
        lastAddTimestamp = System.currentTimeMillis();
        // 在当前时间片里继续+1
        timeSlices.get(index).add(tpInfo);
    }

    /**
     * 清空下个index时间片上的数据
     * @param index
     */
    private void clearFromIndex(int index) {
        int a = 0;
        if (index + 1 >= windowSize){
            a = windowSize - index -1;
        }else {
            a = index + 1;
        }
        timeSlices.set(a,new CopyOnWriteArrayList<TpInfo>());
    }

    /**
     * 计算TP90/TP99的耗时情况
     * @param tpCount
     * @return
     */
    public Long calculateTpCount(Double tpCount){
        List<Long> list = new ArrayList<>();
        //遍历所有时间片位置上的数据，找出耗时
        for (int i = 0; i < timeSlices.length(); i++) {
            timeSlices.get(i).stream()
                    .mapToLong(tpInfo -> tpInfo.getUseTime())
                    .forEach(value -> list.add(Long.valueOf(value)));
        }
        //如果没有数据或者只有一个，就直接返回
        if (list.size()==0){
            return 0L;
        }else if (list.size()==1){
            return list.get(0);
        }
        return list.stream()
                //升序排序
                .sorted(Comparator.comparingLong(Long::longValue))
                //跳过前(n-1)个
                .skip((long) (list.size() * tpCount)-1L)
                //进行截取第1个
                .limit((long) (list.size() * tpCount))
                //截取到的就是我们要找的那个值
                .findFirst()
                .get();
    }

}
