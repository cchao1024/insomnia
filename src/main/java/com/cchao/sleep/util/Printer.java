package com.cchao.sleep.util;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author : cchao
 * @version 2019-02-13
 */
public class Printer {

    public static void print(Object... args) {
        String content = "";
        for (Object arg : args) {
            content += "[" + arg + "]";
        }
        System.out.print(content);
    }
}
