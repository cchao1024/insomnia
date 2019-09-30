package com.cchao.insomnia.model.javabean.compose;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 音频 实体
 *
 * @author cchao
 * @version 2019-07-31.
 */
@Data
@AllArgsConstructor
public class AudioBean {
    int name;
    int src;
    int image;
    float volume;

    public static AudioBean of(int name, int src, int image) {
        return new AudioBean(name, src, image, 0.5f);
    }
}
