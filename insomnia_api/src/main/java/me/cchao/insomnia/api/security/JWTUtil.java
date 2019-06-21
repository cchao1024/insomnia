package me.cchao.insomnia.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import me.cchao.insomnia.api.service.UserService;
import me.cchao.insomnia.common.constant.Constant;

import static me.cchao.insomnia.common.constant.Constant.AUTHORIZATION_HEADER_NAME;

/**
 * @author : cchao
 * @version 2019-01-31
 */
public class JWTUtil {
    // 过期时间
    private static final long EXPIRE_TIME = 100 * 24 * 3600 * 1000L;

    @Autowired
    UserService userService;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, long userId, String secret) {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim(Constant.USER_NAME, username)
                .withClaim(Constant.USER_ID, userId)
                .build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(Constant.USER_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getToken(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME);
    }

    public static boolean haveToken(HttpServletRequest httpServletRequest) {
        return StringUtils.isNoneEmpty(getToken(httpServletRequest));
    }

    public static long getUserId(HttpServletRequest httpServletRequest) {
        return getUserId(getToken(httpServletRequest));
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(Constant.USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return 0;
        }
    }

    /**
     * 生成签名
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String createToken(String username, long id, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim(Constant.USER_NAME, username)
                .withClaim(Constant.USER_ID, id)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
