package com.miller.seckill.dto;

import com.miller.seckill.entity.SuccessKilled;
import com.miller.seckill.enums.SeckillResult;
import lombok.*;

/**
 * 封装秒杀执行后的结果
 * Created by miller on 2018/8/31
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SeckillExecution {

    private long seckillId;

    /**
     * 执行秒杀结果状态
     */
    private int state;

    /**
     * 状态标识
     */
    private String stateInfo;

    private SuccessKilled successKilled;


    public SeckillExecution(long seckillId, SeckillResult seckillResult) {
        this.seckillId = seckillId;
        this.state = seckillResult.getCode();
        this.stateInfo = seckillResult.getMsg();
    }

    public SeckillExecution(long seckillId, SeckillResult seckillResult, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillResult.getCode();
        this.stateInfo = seckillResult.getMsg();
        this.successKilled = successKilled;
    }
}
