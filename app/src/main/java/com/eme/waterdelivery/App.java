package com.eme.waterdelivery;

import android.app.Application;

import com.eme.waterdelivery.injector.component.AppComponent;
import com.eme.waterdelivery.injector.component.DaggerAppComponent;
import com.eme.waterdelivery.injector.module.AppModule;
import com.eme.waterdelivery.injector.module.NetModule;
import com.squareup.leakcanary.LeakCanary;

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
//        初始化cookiesig cookieuid
        appComponent= DaggerAppComponent.builder().appModule(new AppModule()).netModule(new NetModule()).build();
        Fragmentation.builder().stackViewMode(Fragmentation.BUBBLE).debug(true).install();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static App getAppInstance() {
        return app;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
