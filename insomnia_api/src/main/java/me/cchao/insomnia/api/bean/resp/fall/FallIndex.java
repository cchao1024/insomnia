package me.cchao.insomnia.api.bean.resp.fall;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import me.cchao.insomnia.api.domain.FallBanner;
import me.cchao.insomnia.api.domain.FallImage;
import me.cchao.insomnia.api.domain.FallMusic;

@Data
@Accessors(chain = true)
public class FallIndex {
    List<FallBanner> banners;
    List<FallImage> fallImages;
    List<FallMusic> music;
}
