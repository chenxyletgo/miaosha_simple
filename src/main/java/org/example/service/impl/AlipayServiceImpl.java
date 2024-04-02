package org.example.service.impl;

import org.example.cache.GlobalCache;
import org.example.entity.vo.OderDelayed;
import org.example.service.IAlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by cld on 2024/4/1 9:16
 */
@Service
public class AlipayServiceImpl implements IAlipayService {

    /**
     * 该方法会创建任务放入线程池里（旧）
     * 该方法会创建任务放入延迟队列（新）
     * @param orderId
     * @return
     */
    @Override
    public void alipayMock(Long orderId) {
        Random random = new Random();
        // 模拟支付耗时
        long randomTime = random.nextInt(1000 * 30) + 1000 * 40L;
        OderDelayed oderDelayed = new OderDelayed(orderId, 0L , 0L,  randomTime, System.currentTimeMillis() + randomTime);
        GlobalCache.payMockDelayedDelayQueue.offer(oderDelayed);
        //线程不能保证sleep时间短的先执行，所有改用延迟队列了
//        ThreadPoolUtil.getPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(randomTime);
//                    if (randomTime > 1000 * 60) {
//                        System.out.println("订单：" + orderId + " 支付超时，请重新抢购，用时：" + randomTime);
//                        // 需要在延迟队列中进行 删除订单 修改库存
//                        return;
//                    }
//                    boolean isSuccess = random.nextBoolean();
//                    if (!isSuccess) {
//                        System.out.println("订单：" + orderId + " 余额不足，请先充值，用时：" + randomTime);
//                        // 删除订单 修改库存
//                        seckillOrderService.delOrderAndUpdateStock(orderId);
//                        return;
//                    }
//                    // 修改订单
//                    SeckillOrder seckillOrder = new SeckillOrder();
//                    seckillOrder.setId(orderId);
//                    seckillOrder.setIsPay(1);
//                    seckillOrderService.updateById(seckillOrder);
//                    System.out.println("订单：" + orderId + " 抢购成功，用时：" + randomTime);
//                    return;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
