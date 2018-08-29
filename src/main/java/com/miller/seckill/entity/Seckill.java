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
public class Seckill {

    private long seckillId;

    private String name;

    private int number;


    private Date startTime;

    private Date endTime;

    private Date createTime;
}
