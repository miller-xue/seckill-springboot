package com.miller.seckill.controller;

import com.miller.seckill.common.Result;
import com.miller.seckill.dto.Exposer;
import com.miller.seckill.dto.SeckillExecution;
import com.miller.seckill.entity.Seckill;
import com.miller.seckill.enums.SeckillResult;
import com.miller.seckill.enums.SysResult;
import com.miller.seckill.service.SeckillService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by miller on 2018/9/1
 * @author Miller
 */
@RestController
@RequestMapping(value = "/seckill")
public class SeckillController {

    @Resource
    private SeckillService seckillService;

    
    /**@RequestMapping(value = "/time/now",method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})*/
    @GetMapping(value = "/time/now",produces = {"application/json;charset=UTF-8"})
    public Result now() {
        return Result.success(new Date());
    }


    
    @GetMapping(value = "/list" ,produces = {"application/json;charset=UTF-8"})
    public Result<List<Seckill>> list() {
        return Result.success(seckillService.getSeckillList());
    }


    @GetMapping(value = "/{seckillId}/detail" ,produces = {"application/json;charset=UTF-8"})
    public Result<Seckill> detail(@PathVariable("seckillId") Long seckillId) {
        if (seckillId == null) {
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return Result.error(SysResult.SERVER_ERROR);
        }
        return Result.success(seckill);
    }

    @PostMapping(value = "/{seckillId}/exposer",produces = {"application/json;charset=UTF-8"})
    public Result exposer(@PathVariable("seckillId") Long seckillId) {
        if (seckillId == null) {
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        return Result.success(exposer);
    }

    @PostMapping(value = "/{seckillId}/{md5}/execute" ，produces = {"application/json;charset=UTF-8"})
    public Result execute(@PathVariable("seckillId") Long seckillId,
                          @CookieValue(name = "killPhone",required = false) Long userPhone,
                          @PathVariable("md5") String md5) {
        // 是否登陆
        if (userPhone == null) {
            return Result.error(SysResult.NO_LOGIN);
        }
        // 参数是否填写
        if (seckillId == null || StringUtils.isBlank(md5)) {
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        SeckillExecution execution = seckillService.executeSeckillByProcedure(seckillId, userPhone, md5);
        return Result.success(execution);
    }

}
