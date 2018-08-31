package com.miller.seckill.service;

import com.miller.seckill.dto.Exposer;
import com.miller.seckill.dto.SeckillExecution;
import com.miller.seckill.entity.Seckill;
import com.miller.seckill.exception.RepeatKillException;
import com.miller.seckill.exception.SeckillCloseException;
import com.miller.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口: 站在使用者 角度设计接口
 * 三个方面：方法定义粒度,参数,返回类型(return 类型/异常)
 * Created by miller on 2018/8/31
 * @author Miller
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时 输出秒杀接口的地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException , RepeatKillException, SeckillCloseException;
}
