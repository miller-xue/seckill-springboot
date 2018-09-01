package com.miller.seckill.dao;

import com.miller.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by miller on 2018/8/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessKilledMapperTest {

    @Resource
    private SuccessKilledMapper mapper;

    @Test
    public void insert() {
        long id = 1000l;
        long phone = 13022989276l;
        int insert = mapper.insert(id, phone);
        System.out.println(insert);
    }

    @Test
    public void selectByIdWithSeckill() {
        long id = 1000l;
        SuccessKilled successKilled = mapper.selectByIdWithSeckill(id, 13022989276l);
        System.out.println(successKilled.toString());
    }
}