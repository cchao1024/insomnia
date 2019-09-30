package com.cchao.insomnia.api;

import com.cchao.insomnia.global.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit 网络请求helper
 * Created by cchao on 2017.
 */
public class RetrofitHelper {

    private Apis mApiService = null;
    private static volatile RetrofitHelper mRetrofit;

    private RetrofitHelper() {
    }

    static {
        getDefault();
    }

    /**
     * 获取默认正式环境 retrofit
     */
    public static RetrofitHelper getDefault() {
        synchronized (RetrofitHelper.class) {
            if (mRetrofit == null) {
                mRetrofit = new RetrofitHelper();
                mRetrofit.initApiService(Constants.Config.API_Host, Apis.class);
            }
        }
        return mRetrofit;
    }

    private void initApiService(String baseUrl, Class clz) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(HttpClientManager.getProdOkHttpClient())
            .validateEagerly(true)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

        mApiService = (Apis) retrofit.create(clz);
    }

    public static Apis getApis() {
        if (getDefault().mApiService != null) {
            return getDefault().mApiService;
        } else {
            throw new IllegalArgumentException("mApiService is null");
        }
    }
}
