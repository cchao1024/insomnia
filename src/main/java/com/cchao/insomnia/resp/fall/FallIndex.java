package com.cchao.insomnia.resp.fall;

import com.cchao.insomnia.dao.FallBanner;
import com.cchao.insomnia.dao.FallImage;
import com.cchao.insomnia.dao.FallMusic;
import lombok.Data;

import java.util.List;

@Data
public class FallIndex {
    List<FallBanner> banners;
    List<FallImage> fallImages;
    List<FallMusic> music;
}
