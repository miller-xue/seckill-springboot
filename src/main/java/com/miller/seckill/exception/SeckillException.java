package com.miller.seckill.exception;

import com.miller.seckill.enums.BaseResult;
import lombok.Getter;

/**
 * 所有秒杀异常
 * Created by miller on 2018/8/31
 * @author Miller
 */
@Getter
public class SeckillException extends RuntimeException {
    protected BaseResult baseResult;

    protected int code;

    public SeckillException(BaseResult baseResult) {
        super(baseResult.getMsg());
        this.baseResult = baseResult;
        this.code = baseResult.getCode();
    }
}
