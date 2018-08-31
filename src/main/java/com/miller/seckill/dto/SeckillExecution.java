package com.miller.seckill.dto;

import com.miller.seckill.entity.SuccessKilled;
import lombok.*;

/**
 * 封装秒杀执行后的结果
 * Created by miller on 2018/8/31
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
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


    public SeckillExecution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }
}
