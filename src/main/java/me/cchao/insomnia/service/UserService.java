package me.cchao.insomnia.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.cchao.insomnia.bean.req.user.EditUserDTO;
import me.cchao.insomnia.bean.req.user.UserLoginDTO;
import me.cchao.insomnia.bean.resp.user.UpdateUser;
import me.cchao.insomnia.config.GlobalConfig;
import me.cchao.insomnia.constant.enums.Results;
import me.cchao.insomnia.dao.User;
import me.cchao.insomnia.exception.CommonException;
import me.cchao.insomnia.exception.SystemErrorMessage;
import me.cchao.insomnia.repository.UserRepository;
import me.cchao.insomnia.security.JWTUtil;

/**
 * @author : cchao
 * @version 2019-01-31
 */
@CacheConfig(cacheNames = "User")
@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository mUserRepository;

    @Cacheable(key = "'user_id_' + #id", unless = "#result eq null")
    public User findUserById(Long id) {
        log.info("UserService#findUserById:" + id);
        return GlobalConfig.joinRemotePath(mUserRepository.getOne(id));
    }

    public void increaseLike(Long id) {
        // 用户 like +1
        User user = mUserRepository.getOne(id);
        mUserRepository.save(user.increaseLike());
    }

    @Cacheable(key = "'user_email_' + #id", unless = "#result eq null")
    public User findUserByEmail(String email) {
        log.info("UserService#findUserByEmail:" + email);
        return GlobalConfig.joinRemotePath(mUserRepository.findByEmail(email)
            .orElse(null));
    }

    public UpdateUser login(UserLoginDTO params) {
        String email = params.getEmail();
        String password = params.getPassword();

        User user = findUserByEmail(email);
        // 检验密码是否正确
        boolean validPassword = StringUtils.equals(user.getPassword(), password);
        if (validPassword) {
            // 登录成功 返回 token
            String token = JWTUtil.createToken(email, user.getId(), password);

            return wrapUpdateUser(user, token);
        } else {
            throw new CommonException(SystemErrorMessage.USER_PASSWORD_INVALID);
        }
    }

    private UpdateUser wrapUpdateUser(User user, String token) {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setToken(token);
        BeanUtils.copyProperties(user, updateUser);
        return updateUser;

    }

    public UpdateUser updateToken(String token) {
        long userId = JWTUtil.getUserId(token);

        User user = findUserById(userId);
        // 刷新token
        String newToken = JWTUtil.createToken(user.getEmail(), user.getId(), user.getPassword());
        // 构建返回 携带Token的用户实体
        return wrapUpdateUser(user, newToken);
    }

    /**
     * 游客注册
     */
    public UpdateUser visitorSignup() {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String nameLabel = timeStamp.substring(timeStamp.length() - 5);
        String defPwd = "123456";
        String defEmail = nameLabel + "@qq.com";
        User user = new User()
            .setVisitor(1)
            .setEmail(defEmail)
            .setNickName("游客" + nameLabel)
            .setPassword(defPwd);

        // 写入库
        user = mUserRepository.save(user);
        String token = JWTUtil.createToken(defEmail, user.getId(), defPwd);
        return wrapUpdateUser(user, token);
    }

    /**
     * 更新用户信息
     */
    public UpdateUser saveUserInfo(long id, EditUserDTO reqUser) {
        User user = findUserById(id);
        user.setVisitor(0);

        try {
            BeanUtils.copyProperties(reqUser, user);
            mUserRepository.save(user);
        } catch (Exception ex) {
            throw CommonException.of(Results.PARAM_ERROR);
        }
        UpdateUser result = new UpdateUser();
        BeanUtils.copyProperties(user, result);
        // 生成新的token
        result.setToken(JWTUtil.createToken(result.getEmail(), result.getId(), result.getPassword()));
        return result;
    }
}
