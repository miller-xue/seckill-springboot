package com.miller.seckill.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by miller on 2018/8/29
 */
@Getter
@Setter
@ToString
public class SuccessKilled {

    private long seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    private Seckill seckill;
}
