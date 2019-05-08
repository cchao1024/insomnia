package me.cchao.insomnia.config;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import lombok.Data;
import me.cchao.insomnia.dao.User;

/**
 * @author : cchao
 * @version 2019-03-12
 */
@Data
@ConfigurationProperties(prefix = "project")
@Component
public class GlobalConfig {

    /**
     * 文件上传的保存路径
     */
    public String uploadDirPath = "/www/file/upload/";
    @Value("${sourceServerPath}")
    public static final String sourceServerPath = "https://insomnia-1254010092.cos.ap-hongkong.myqcloud.com/";
    @Value("${defaultAvatarPath}")
    public static final String defaultAvatarPath = sourceServerPath + "common/default/avatar.png";
    public static final String defaultAvatar = "common/default/avatar.png";
    public static String mUserHome = System.getProperty("user.home");

    public static String getSourceServerPath() {
        return sourceServerPath;
    }

    public static String joinRemotePath(String relativePath) {
        // 以 http 开头默认已经不是相对路径
        if (relativePath.startsWith("http")) {
            return relativePath;
        }
        return (sourceServerPath + relativePath).replaceAll("//", "/");
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

    public static String getUploadFileName(String suffix) {
        return System.currentTimeMillis() % 100000 + RandomUtils.nextInt() % 10000 + suffix;
    }

    public static String getUploadDir(String type) {
        return "/upload/" + type + "/" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy_MM_dd/");
    }

    public static String getUploadAbsolutePath(String relativePath) {
        // https://insomnia-1254010092.cos.ap-hongkong.myqcloud.com/common/default/avatar.png
        return "https://insomnia-1254010092.cos.ap-hongkong.myqcloud.com" + relativePath;
    }

    public static File getTempSaveFile(String uploadDirPath, String fileName) {
        String dir = mUserHome + uploadDirPath;
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }
        return new File(dir + fileName);
    }
}
