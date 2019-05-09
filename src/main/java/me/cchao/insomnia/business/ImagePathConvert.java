package me.cchao.insomnia.business;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import me.cchao.insomnia.config.GlobalConfig;
import me.cchao.insomnia.dao.User;

/**
 * 图片资源的绝对路径追加
 */
public class ImagePathConvert {

    public static String joinRemotePath(String relativePath) {
        if (StringUtils.isEmpty(relativePath)) {
            return "";
        }
        // 以 http 开头默认已经不是相对路径
        if (relativePath.startsWith("http")) {
            return relativePath;
        }
        return (GlobalConfig.sourceServerPath + relativePath).replaceAll("//", "/");
    }

    /**
     * 图片或资源，拼接远程路径，
     *
     * @param object 传入类型，通过 instanceof 判断拼接
     * @param <T>    类型
     * @return T
     */
    public static <T> T joinRemotePath(T object) {
        if (object == null) {
            return null;
        }
        if (object instanceof User) {
            User user = (User) object;
            user.setAvatar(joinRemotePath(user.getAvatar()));
        }
        return object;
    }

    /**
     * 将  xx,yy,zz 切割成List类型并追加图片服务器路径 返回
     *
     * @param images 数据库中的图片相对路径字符串
     * @return list
     */
    public static List<String> convertImageList(String images) {
        if (StringUtils.isEmpty(images)) {
            return new ArrayList<>();
        }
        List<String> list = Arrays.stream(StringUtils.split(images, ","))
            .map(new Function<String, String>() {
                @Override
                public String apply(String s) {
                    return joinRemotePath(s);
                }
            }).collect(Collectors.toList());
        return list;
    }
}
