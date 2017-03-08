package com.eme.waterdelivery.injector.module;


import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.injector.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dijiaoliang on 17/3/2.
 */
@Module
public class ViewModule {

    private BaseView view;

    public ViewModule(BaseView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BaseView getView() {
        return view;
    }
}
