package me.cchao.insomnia.api.util;

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
        System.out.println(content);
    }
}
