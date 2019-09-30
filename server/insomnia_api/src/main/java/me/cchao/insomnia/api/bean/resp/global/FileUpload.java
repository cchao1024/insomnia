package me.cchao.insomnia.api.bean.resp.global;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-04-25
 */
@Data
@Accessors(chain = true)
public class FileUpload {
    String relativeUrl;
    String absoluteUrl;
    String fileName;
}
