package com.cchao.insomnia.manager;

import android.content.Context;
import android.content.Intent;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.model.javabean.RespBean;
import com.cchao.insomnia.model.javabean.compose.AudioBean;
import com.cchao.insomnia.model.javabean.global.AppLaunch;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 处理全局的业务任务
 *
 * @author : cchao
 * @version 2019-04-14
 */
public class GlobalHelper {
    public static List<List<AudioBean>> mAudioList = new ArrayList<>();

    /**
     * 启动同步获取app信息
     */
    public static void syncAppInfo(Consumer<RespBean<AppLaunch>> consumer, Consumer<Throwable> throwableConsumer) {
        Disposable disposable = RetrofitHelper.getApis().appLaunch()
            .compose(RxHelper.toMain())
            .subscribe(respBean -> {
                UserManager.setUserBean(respBean.getData().getUserInfo());
                if (consumer != null) {
                    consumer.accept(respBean);
                }
            }, throwableConsumer);
    }

    public static void shareImage(Context context, String uri) {
        String title = UiHelper.getString(R.string.share_image_title);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, uri);
        context.startActivity(Intent.createChooser(intent, title));
    }

    public static void shareMusic(Context context, String uri) {
        String title = UiHelper.getString(R.string.share_image_title);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, uri);
        context.startActivity(Intent.createChooser(intent, title));
    }

    public static List<List<AudioBean>> getAudioList() {
        if (!mAudioList.isEmpty()) {
            return Collections.unmodifiableList(mAudioList);
        }

        // rain
        List<AudioBean> list0 = new ArrayList<>();
        list0.add(AudioBean.of(R.string.rain_normal, R.raw.rain_normal, R.drawable.audio_rain));
        list0.add(AudioBean.of(R.string.rain_light, R.raw.rain_light, R.drawable.audio_rain));
        list0.add(AudioBean.of(R.string.rain_ocean, R.raw.rain_ocean, R.drawable.audio_rain_ocean));
        list0.add(AudioBean.of(R.string.rain_on_leaves, R.raw.rain_on_leaves, R.drawable.audio_rain_leaves));
        list0.add(AudioBean.of(R.string.rain_on_roof, R.raw.rain_on_roof, R.drawable.audio_rain_roof2));
        list0.add(AudioBean.of(R.string.rain_on_windos, R.raw.rain_on_window, R.drawable.audio_rain_window2));
        list0.add(AudioBean.of(R.string.rain_thunders, R.raw.rain_thunders, R.drawable.audio_rain_thunders));
        list0.add(AudioBean.of(R.string.rain_water, R.raw.rain_water, R.drawable.audio_rain_waterfall));
        list0.add(AudioBean.of(R.string.rain_under_umbrella, R.raw.rain_under_umbrella, R.drawable.audio_rain_umbrella));
        mAudioList.add(list0);

        // forest
        List<AudioBean> list1 = new ArrayList<>();
        list1.add(AudioBean.of(R.string.forest_birds, R.raw.forest_birds, R.drawable.audio_forest_bird));
        list1.add(AudioBean.of(R.string.forest_creek, R.raw.forest_creek, R.drawable.audio_forest_creek));
        list1.add(AudioBean.of(R.string.forest_fire, R.raw.forest_fire, R.drawable.audio_forest_fire));
        list1.add(AudioBean.of(R.string.forest_frogs, R.raw.forest_frogs, R.drawable.audio_forest_frog));
        list1.add(AudioBean.of(R.string.forest_grasshoppers, R.raw.forest_grasshoppers, R.drawable.audio_forest_grasshopper));
        list1.add(AudioBean.of(R.string.forest_leaves, R.raw.forest_leaves, R.drawable.audio_rain_leaves));
        list1.add(AudioBean.of(R.string.forest_waterfall, R.raw.forest_waterfall, R.drawable.audio_rain_waterfall));
        list1.add(AudioBean.of(R.string.forest_wind, R.raw.forest_wind, R.drawable.audio_forest_wind2));
        mAudioList.add(list1);

        // meditation
        List<AudioBean> list2 = new ArrayList<>();
        list2.add(AudioBean.of(R.string.meditation_bells, R.raw.meditation_bells, R.drawable.audio_meditation_bell));
        list2.add(AudioBean.of(R.string.meditation_bowl, R.raw.meditation_bowl, R.drawable.audio_meditation_bowl));
        list2.add(AudioBean.of(R.string.meditation_brown_noise, R.raw.meditation_brown_noise, R.drawable.audio_meditaion_noise));
        list2.add(AudioBean.of(R.string.meditation_flute, R.raw.meditation_flute, R.drawable.audio_meditation_flute));
        list2.add(AudioBean.of(R.string.meditation_piano, R.raw.meditation_piano, R.drawable.audio_meditation_piano));
        list2.add(AudioBean.of(R.string.meditation_pink_noise, R.raw.meditation_pink_noise, R.drawable.audio_meditaion_noise));
        list2.add(AudioBean.of(R.string.meditation_stones, R.raw.meditation_stones, R.drawable.audio_meditation_stones));
        list2.add(AudioBean.of(R.string.meditation_white_noise, R.raw.meditation_white_noise, R.drawable.audio_meditaion_noise));
        mAudioList.add(list2);

        // city
        List<AudioBean> list3 = new ArrayList<>();
        list3.add(AudioBean.of(R.string.city_airplane, R.raw.city_airplane, R.drawable.audio_city_airplane));
        list3.add(AudioBean.of(R.string.city_car, R.raw.city_car, R.drawable.audio_city_car));
        list3.add(AudioBean.of(R.string.city_fan, R.raw.city_fan, R.drawable.audio_city_fan));
        list3.add(AudioBean.of(R.string.city_keyboard, R.raw.city_keyboard, R.drawable.audio_city_keyboard));
        list3.add(AudioBean.of(R.string.city_rails, R.raw.city_rails, R.drawable.audio_rain_leaves));
        list3.add(AudioBean.of(R.string.city_restaurant, R.raw.city_restaurant, R.drawable.audio_city_restaurant));
        list3.add(AudioBean.of(R.string.city_subway, R.raw.city_subway, R.drawable.audio_city_subway));
        list3.add(AudioBean.of(R.string.city_washing_machine, R.raw.city_washing_machine, R.drawable.audio_city_wash));
        mAudioList.add(list3);
        return Collections.unmodifiableList(mAudioList);
    }
}
