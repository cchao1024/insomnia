package me.cchao.insomnia.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import me.cchao.insomnia.api.bean.resp.global.FileUpload;
import me.cchao.insomnia.api.business.FileServiceManager;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.common.constant.Results;
import me.cchao.insomnia.api.config.GlobalConfig;
import me.cchao.insomnia.api.util.Logs;

/**
 * @author : cchao
 * @version 2019-03-12
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    GlobalConfig mGlobalConfig;

    @RequestMapping("/uploadImage")
    public RespBean upload(@RequestParam("file") MultipartFile[] uploadingFiles, HttpServletRequest request) throws IOException {
        if (uploadingFiles.length == 0) {
            return RespBean.fail(Results.FILE_EMPTY);
        }

        String uploadFileName = GlobalConfig.getUploadFileName(".png");
        String uploadDir = GlobalConfig.getUploadDir("image");
        String relativePath = uploadDir + uploadFileName;
        // 临时保存在 web服务器
        File tempFile = GlobalConfig.getTempSaveFile(uploadDir, uploadFileName);
        Logs.println("保存文件到 " + tempFile.getAbsolutePath());
        for (MultipartFile uploadedFile : uploadingFiles) {
            uploadedFile.transferTo(tempFile);
        }
        // 传到文件服务器
        FileServiceManager.uploadFile(relativePath, tempFile);
        // 返回值
        FileUpload fileUpload = new FileUpload();
        fileUpload.setAbsoluteUrl(GlobalConfig.getUploadAbsolutePath(relativePath))
            .setRelativeUrl(relativePath);
        return RespBean.suc(fileUpload);
    }
}
