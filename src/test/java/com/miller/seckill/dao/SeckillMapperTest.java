package com.miller.seckill.dao;

import com.miller.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by miller on 2018/8/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillMapperTest {

    @Resource
    private SeckillMapper seckillMapper;

    @Test
    public void reduceNumber() {
        long id = 1000;
        int i = seckillMapper.reduceNumber(id, new Date());
        System.out.println(i);
    }

    @Test
    public void selectById() {
        long id = 1000;
        Seckill seckill = seckillMapper.selectById(id);
        System.out.println(seckill.toString());
    }

    @Test
    public void selectAll() {
        List<Seckill> seckills = seckillMapper.selectAll(0, 10);
        System.out.println(seckills.size());
    }
}