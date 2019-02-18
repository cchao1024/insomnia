package com.cchao.sleep.service;

import com.cchao.sleep.constant.enums.Results;
import com.cchao.sleep.dao.User;
import com.cchao.sleep.exception.CommonException;
import com.cchao.sleep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author : cchao
 * @version 2019-01-31
 */
@Service
public class UserService {

    @Autowired
    UserRepository mUserRepository;

    public User findUserById(long id) {
        return mUserRepository.getOne(id);
    }

    public User findUserByEmail(String name) {
        return mUserRepository.findByEmail(name);
    }

    /**
     * 注册
     */
    public User signUp(String email, String password) {
        if (!Pattern.matches("\\w{4,12}", email)) {
            throw CommonException.of(Results.FAIL.msg("用户名需为4到12位英文和数字组合"));
        }

        // 用户名已被占用
        if (mUserRepository.findByEmail(email) != null) {
            throw CommonException.of(Results.FAIL.msg("用户名已被占用"));
        }

        if (!Pattern.matches("\\w{6,12}", password)) {
            throw CommonException.of(Results.FAIL.msg("密码需为6到12位英文和数字组合"));
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("user");
        return mUserRepository.save(user);
    }

    /**
     * 更新用户信息
     */
    public User saveUserInfo(String email, Map<String, String> map) {
        User user = mUserRepository.findByEmail(email);
        if (user == null) {
            throw CommonException.of(Results.PARAM_ERROR.msg("用户不存在"));
        }

        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                switch (entry.getKey()) {
                    case "nickname":
                        user.setNickname(entry.getValue());
                        break;
                    case "age":
                        user.setAge(Integer.valueOf(entry.getValue()));
                        break;
                }
            }
            mUserRepository.save(user);
        } catch (Exception ex) {
            throw CommonException.of(Results.PARAM_ERROR);
        }
        return user;
    }
}
