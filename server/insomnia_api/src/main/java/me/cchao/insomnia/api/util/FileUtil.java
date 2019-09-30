package me.cchao.insomnia.api.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author cchao
 * @version 2019-05-18.
 */
public class FileUtil {

    public static String getFileSuffix(String orignalName, String defaultV) {
        if (StringUtils.isEmpty(orignalName)) {
            return defaultV;
        }
        int dotIndex = orignalName.lastIndexOf('.');
        if (dotIndex == -1) {
            return defaultV;
        }
        return orignalName.substring(dotIndex);
    }
}
