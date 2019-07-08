package me.cchao.insomnia.api.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import me.cchao.insomnia.api.bean.resp.RespBean;
import me.cchao.insomnia.api.bean.resp.Results;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlers {

    /**
     * 通用异常拦截
     *
     * @return json
     */
    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public RespBean handlerAuthorizeException(CommonException ex) {
        log.error("ExceptionHandlers", ex);
        return RespBean.of(ex.getCode(), ex.getMessage());
    }

    /**
     * 捕捉shiro的异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    @ResponseBody
    public RespBean handle401(ShiroException e) {
        log.error("ExceptionHandlers", e);
        return RespBean.of(401, e.getMessage());
    }

    /**
     * 捕捉UnauthorizedException
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public RespBean handle401(UnauthorizedException e) {
        log.error("ExceptionHandlers", e);
        return RespBean.of(401, e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = TokenExpiredException.class)
    public RespBean handle401(TokenExpiredException e) {
        log.error("ExceptionHandlers", e);
        return RespBean.fail(Results.TOKEN_EXPIRED);
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public RespBean handle401(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException", e);
        return RespBean.fail(Results.PARAM_MISSING);
    }

    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public RespBean handle401(AuthenticationException e) {
        log.error("ExceptionHandlers", e);
        return RespBean.fail(Results.TOKEN_EXPIRED);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RespBean handleOtherException(Exception e) {
        log.error("ExceptionHandlers", e);
        return RespBean.fail(Results.ERROR);
    }


    /**
     * 参数校验
     *
     * @param ex the ex
     */
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBean methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

        log.error("ExceptionHandlers", ex);
        return RespBean.of(203, errorMsg);
    }

    /**
     * 参数校验
     *
     * @param ex the ex
     */
    @ExceptionHandler(BindException.class)
    public RespBean methodArgumentNotValidException(BindException ex) {
        ex.printStackTrace();
        String errorMsg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

        log.error("ExceptionHandlers", ex);
        return RespBean.fail(Results.PARAM_ERROR).setMsg(errorMsg);
    }
}
