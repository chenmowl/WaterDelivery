package com.eme.waterdelivery.injector.module;


import com.eme.waterdelivery.App;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.net.api.WaterApi;
import com.eme.waterdelivery.model.net.api.ZhihuApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dijiaoliang on 17/3/2.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    App provideApp() {
        return App.getAppInstance();
    }

    @Singleton
    @Provides
    RetrofitHelper provideRetrofitHelper(ZhihuApi zhihuApi, WaterApi waterApi) {
        return new RetrofitHelper(zhihuApi,waterApi);
    }
}
