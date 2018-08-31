package com.miller.seckill.exception;

import com.miller.seckill.enums.BaseResult;

/**秒杀关闭异常
 * Created by miller on 2018/8/31
 * @author Miller
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(BaseResult baseResult) {
        super(baseResult);
    }
}
