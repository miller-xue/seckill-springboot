package com.miller.seckill.exception;

import com.miller.seckill.common.Result;
import com.miller.seckill.enums.SysResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by miller on 2018/8/10
 *
 * @author Miller
 * 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class SeckillExceptionHandler {

    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public Result bindExceptionHandler(BindException e) {
        e.printStackTrace();
        List<ObjectError> allErrors = e.getAllErrors();
        ObjectError objectError = allErrors.get(0);
        return Result.error(SysResult.BIND_ERROR.fillArgs(objectError.getDefaultMessage()));
    }

    /**
     * 全局手动抛出参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = SeckillException.class)
    public Result globleExceptionHandler(SeckillException e)
    {
        e.printStackTrace();
        return Result.error(e.getBaseResult());
    }

    /**
     * 处理全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.error(SysResult.SERVER_ERROR);
    }
}
