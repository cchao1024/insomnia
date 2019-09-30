package com.cchao.insomnia.model.javabean.post;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : cchao
 * @version 2019-04-25
 */
@Data
@Accessors(chain = true)
public class UploadImageBean {
    String localUri = "";
    String relativeUrl = "";
    String absoluteUrl = "";
}
