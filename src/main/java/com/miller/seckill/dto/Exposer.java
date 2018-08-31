package com.miller.seckill.dto;

import lombok.*;

/**
 * 暴露秒杀地址DTO
 * Created by miller on 2018/8/31
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Exposer {

    private boolean exposed;

    /**
     * 一种加密措施
     */
    private String md5;

    private long seckillId;

    /**
     * 系统当前时间(毫秒)
     */
    private long now;

    /**
     * 开启时间
     */
    private long start;

    /**
     * 结束时间
     */
    private long end;

    public Exposer(boolean exposed, long seckillId, long now, long start, long end) {

        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
