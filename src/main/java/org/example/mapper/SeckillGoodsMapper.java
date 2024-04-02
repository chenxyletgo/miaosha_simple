package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.entity.SeckillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cld
 * @since 2024-04-01
 */
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

    /**
     * mybatis plus执行原生sql方式
     * @param sqlStr
     * @return
     */
    @Update("${sqlStr}")
    int updateBySeckill(@Param(value = "sqlStr") String sqlStr);
}
