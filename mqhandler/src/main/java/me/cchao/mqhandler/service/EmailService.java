package me.cchao.mqhandler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import me.cchao.mqhandler.bean.RespBean;
import me.cchao.mqhandler.bean.Results;

/**
 * @author cchao
 * @version 2019-06-22.
 */
@Service
public class EmailService {

    public static final String Key_Prefix = "verifyCode__";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成验证码，结合 email 放入redis
     * 10 min 有效
     *
     * @return code 验证码
     */
    public String generateVerifyCode(String email) {
        String code = String.format("%06d", System.currentTimeMillis() % 100000);
        String key = Key_Prefix + email;
        redisTemplate.opsForValue().set(key, code, 10, TimeUnit.MINUTES);
        return code;
    }

    public RespBean verifyEmail(String email, String code) {
        String key = Key_Prefix + email;
        if (redisTemplate.hasKey(key) == Boolean.FALSE) {
            return RespBean.fail(Results.CODE_NOT_EXIST);
        }

        if (code.equals(redisTemplate.opsForValue().get(key))) {
            redisTemplate.delete(key);
            return RespBean.suc("邮箱验证成功");
        }
        return RespBean.fail(Results.VERIFY_FAIL);
    }
}
