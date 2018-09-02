package com.miller.seckill.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.miller.seckill.common.serializer.Date2LongSerializer;
import com.miller.seckill.enums.BaseResult;
import com.miller.seckill.enums.SysResult;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by miller on 2018/8/8
 * @author Miller
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Result<T> {

    private int code;

    private String msg;

    private T data;


    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(T data) {
        this.code = SysResult.SUCCESS.getCode();
        this.msg = SysResult.SUCCESS.getMsg();
        this.data = data;
    }

    private Result(BaseResult baseResult) {
        if(baseResult == null) {
            return;
        }
        this.code = baseResult.getCode();
        this.msg = baseResult.getMsg();
    }



    /**
     * 成功时候的调用
     * */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }
    public static <T> Result<T> success(){
        return new Result(null);
    }

    /**
     * 失败时候的调用
     * */
    public static <T> Result<T> error(BaseResult baseResult){
        return new Result<T>(baseResult);
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg);
    }

}
