package me.cchao.insomnia.bean.resp.fall;

import me.cchao.insomnia.dao.FallBanner;
import me.cchao.insomnia.dao.FallImage;
import me.cchao.insomnia.dao.FallMusic;
import lombok.Data;

import java.util.List;

@Data
public class FallIndex {
    List<FallBanner> banners;
    List<FallImage> fallImages;
    List<FallMusic> music;
}
