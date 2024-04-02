package org.example.service;

import org.example.entity.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cld
 * @since 2024-04-01
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    public boolean delOrderAndUpdateStock(Long orderId);

}
