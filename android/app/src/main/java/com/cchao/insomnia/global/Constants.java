package com.cchao.insomnia.global;

import com.cchao.insomnia.BuildConfig;
import com.cchao.insomnia.R;
import com.cchao.simplelib.core.UiHelper;

/**
 * 常量
 */
public class Constants {
    // 防用户抖动间隔
    public static final int SHAKE_INTERVAL = 600;
    public static final String TEST_IMAGE_PATH = "http://d6.yihaodianimg.com/V00/M00/3E/5C/CgQDslSNDEyAQp-mAAHoVWDzhu877700_380x380.jpg";

    public interface RequestCode {
        int TAKE_IMAGE = 333;
    }

    public interface Config {

//        String API_Host = "http://127.0.0.1:8080/";
        String API_Host = "http://47.240.35.14:8080/";

        // 接口API版本
        String API_BUILD = String.valueOf(BuildConfig.VERSION_CODE);

        // 网络访问超时时间
        int TIMEOUT = 60 * 1000;

        int PAGE_SIZE = 20;

        int MAX_POST_IMAGE = 5;
    }

    public interface Type {
        int FALL_IMAGE = 1;
        int FALL_MUSIC = 2;
        int UP_IMAGE = 3;
        int UP_MUSIC = 4;
    }

    public interface Event {
        int Update_Play_Status = 61;
        int Update_Post_Box = 10001;
        int update_count_down = 1002;
        int update_post_like_count = 1003;
        int update_post_comment_count = 1004;
        int play_audio = 2001;
        int pause_audio = 2002;
    }

    public interface Token {
        String daodao = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IjEwMDBlZWVAcXEuY29tIiwiZXhwIjoxNTY3NzYwMDg2LCJ1c2VySWQiOjV9.QxPQtx2veDSH5F8ltrpRJ4gu33uU0jg281DrpMH7fFg";
        String anye = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6Ijk5OTk5OUBxcS5jb20iLCJleHAiOjE1Njc3NjAyMDMsInVzZXJJZCI6NH0.uYdVyy0oB8m_UPLj2sNAAJrkOUHCvUfrFpGk5j39Ya0";
        String dongshan = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6Ijg4ODg4OEBxcS5jb20iLCJleHAiOjE1Njc3NjAzNjYsInVzZXJJZCI6M30.SRDM9fBaLlYtZPoTxOAyCFFF_sJmnRk9XmE6UEPXiYU";
        String y360 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IjM2MDY2NkB5YWhvby5jb20iLCJleHAiOjE1Njc4MjU4NjEsInVzZXJJZCI6Mjl9.Hj8dlt_djw9DHdG7LqojonQQlpX5ulfxaC_5cmFyh_g";
    }

    public interface Prefs {
        String USER_INFO = "user_info";
        String USER_NAME = "user_name";
        String USER_EMAIL = "user_email";
        String LOGIN_TIPS = "login_tips";
        String MUSIC_PLAY_MODE = "music_play_mode";
        String INIT_TIME_STAMP = "INIT_TIME_STAMP";
        String WISH_LIST = "wish_list";
        String Play_Mode = "play_mode";
    }

    // 后端响应Code
    public interface ApiResp {
        String CODE_SUC = "00";
        String CODE = "code";
    }

    // 播放模式
    public interface Play_Mode {
        String SINGLE_LOOP = UiHelper.getString(R.string.mode_single_loop);
        String RANDOM = UiHelper.getString(R.string.mode_random);
        String LIST_LOOP = UiHelper.getString(R.string.mode_loop);

    }

    public interface Extra {
        String IMAGE_URL = "image_url";
        String ID = "commentId";
        String TITLE = "title";
        String Fragment = "Fragment";
    }

    public interface Container {
        String PlayGame = "PlayGame";
    }

    public interface Drawer {
        int Divider = 1111;
        int Wish = 1001;
        int FeedBack = 1002;
        int AboutUs = 1003;
        int TimeDown = 1004;
        int Settings = 1005;
        int Lang = 1006;
    }

    /**
     * api 追加的通用参数
     */
    public interface Api_Appand {
        String App_Build = "appBuild";
        String Device_No = "deviceNo";
        String Page_Size = "pageSize";
        String Lang = "lang";
    }
}
