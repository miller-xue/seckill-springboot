package com.miller.seckill.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.miller.seckill.common.serializer.Date2LongSerializer;
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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date startTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date endTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}
