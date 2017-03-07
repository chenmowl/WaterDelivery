package com.eme.waterdelivery.injector.component;


import com.eme.waterdelivery.App;
import com.eme.waterdelivery.injector.module.AppModule;
import com.eme.waterdelivery.injector.module.NetModule;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dijiaoliang on 17/3/6.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {

    RetrofitHelper getRetrofitHelper();

    App provideApp();

}
