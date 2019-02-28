package com.cchao.sleep.service;

import com.cchao.sleep.bean.req.UserLoginDTO;
import com.cchao.sleep.constant.enums.Results;
import com.cchao.sleep.dao.User;
import com.cchao.sleep.exception.CommonException;
import com.cchao.sleep.exception.SystemErrorMessage;
import com.cchao.sleep.json.user.LoginResp;
import com.cchao.sleep.repository.UserRepository;
import com.cchao.sleep.security.JWTUtil;
import org.apache.commons.lang3.StringUtils;
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

    public User findUserByEmail(String email) {
        return
                mUserRepository.findByEmail(email)
                        .orElseThrow(() -> new CommonException(SystemErrorMessage.USER_NOT_EXISTED));
    }

    public LoginResp login(UserLoginDTO params) {
        String
                email = params.getEmail(),
                password = params.getPassword();

        User user = findUserByEmail(email);
        boolean validPassword = StringUtils.equals(user.getPassword(), password);
        if (validPassword) {
            String token = JWTUtil.sign(email, user.getId(), password);

            return
                    new LoginResp()
                            .setAge(user.getAge())
                            .setNikeName(user.getNickname())
                            .setEmail(email)
                            .setToken(token);
        } else {
            throw new CommonException(SystemErrorMessage.USER_PASSWORD_INVALID);
        }
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
        User user = findUserByEmail(email);

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
