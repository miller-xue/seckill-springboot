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
@Controller
@RequestMapping(value = "/seckill")
public class SeckillController {

    @Resource
    private SeckillService seckillService;

    @ResponseBody
    @RequestMapping(value = "/time/now",method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    public Result now() {
        return Result.success(new Date());
    }


    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    public Result<List<Seckill>> list() {
        return Result.success(seckillService.getSeckillList());
    }


    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<Seckill> detail(@PathVariable("seckillId") Long seckillId) {
        if (seckillId == null) {
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        return Result.success(seckill);
    }

    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST
            ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result exposer(@PathVariable("seckillId") Long seckillId) {
        if (seckillId == null) {
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        return Result.success(exposer);
    }

    @RequestMapping(value = "/{seckillId}/md5/execute", method = RequestMethod.POST
            ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result execute(@PathVariable("seckillId") Long seckillId,
                          @CookieValue(required = false) Long userPhone,
                          @PathVariable("md5") String md5) {
        // 是否登陆
        if (userPhone == null) {
            return Result.error(SysResult.NO_LOGIN);
        }
        // 参数是否填写
        if (seckillId == null || StringUtils.isBlank(md5)) {
            return Result.error(SeckillResult.DATA_REWRITE);
        }
        SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
        return Result.success(execution);
    }

}
