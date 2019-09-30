package com.cchao.insomnia.api;

import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.simplelib.core.AndroidHelper;
import com.cchao.simplelib.http.OkHttpHelper;
import com.cchao.simplelib.util.LanguageUtil;
import com.cchao.simplelib.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author cchao
 * @version 8/10/18.
 */
public class HttpClientManager {
    public static final String AUTHORIZATION = "Authorization";

    public static final Map<String, String> Req_Params = new HashMap<>();

    static {
        Req_Params.put(Constants.Api_Appand.Device_No, AndroidHelper.getDeviceNum());
        Req_Params.put(Constants.Api_Appand.App_Build, Constants.Config.API_BUILD);
        Req_Params.put(Constants.Api_Appand.Lang, LanguageUtil.Cur_Language);
        Req_Params.put(Constants.Api_Appand.Page_Size, String.valueOf(Constants.Config.PAGE_SIZE));
    }

    public static OkHttpClient getProdOkHttpClient() {
        return OkHttpHelper.getClient();
    }

    public static OkHttpClient getWrapClient() {
        return OkHttpHelper.getDefault().newBuilder()
            .addNetworkInterceptor(chain -> {
                Request request = chain.request();
                String originUrl = chain.request().url().url().toString();

                // 加入通用的请求参数
                originUrl = UrlUtil.buildUrl(originUrl, Req_Params);
                // 添加 header 授权的token
                request = request.newBuilder().url(originUrl)
                    .addHeader(AUTHORIZATION, UserManager.getToken())
                    .build();
                return chain.proceed(request);
            }).build();
    }
}
