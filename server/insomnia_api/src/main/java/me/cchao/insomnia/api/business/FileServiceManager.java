package me.cchao.insomnia.api.business;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;

/**
 * 文件服务器
 *
 * @author : cchao
 * @version 2019-04-25
 */
public class FileServiceManager {

    private static final String SecretId = "AKIDHqFG2Y1rihQt6xqw41A4h4HdnjhzeoS6";
    private static final String SecretKey = "nWSYS6XO1OVM6C3ez4tSkwve6RZRSQWR";
    private static final String bucketName = "insomnia-1254010092";
    private static COSClient cosClient = null;

    static {
        init();
    }

    private static void init() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(SecretId, SecretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        ClientConfig clientConfig = new ClientConfig(new Region("ap-hongkong"));
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        //        String bucketName = "mybucket-1251668577";
    }

    public static PutObjectResult uploadFile(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return putObjectResult;
    }
}
