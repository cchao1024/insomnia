package com.cchao.insomnia.api;


import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.model.javabean.RespBean;
import com.cchao.insomnia.model.javabean.RespListBean;
import com.cchao.insomnia.model.javabean.fall.FallImage;
import com.cchao.insomnia.model.javabean.fall.FallIndex;
import com.cchao.insomnia.model.javabean.fall.FallMusic;
import com.cchao.insomnia.model.javabean.global.AppLaunch;
import com.cchao.insomnia.model.javabean.post.PostListVO;
import com.cchao.insomnia.model.javabean.post.PostVO;
import com.cchao.insomnia.model.javabean.post.UploadImageBean;
import com.cchao.insomnia.model.javabean.user.UserBean;
import com.cchao.insomnia.model.javabean.user.WishItem;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * description
 * author  cchao
 * date  2017/2/24
 **/
public interface Apis {

    String aboutUs = Constants.Config.API_Host + "app/about_us";
    String sleep478 = "https://www.healthline.com/health/4-7-8-breathing";

    @GET("/app/getLaunch")
    Observable<RespBean<AppLaunch>> appLaunch();

    /**
     * 忘记密码 必选参数: email
     */
    @FormUrlEncoded
    @POST("?com=customer&t=findPwdByEmail")
    Observable<RespBean> resetPwdByEmail(@Field("email") String email);

    @Multipart
    @POST("/file/upload")
    Observable<RespBean<UploadImageBean>> uploadImage(@Query("type") String type, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("/user/{path}")
    Observable<RespBean<UserBean>> login(@Path("path") String path, @Field("email") String email
        , @Field("password") String pwd);

    @FormUrlEncoded
    @POST("/user/update")
    Observable<RespBean<UserBean>> editUserInfo(@FieldMap Map<String, String> map);

    @GET("/fall/index")
    Observable<RespBean<FallIndex>> getIndexData();

    @FormUrlEncoded
    @POST("/fall/image/list")
    Observable<RespListBean<FallImage>> getImageList(@Field("page") int page);

    @FormUrlEncoded
    @POST("/post/list")
    Observable<RespListBean<PostListVO>> getPostBoxList(@Field("page") int page);

    @FormUrlEncoded
    @POST("/post/detail")
    Observable<RespBean<PostVO>> getPostDetail(@Field("id") long id);

    @FormUrlEncoded
    @POST("/post/new")
    Observable<RespBean> addPost(@Field("content") String content, @Field("images") String images);

    @FormUrlEncoded
    @POST("/{type}/new")
    Observable<RespBean> addCommentOrReply(@Path("type") String type, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/fall/music/list")
    Observable<RespListBean<FallMusic>> getMusicList(@Field("page") int page);

    @FormUrlEncoded
    @POST("/fall/music/play")
    Observable<RespBean> play(@Field("id") String id);

    @FormUrlEncoded
    @POST("/app/feed_back")
    Observable<RespBean> feedBack(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/{path}/like")
    Observable<RespBean> like(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/wish/list")
    Observable<RespListBean<WishItem>> wishList(@Field("page") int page);

    @FormUrlEncoded
    @POST("/wish/add")
    Observable<RespBean> addWish(@Field("id") long id);
}
