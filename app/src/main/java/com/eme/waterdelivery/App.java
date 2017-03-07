package com.eme.waterdelivery;

import android.app.Application;

import com.eme.waterdelivery.injector.component.AppComponent;
import com.eme.waterdelivery.injector.component.DaggerAppComponent;
import com.eme.waterdelivery.injector.module.AppModule;
import com.eme.waterdelivery.injector.module.NetModule;

import me.yokeyword.fragmentation.Fragmentation;


/**
 *
 * 应用的技术:   Dagger2  MVP  RxJava  Retrofit
 *
 *
 * Created by dijiaoliang on 17/3/2.
 */
public class App extends Application {

    private static App app;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent= DaggerAppComponent.builder().appModule(new AppModule()).netModule(new NetModule()).build();
        Fragmentation.builder().stackViewMode(Fragmentation.BUBBLE).debug(true).install();
    }

    public static App getAppInstance() {
        return app;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
