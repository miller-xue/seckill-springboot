package com.miller.seckill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/8/12
 */

@AllArgsConstructor
@Getter
public enum  SeckillResult implements BaseResult {
    SUCCESS(1, "秒杀成功"),

    END(0,"秒杀结束"),

    NOT_START(-4,"秒杀未开始"),

    REPEAT_KILL(-1,"重复秒杀"),

    INNER_ERROR(-2,"系统异常"),

    DATA_REWRITE(-3,"数据篡改"),

    WAIT(-5,"排队中")
    ;

    private int code;
    private String msg;

}
