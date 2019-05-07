package me.cchao.insomnia.bean.resp.fall;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import me.cchao.insomnia.dao.FallBanner;
import me.cchao.insomnia.dao.FallImage;
import me.cchao.insomnia.dao.FallMusic;

@Data
@Accessors(chain = true)
public class FallIndex {
    List<FallBanner> banners;
    List<FallImage> fallImages;
    List<FallMusic> music;
}
