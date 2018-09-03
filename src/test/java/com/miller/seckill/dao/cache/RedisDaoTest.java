package com.miller.seckill.dao.cache;

import com.miller.seckill.dao.SeckillMapper;
import com.miller.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by miller on 2018/9/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDaoTest {

    private long id = 1001l;
    @Resource
    private RedisDao redisDao;

    @Resource
    private SeckillMapper seckillMapper;



    @Test
    public void Seckill() {
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null) {
            seckill = seckillMapper.selectById(id);
            if (seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
            }
        }
        System.out.println(seckill);
    }

}