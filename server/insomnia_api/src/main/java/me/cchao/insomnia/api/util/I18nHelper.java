package me.cchao.insomnia.api.util;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import me.cchao.insomnia.api.bean.resp.Results;
import me.cchao.insomnia.api.exception.CommonException;

/**
 * 多语言 工具类
 *
 * @author cchao
 * @version 2019-07-08.
 */
@Component
public class I18nHelper {

    public static String getMessage(String code) {
        try {
            return SpringUtil.getContext().getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            Logs.logException("获取国际化资源{}失败" + e.getMessage(), code, e);
            throw CommonException.of(Results.ERROR);
        }
    }
}
