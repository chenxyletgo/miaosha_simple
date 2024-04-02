package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.cache.GlobalCache;
import org.example.entity.SeckillGoods;
import org.example.entity.SeckillOrder;
import org.example.entity.vo.OderDelayed;
import org.example.mapper.SeckillGoodsMapper;
import org.example.service.IAlipayService;
import org.example.service.ISeckillGoodsService;
import org.example.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/seckill"))
public class SeckillController {

    String pattern = "yyyy-MM-dd HH:mm:ss";

    @Autowired(required = false)
    private SeckillGoodsMapper seckillGoodsMapper;


    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IAlipayService alipayService;

    /**
     * qps 每秒查询次数
     * tps 每秒完成多少个过程：客户机发送时间到服务器返回的时间
     *
     * 先看是否有库存 再看该用户是否已经抢购 有库存且该用户还没有抢购就生成订单插入订单表 再插入抢购订单表
     * @return
     */
    @GetMapping("/doSeckill")
    public String doSeckill(Long userId, Long goodsId) {
        if (userId == null) {
            return "未登录";
        }
        LambdaQueryWrapper<SeckillGoods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SeckillGoods::getId, goodsId);
        SeckillGoods seckillGoods = seckillGoodsService.getOne(lambdaQueryWrapper);
        if (seckillGoods == null) {
            return "无该商品";
        }
        if (seckillGoods.getStockCount() > 0) {
            LambdaQueryWrapper<SeckillOrder> seckillOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            seckillOrderLambdaQueryWrapper.eq(SeckillOrder::getUserId, userId)
                            .eq(SeckillOrder::getGoodsId, goodsId);
            long count = seckillOrderService.count(seckillOrderLambdaQueryWrapper);
            if (count > 0) {
                return "一个人只能抢购一件";
            }
            // 必须用原生sql且一条sql，保证原子性
            int i = seckillGoodsMapper.updateBySeckill("update t_seckill_goods set stock_count = stock_count - 1 where id = ? and stock_count > 0".replace("?", String.valueOf(goodsId)));
            if (i != 1) {
                return "抢购失败";
            }
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setGoodsId(goodsId);
            seckillOrder.setUserId(userId);
            seckillOrder.setIsPay(0);
            seckillOrderService.save(seckillOrder);
            // mybatis plus中实体类设置ID注解后 save等方法会返回实体的id值 所有下面可以直接用getId()
            OderDelayed oderDelayed = new OderDelayed(seckillOrder.getId(), userId, goodsId, System.currentTimeMillis() + 60 * 1000);
            GlobalCache.oderDelayedDelayQueue.offer(oderDelayed);
            //模拟跳转到支付页面
            alipayService.alipayMock(seckillOrder.getId());
            return "抢购成功，还需尽快支付，1分钟内不支付将会自动取消订单";
        }
        return "没有库存了";
    }

}
