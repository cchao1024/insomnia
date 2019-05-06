package me.cchao.insomnia.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : cchao
 * @version 2019-02-13
 */
@Slf4j
public class Logs {

    public static void println(Object... args) {
        log.info(getFormat(args));
    }

    public static void logException(Object... args) {
        log.error(getFormat(args));
    }

    public static String getFormat(Object... args) {
        String content = "";
        for (Object arg : args) {
            content += "【" + arg + "】";
        }
        return content;
    }
}
