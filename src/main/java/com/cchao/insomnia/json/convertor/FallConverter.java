package com.cchao.insomnia.json.convertor;

import com.cchao.insomnia.dao.FallImage;
import com.cchao.insomnia.dao.WishImage;
import com.cchao.insomnia.json.fall.FallImageVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : cchao
 * @version 2019-02-02
 */
public class FallConverter {

    public static List<FallImageVo> convert(List<FallImage> fallImages) {
        return fallImages.stream().map(e -> {
                    FallImageVo wishImageVo = new FallImageVo();
                    BeanUtils.copyProperties(e, wishImageVo);
                    return wishImageVo;
                }
        ).collect(Collectors.toList());
    }
}
