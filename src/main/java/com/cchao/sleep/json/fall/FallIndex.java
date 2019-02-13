package com.cchao.sleep.json.fall;

import com.cchao.sleep.dao.FallBanner;
import com.cchao.sleep.dao.FallImage;
import com.cchao.sleep.dao.FallMusic;
import lombok.Data;

import java.util.List;

@Data
public class FallIndex {
    List<FallBanner> banners;
    List<FallImage> fallImages;
    List<FallMusic> music;
}
