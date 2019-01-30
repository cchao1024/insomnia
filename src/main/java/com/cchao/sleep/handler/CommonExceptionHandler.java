package com.cchao.sleep.handler;

import com.cchao.sleep.exception.CommonException;
import com.cchao.sleep.json.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 通用异常拦截
     *
     * @return json
     */
    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public RespBean handlerAuthorizeException(CommonException ex) {
        return RespBean.of(ex.getCode(), ex.getMessage());
    }
}
