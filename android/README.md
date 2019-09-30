# Insomnia-android
Insomnia-android 是全栈项目 **Insomnia** 的 Android 端代码。
项目深度依赖 笔者另一开源库 [simpleLib](https://github.com/cchao1024/simpleLib) 开发

基于 

* [data-binding](https://developer.android.com/topic/libraries/data-binding)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [glide](https://github.com/bumptech/glide)
* [okHttp](https://github.com/square/okhttp)
* [Gson](https://github.com/google/gson)   等主流框架。

## 其他关联项目
 * [insomnia-server 基于SpringBoot的服务端代码](https://github.com/cchao1024/insomnia-server)
 * [insomnia-android Android 客户端](https://github.com/cchao1024/insomnia-android)
 * [simpleLib Android 基础类库](https://github.com/cchao1024/insomnia-server)


# 主体功能

```
Insomnia-server
├── 用户相关
|   ├── 游客邮箱绑定
|   ├── 用户登录
|   └── 信息查看/修改
|
├── 催眠图片
|   ├── 图片列表
|   └── 图片查看、点赞、下载 
|
├── 音乐相关
|   ├── 音乐列表
|   └── 音乐播放、点赞、下载 
|
└── 说说
    ├── 说说图片上传  
    ├── 发布说说
    ├─- 评论/点赞说说
    └─- 回复/点赞他人评论及回复
```

# 页面截图
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/audio.jpg)
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/1.jpg)
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/2.jpg)
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/33.jpg) 
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/44.jpg) 
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/66.jpg)
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/55.jpg) 

# 项目结构划分

```java

└── insomnia
    ├── api
    │   ├── Apis.java
    │   ├── HttpClientManager.java
    │   └── RetrofitHelper.java
    ├── global
    │   ├── App.java
    │   ├── Constants.java
    ├── manager
    │   ├── MusicPlayer.java
    │   └── UserManager.java
    ├── model
    │   └── javabean
    │       ├── RespBean.java
    │       ├── RespListBean.java
    │       ├── fall
    │       ├── home
    │       ├── post
    │       └── user
    ├── ui
    │   ├── BindingAdapters.java
    │   ├── account
    │   │   ├── EditUserInfoActivity.java
    │   │   ├── LogInActivity.java
    │   │   ├── UserInfoActivity.java
    │   │   └── WishListActivity.java
    │   ├── fall
    │   │   ├── FallFragment.java
    │   │   ├── FallImageActivity.java
    │   │   └── FallMusicListActivity.java
    │   ├── global
    │   │   ├── BaseListActivity.java
    │   │   └── ImageShowActivity.java
    │   ├── main
    │   │   ├── MainActivity.java
    │   │   └── SplashActivity.java
    │   ├── music
    │   │   ├── MusicListActivity.java
    │   │   └── PLayListFragment.java
    │   └── post
    │       ├── AddPostActivity.java
    │       ├── PostBoxFragment.java
    │       ├── PostDetailActivity.java
    │       └── convert
    ├── util
    │   ├── AnimHelper.java
    │   ├── ImageHelper.java
    └── view
        ├── Dialogs.java
        ├── adapter
        │   ├── DataBindMultiQuickAdapter.java
        │   ├── DataBindQuickAdapter.java
        │   └── PageAdapter.java
        ├── imagewatcher
        │   ├── ImageWatcher.java
        │   └── ViewState.java
        └── wish
            ├── BindingAdapter.java
            └── WishView.java

```

# Download
下载二维码
![](https://raw.githubusercontent.com/cchao1024/insomnia-android/develop/document/fir.jpg)