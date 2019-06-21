package me.cchao.insomnia.common.constant;

public interface Constant {
    interface Module {
        int FALL_IMAGE = 1;
        int FALL_MUSIC = 2;
        int UP_IMAGE = 3;
        int UP_MUSIC = 4;
    }

    interface POST_TYPE {
        int Post = 1;
        int Comment = 2;
        int Reply = 3;
    }


    String USER_NAME = "userName";
    String USER_ID = "userId";

    String AUTHORIZATION_HEADER_NAME = "Authorization";
    int ANDROID_LAST_VERSION = 234;
}
