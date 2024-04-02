package org.example.cache;

import org.example.entity.vo.OderDelayed;

import java.util.concurrent.DelayQueue;

/**
 * Created by cld on 2024/4/1 9:55
 */
public class GlobalCache {

    // 两个队列的唯一区别是同一个订单放入的时的时间不通
    // 其实可以都放入一个队列，但是要改MyStartupRuner并且
    // 还要改OderDelayed判断啥的，所有我干脆放两个队列了。

    // 订单存放 生成的订单都会放入里面
    public static DelayQueue<OderDelayed> oderDelayedDelayQueue = new DelayQueue<>();

    // 模拟支付 生成的订单都会放入里面
    public static DelayQueue<OderDelayed> payMockDelayedDelayQueue = new DelayQueue<>();
}
