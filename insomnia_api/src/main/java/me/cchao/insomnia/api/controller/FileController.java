package me.cchao.insomnia.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import me.cchao.insomnia.api.bean.resp.global.FileUpload;
import me.cchao.insomnia.api.config.GlobalConfig;
import me.cchao.insomnia.api.util.FileUtil;
import me.cchao.insomnia.api.util.Logs;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.common.constant.Results;

import static me.cchao.insomnia.api.business.ImagePathConvert.joinRemotePath;

/**
 * @author : cchao
 * @version 2019-03-12
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    GlobalConfig mGlobalConfig;

    @RequestMapping("/upload")
    public RespBean<FileUpload> upload(@RequestParam(value = "type", defaultValue = "user_post") String type
            , @RequestParam("file") MultipartFile uploadingFile) throws IOException {

        if (uploadingFile == null) {
            throw new IllegalArgumentException(Results.PARAM_EMPTY.getMessage());
        }
        String uploadDir = GlobalConfig.getUploadDir("image");
        String defSuffix = ".png";
        switch (type) {
            case "user_avatar":
                uploadDir = "/avatar" + GlobalConfig.getTimeFormatPath();
                break;
            case "fall_image":
                uploadDir = "/image" + GlobalConfig.getTimeFormatPath();
                break;
            case "fall_music":
                uploadDir = "/music" + GlobalConfig.getTimeFormatPath();
                defSuffix = ".mp3";
                break;
        }

        // 后缀
        String suffix = FileUtil.getFileSuffix(uploadingFile.getOriginalFilename(), defSuffix);
        // 待上传的 文件信息
        String uploadFileName = GlobalConfig.getUploadFileName(suffix);
        String relativePath = uploadDir + uploadFileName;
        // 临时保存在 web服务器
        File tempFile = GlobalConfig.getTempSaveFile(uploadDir, uploadFileName);
        Logs.println("保存文件到 " + tempFile.getAbsolutePath());
        uploadingFile.transferTo(tempFile);
        // 传到文件服务器
//        FileServiceManager.uploadFile(relativePath, tempFile);
        // 返回值
        FileUpload fileUpload = new FileUpload();
        fileUpload.setAbsoluteUrl(joinRemotePath(relativePath))
                .setRelativeUrl(relativePath)
                .setFileName(uploadFileName);
        return RespBean.suc(fileUpload);
    }
}
