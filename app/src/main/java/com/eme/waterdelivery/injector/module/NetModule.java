package com.eme.waterdelivery.injector.module;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.injector.WaterQual;
import com.eme.waterdelivery.injector.ZhihuQual;
import com.eme.waterdelivery.model.net.ApiConfig;
import com.eme.waterdelivery.model.net.api.WaterApi;
import com.eme.waterdelivery.model.net.api.ZhihuApi;
import com.eme.waterdelivery.model.net.converter.FastJsonConverterFactory;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by dijiaoliang on 17/3/6.
 */
@Module
public class NetModule {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkhttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @ZhihuQual
    @Singleton
    @Provides
    Retrofit provideZhihuRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client, ApiConfig.API_HOST);
    }

    @WaterQual
    @Singleton
    @Provides
    Retrofit provideWaterRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client, ApiConfig.WATER_HOST);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (ApiConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(new StethoInterceptor());
        }
        File cacheFile = new File(ApiConfig.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected(App.getAppInstance())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isConnected(App.getAppInstance())) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        Interceptor apikey = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader(SpConstant.HEAD_COOKIE_UID, SPBase.getContent(App.getAppInstance(),SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_UID))
                        .addHeader(SpConstant.HEAD_COOKIE_SIG,SPBase.getContent(App.getAppInstance(),SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_SIG))
                        .build();
                return chain.proceed(request);
            }
        };
//        设置统一的请求头部参数
        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    /**
     * 区分不同主机、不同网络配置，例如微信支付与主项目要分开部署
     * @param builder
     * @param client
     * @param host
     * @return
     */
    Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String host) {
        return builder.baseUrl(host)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    ZhihuApi provideZhihuService(@ZhihuQual Retrofit retrofit){
        return retrofit.create(ZhihuApi.class);
    }

    @Singleton
    @Provides
    WaterApi provideWaterService(@WaterQual Retrofit retrofit){
        return retrofit.create(WaterApi.class);
    }


}
