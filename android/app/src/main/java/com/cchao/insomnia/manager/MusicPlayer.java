package com.cchao.insomnia.manager;

import android.media.MediaPlayer;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.model.javabean.fall.FallMusic;
import com.cchao.simplelib.LibCore;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.PrefHelper;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.danikula.videocache.HttpProxyCacheServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.reactivex.schedulers.Schedulers;

/**
 * @author cchao
 * @version 8/14/18.
 */
public class MusicPlayer {


    private static HttpProxyCacheServer proxy;
    private static MediaPlayer mMediaPlayer = new MediaPlayer();
    private static FallMusic mCurMusic;
    private static FallMusic mPreMusic;
    public static List<FallMusic> mPlayList = new ArrayList<>();
    static Stack<FallMusic> mPlayHistory = new Stack<>();
    static int mPlayIndex = 0;

    static String mPlayMode = PrefHelper.getString(Constants.Prefs.Play_Mode, Constants.Play_Mode.RANDOM);
    public static String mCurState = State.Init;

    public interface State {
        String Prepare = "prepare";
        String Playing = "Playing";
        String Pause = "Pause";
        String Init = "Init";
    }

    public static MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public static long getCurPlayingId() {
        if (mCurMusic != null) {
            return mCurMusic.getId();
        }
        return 0;
    }

    public static long getPrePlayingId() {
        if (mPreMusic != null) {
            return mPreMusic.getId();
        }
        return 0;
    }

    public static void init() {
        mMediaPlayer.setOnErrorListener((mediaPlayer, i, i1) -> {
            UiHelper.showToast(R.string.music_play_error);
            updateState(State.Init);
            return false;
        });

        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            // 请求+播放计数
            RetrofitHelper.getApis().play(String.valueOf(mCurMusic.getId()))
                .subscribeOn(Schedulers.io())
                .subscribe(RxHelper.getNothingObserver());
            mMediaPlayer.start();
            if (!mPlayHistory.empty() && !isCurPlaying(mPlayHistory.peek())) {
                mPlayHistory.push(mCurMusic);
            }
            // 非单曲循环 赋值 index
            if (!mPlayMode.equals(Constants.Play_Mode.SINGLE_LOOP)) {
                for (int i = 0; i < mPlayList.size(); i++) {
                    if (mCurMusic == mPlayList.get(i)) {
                        mPlayIndex = i;
                    }
                }
            }
            updateState(State.Playing);
            UiHelper.showToast(UiHelper.getString(R.string.playing) + mCurMusic.getName());
        });

        mMediaPlayer.setOnCompletionListener(mp -> {
            // 非单曲循环
            if (!mPlayMode.equals(Constants.Play_Mode.SINGLE_LOOP)) {
                playNext();
            }
        });
    }

    public static boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public static boolean isCurPlaying(FallMusic fallMusic) {
        return fallMusic.getId() == getCurPlayingId();
    }

    public static void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            updateState(State.Pause);
        }
    }

    public static void playOrPause() {
        if (isPlaying()) {
            pause();
        } else if (mCurState.equals(State.Pause)) {
            mMediaPlayer.start();
            updateState(State.Playing);
        } else {
            playNext();
        }
    }

    private static void updateState(String curState) {
        RxBus.get().postEvent(Constants.Event.Update_Play_Status, curState);
        mCurState = curState;
    }

    public static void playOrPause(FallMusic item) {
        if (getCurPlayingId() == item.getId()) {
            pause();
        } else {
            playNow(item);
        }
    }

    public static void addToPlayList(FallMusic item) {
        for (FallMusic fallMusic : mPlayList) {
            if (fallMusic.getId() == item.getId()) {
                UiHelper.showToast(R.string.exist_play_list);
                return;
            }
        }
        mPlayList.add(item);
    }

    public static void clearAndStop() {
        updateState(State.Init);
        mPlayList.clear();
        mPlayHistory.clear();
        mCurMusic = null;
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public static void removeFromPlayList(FallMusic item) {
        if (mCurMusic == item && mPlayList.size() <= 1) {
            clearAndStop();
            return;
        }
        mPlayHistory.remove(item);
        mPlayList.remove(item);
    }

    public static void playNext() {
        if (mPlayMode.equals(Constants.Play_Mode.SINGLE_LOOP)) {
            return;
        }
        if (mPlayList.size() <= 1) {
            return;
        }
        // 随机播放
        FallMusic item = mPlayList.get((mPlayIndex + 1) % mPlayList.size());
        if (mPlayMode.equals(Constants.Play_Mode.RANDOM)) {
            item = mPlayList.get((mPlayIndex * 17 + 1) % mPlayList.size());
        }
        prepareAsync(item);
    }

    public static void playPre() {
        if (mPlayMode.equals(Constants.Play_Mode.SINGLE_LOOP)) {
            return;
        }
        prepareAsync(mPlayHistory.pop());
    }

    private static void prepareAsync(FallMusic item) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(getPlaySrc(item));
        } catch (IOException e) {
            Logs.logException(e);
        }
        mMediaPlayer.setLooping(mPlayMode.equals(Constants.Play_Mode.SINGLE_LOOP));
        mMediaPlayer.prepareAsync();
        mPreMusic = mCurMusic;
        mCurMusic = item;
    }

    public static void playNow(FallMusic item) {
        if (isPlaying() && item == mCurMusic) {
            return;
        }
        if (!mPlayList.contains(item)) {
            mPlayList.add(item);
        }

        Logs.logEvent("playNow " + item.getSrc());
        prepareAsync(item);
        updateState(State.Prepare);
    }

    private static String getPlaySrc(FallMusic fallMusic) {
        return getProxy().getProxyUrl(fallMusic.getSrc());
    }

    /**
     * 缓存代理
     */
    public static HttpProxyCacheServer getProxy() {
        if (proxy == null) {
            proxy = new HttpProxyCacheServer.Builder(LibCore.getContext())
                // 1 Gb for cache
                .maxCacheSize(1024 * 1024 * 1024)
                .maxCacheFilesCount(40)
                .build();
        }
        return proxy;
    }
}
