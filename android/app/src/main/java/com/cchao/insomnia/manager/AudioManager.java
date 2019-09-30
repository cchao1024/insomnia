package com.cchao.insomnia.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.model.javabean.compose.AudioBean;
import com.cchao.simplelib.LibCore;
import com.cchao.simplelib.core.RxBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 音频管理
 *
 * @author cchao
 * @version 2019-08-01.
 */
public class AudioManager {
    private static List<MediaPlayer> mPlayers = new ArrayList<>();
    public static Map<MediaPlayer, Integer> mPlayerMap = new HashMap<>(8);

    static {
        mPlayers.add(new MediaPlayer());
        mPlayers.add(new MediaPlayer());
        mPlayers.add(new MediaPlayer());
        mPlayers.add(new MediaPlayer());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void play(AudioBean audioBean) {
        // 如果已经是播放状态 就暂停它
        if (getMpFromBean(audioBean) != null && getMpFromBean(audioBean).isPlaying()) {
            pause(audioBean);
            return;
        }
        // 取出空闲的 player 去播放
        for (MediaPlayer mp : mPlayers) {
            if (mp.isPlaying()) {
                continue;
            }
            mp.reset();
            mPlayerMap.remove(audioBean.getSrc());

            AssetFileDescriptor file = LibCore.getContext().getResources().openRawResourceFd(audioBean.getSrc());
            try {
                mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
                mp.prepare();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.setVolume(audioBean.getVolume(), audioBean.getVolume());
            mp.setLooping(true);
            mp.start();
            mPlayerMap.put(mp, audioBean.getSrc());
            RxBus.get().postEvent(Constants.Event.play_audio, audioBean);
            break;
        }
        // 没有空闲的
    }

    public static void pause(AudioBean audioBean) {
        // 移除映射关系
        MediaPlayer mediaPlayer = getMpFromBean(audioBean);
        mediaPlayer.pause();
        mPlayerMap.remove(mediaPlayer);
        RxBus.get().postEvent(Constants.Event.pause_audio, audioBean);
    }

    public static void changeVolume(AudioBean audioBean) {
        MediaPlayer mediaPlayer = getMpFromBean(audioBean);
        mediaPlayer.setVolume(audioBean.getVolume(), audioBean.getVolume());

    }

    public static MediaPlayer getMpFromBean(AudioBean audioBean) {
        for (Map.Entry<MediaPlayer, Integer> entry : mPlayerMap.entrySet()) {
            if (entry.getValue().equals(audioBean.getSrc())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
