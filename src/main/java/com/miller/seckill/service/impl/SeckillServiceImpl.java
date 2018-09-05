package com.miller.seckill.service.impl;

import com.miller.seckill.dao.SeckillMapper;
import com.miller.seckill.dao.SuccessKilledMapper;
import com.miller.seckill.dao.cache.RedisDao;
import com.miller.seckill.dto.Exposer;
import com.miller.seckill.dto.SeckillExecution;
import com.miller.seckill.entity.Seckill;
import com.miller.seckill.entity.SuccessKilled;
import com.miller.seckill.enums.EnumUtil;
import com.miller.seckill.enums.SeckillResult;
import com.miller.seckill.enums.SysResult;
import com.miller.seckill.exception.RepeatKillException;
import com.miller.seckill.exception.SeckillCloseException;
import com.miller.seckill.exception.SeckillException;
import com.miller.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miller on 2018/8/31
 *
 * @author Miller
 */

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    @Resource
    private SeckillMapper seckillMapper;

    @Resource
    private SuccessKilledMapper successKilledMapper;

    @Resource
    private RedisDao redisDao;

    private final String slat = "213idkjawoidjoi";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillMapper.selectAll(0, 10);
    }

    @Override
    public Seckill getById(long seckillId) {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            seckill = seckillMapper.selectById(seckillId);
            if (seckill != null) {
                redisDao.putSeckill(seckill);
            }
        }
        return seckill;
    }


    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = getById(seckillId);
        // 查不到秒杀记录
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        long startTime = seckill.getStartTime().getTime();
        long endTime = seckill.getEndTime().getTime();
        long now = System.currentTimeMillis();
        if (now < startTime || now > endTime) {
            return new Exposer(false, seckillId, now, startTime, endTime);
        }

        // 转换特定字符串的过程，不可逆
        return new Exposer(true, getMd5(seckillId), seckillId);
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (StringUtils.isBlank(md5) || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException(SeckillResult.DATA_REWRITE);
        }
        try {
            // 执行秒杀逻辑 : 加库存 + 记录购买行为 TODO ifelse 逻辑更加明确
            int successKilledCount = successKilledMapper.insert(seckillId, userPhone);
            if (successKilledCount <= 0) {
                // 重复秒杀
                throw new RepeatKillException(SeckillResult.REPEAT_KILL);
            } else {
                int reduceCount = seckillMapper.reduceNumber(seckillId, new Date());
                if (reduceCount <= 0) {
                    // 秒杀结束
                    throw new SeckillCloseException(SeckillResult.END);
                } else {
                    SuccessKilled successKilled = successKilledMapper.selectByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillResult.SUCCESS, successKilled);
                }

            }
            //TODO
        }
        catch (RepeatKillException e1) {
            throw e1;
        }
        catch (SeckillCloseException e2) {
            throw e2;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SeckillException(SysResult.SERVER_ERROR);
        }
    }

    @Override
    public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5) {
        if (StringUtils.isBlank(md5) || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException(SeckillResult.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            seckillMapper.killByProcedure(map);
            Integer result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SuccessKilled successKilled = successKilledMapper.selectByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillResult.SUCCESS, successKilled);
            }else {
                return new SeckillExecution(seckillId, EnumUtil.getByCode(result, SeckillResult.class));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillResult.INNER_ERROR);
        }
    }
}
