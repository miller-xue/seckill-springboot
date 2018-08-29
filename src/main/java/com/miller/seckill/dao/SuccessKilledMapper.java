package com.miller.seckill.dao;

import com.miller.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by miller on 2018/8/29
 */
@Mapper
public interface SuccessKilledMapper {

    /**
     * 插入购买明细,可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insert(long seckillId, long userPhone);

    /**
     * 查询SuccessKilled携带秒杀产品实体
     * @param seckillId
     * @return
     */
    SuccessKilled selectByIdWithSeckill(long seckillId);

}
