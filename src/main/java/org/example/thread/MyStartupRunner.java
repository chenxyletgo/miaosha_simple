package org.example.thread;

import org.example.cache.GlobalCache;
import org.example.entity.SeckillOrder;
import org.example.entity.vo.OderDelayed;
import org.example.service.ISeckillOrderService;
import org.example.util.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by cld on 2024/4/1 19:02
 */
@Component
@Order(value = 1)
public class MyStartupRunner implements CommandLineRunner {

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Override
    public void run(String... args) throws Exception {

        // 判断订单是否超时，超时删除并该库存
        ThreadPoolUtil.getPool().execute(new Runnable() {
            @Override
            public void run() {
                consomerOrderDel();
            }
        });
        // 模拟支付过程
        ThreadPoolUtil.getPool().execute(new Runnable() {
            @Override
            public void run() {
                consomerPayDel();
            }
        });
    }

    public void consomerOrderDel() {
        while (true) {
            OderDelayed oderDelayed =
                    null;
            try {
                oderDelayed = GlobalCache.oderDelayedDelayQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费延时队列 data:" + oderDelayed.toString());
            SeckillOrder byId = seckillOrderService.getById(oderDelayed.getOrderId());
            if (byId != null && byId.getIsPay() == 0) {
                seckillOrderService.delOrderAndUpdateStock(oderDelayed.getOrderId());
            }
        }
    }

    public void consomerPayDel() {
        while (true) {
            OderDelayed take = null;
            try {
                take = GlobalCache.payMockDelayedDelayQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long randomTime = take.getInTime();
            long orderId = take.getOrderId();
            Random random = new Random();
            if (randomTime > 1000 * 60) {
                System.out.println("订单：" + orderId + " 支付超时，请重新抢购，用时：" + randomTime);
                // 需要在延迟队列中进行 删除订单 修改库存
                continue;
            }
            boolean isSuccess = random.nextBoolean();
            if (!isSuccess) {
                System.out.println("订单：" + orderId + " 余额不足，请先充值，用时：" + randomTime);
                // 删除订单 修改库存
                seckillOrderService.delOrderAndUpdateStock(orderId);
                continue;
            }
            // 修改订单
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setId(orderId);
            seckillOrder.setIsPay(1);
            seckillOrderService.updateById(seckillOrder);
            System.out.println("订单：" + orderId + " 抢购成功，用时：" + randomTime);
        }
    }

}
