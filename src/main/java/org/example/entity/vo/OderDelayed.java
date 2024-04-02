package org.example.entity.vo;

import lombok.Data;
import org.example.cache.GlobalCache;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by cld on 2024/4/1 9:51
 */
@Data
public class OderDelayed implements Delayed {

    /**
     * 测试延迟队列
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        OderDelayed oderDelayed = new OderDelayed(1L, 1L, 1L, System.currentTimeMillis() + 5 * 1000);
        OderDelayed oderDelayed2 = new OderDelayed(2L, 1L, 1L, System.currentTimeMillis() + 3 * 1000);
        GlobalCache.oderDelayedDelayQueue.offer(oderDelayed);
        GlobalCache.oderDelayedDelayQueue.offer(oderDelayed2);
        while (true)
        {
            long timeMillis = System.currentTimeMillis();
            OderDelayed take = GlobalCache.oderDelayedDelayQueue.take();
            System.out.println(take.getOrderId());
            System.out.println(System.currentTimeMillis() - timeMillis);
        }
    }

    private Long orderId;
    private Long userId;
    private Long goodsId;
    private Long inTime;
    private long time;

    public OderDelayed(Long orderId, Long userId, Long goodsId, Long inTime, long time) {
        this.orderId = orderId;
        this.userId = userId;
        this.goodsId = goodsId;
        this.inTime = inTime;
        this.time = time;
    }

    public OderDelayed(Long orderId, Long userId, Long goodsId, long time) {
        this.orderId = orderId;
        this.userId = userId;
        this.goodsId = goodsId;
        this.time = time;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        OderDelayed oderDelayed = (OderDelayed) o;
        long to = this.time - oderDelayed.getTime();
        return to <= 0 ? -1 : 1;
    }
}
