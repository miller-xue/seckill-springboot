package com.miller.seckill.service.impl;

import com.miller.seckill.dto.Exposer;
import com.miller.seckill.dto.SeckillExecution;
import com.miller.seckill.entity.Seckill;
import com.miller.seckill.exception.RepeatKillException;
import com.miller.seckill.exception.SeckillCloseException;
import com.miller.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by miller on 2018/9/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SeckillServiceImplTest {

    @Resource
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        log.info( "结果集长度" + seckillList.size());
    }

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(1000l);
        log.info(seckill.toString());
    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(100012);
        System.out.println(exposer.toString()); // e7c839e9e902cfa303ea32d9bd8a3b06
    }

    @Test
    public void executeSeckill() {
        long id = 100012l;
        long phone = 18149011466l;
        String md5 = "e7c839e9e902cfa303ea32d9bd8a3b06";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
        log.info("seckillExecution = {}",seckillExecution);
    }

    @Test
    public void seckillLogic() {
        long id = 100012l;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            long phone = 18149011466l;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                log.info("result = {}", seckillExecution);
            } catch (RepeatKillException e1) {
                log.error(e1.getMessage());
            }catch (SeckillCloseException e2) {
                log.error(e2.getMessage());
            }
        }else {
            log.warn("秒杀未开启  exposer={}", exposer);
        }

    }
}