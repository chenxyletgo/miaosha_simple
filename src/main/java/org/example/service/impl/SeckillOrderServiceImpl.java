package org.example.service.impl;

import org.example.entity.SeckillOrder;
import org.example.mapper.SeckillGoodsMapper;
import org.example.mapper.SeckillOrderMapper;
import org.example.service.ISeckillGoodsService;
import org.example.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cld
 * @since 2024-04-01
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired(required = false)
    private SeckillGoodsMapper seckillGoodsMapper;

    /**
     * 当订单支付失败活支付超时时，删除订单和更新库存
     * 因为Service在spring容器中只有一个，所以这个方法是线程安全的
     * UPDATE t_seckill_goods SET stock_count = stock_count + 1 WHERE id = (SELECT goods_id FROM t_seckill_order WHERE id = ?)
     * @param orderId
     * @return
     */
    @Override
    public boolean delOrderAndUpdateStock(Long orderId) {
        // 获取订单 获取商品
        SeckillOrder order = this.getById(orderId);
        // 修改商品库存 用sql，保证原子性
        int i = seckillGoodsMapper.updateBySeckill("UPDATE t_seckill_goods SET stock_count = stock_count + 1 WHERE id = (SELECT goods_id FROM t_seckill_order WHERE id = ?)".replace("?", String.valueOf(orderId)));
        if (i == 1) {
            return this.removeById(orderId);
        }
        System.out.println("删除订单失败，修改库存失败 orderId: " + orderId);
        return false;
        // 删除订单
    }
}
