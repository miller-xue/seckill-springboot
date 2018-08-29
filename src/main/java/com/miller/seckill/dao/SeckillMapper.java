package com.miller.seckill.dao;

import com.miller.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created by miller on 2018/8/29
 */
@Mapper
public interface SeckillMapper {

    /**
     * 减库存
     * @param seckillId 秒杀库存id
     * @param killTime 执行减库存时间
     * @return
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * 根据id查询秒杀对象
     *
     * @param seckillId
     * @return
     */
    Seckill selectById(long seckillId);


    /**
     * 根据偏移量查询秒杀商品列表
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> selectAll(int offet, int limit);
}
