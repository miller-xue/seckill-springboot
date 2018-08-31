package com.miller.seckill.exception;

import com.miller.seckill.enums.BaseResult;

/**
 * 重复秒杀异常
 * Created by miller on 2018/8/31
 * @author Miller
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(BaseResult baseResult) {
        super(baseResult);
    }
}
