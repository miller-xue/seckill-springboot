package com.miller.seckill.dao.cache;

import com.miller.seckill.common.ProtoBufUtil;
import com.miller.seckill.dao.SeckillMapper;
import com.miller.seckill.entity.Seckill;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * Created by miller on 2018/9/3
 */
@Repository
public class RedisDao {

    @Autowired
    private JedisPool jedisPool;

    @Resource
    private SeckillMapper seckillMapper;


    public Seckill getSeckill(long seckillId) {
        //redis操作逻辑
        @Cleanup Jedis jedis = jedisPool.getResource();
        String key = "seckill:" + seckillId;
        byte[] bytes = jedis.get(key.getBytes());
        if (bytes != null) {
            return ProtoBufUtil.deserializer(bytes, Seckill.class);
        }else {

        }

        return null;
    }

    public String putSeckill(Seckill seckill) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String key = "seckill:" + seckill.getSeckillId();
        byte[] serializer = ProtoBufUtil.serializer(seckill);
        int timeout = 60 * 60;
        return jedis.setex(key.getBytes(), timeout, serializer);
    }
}
