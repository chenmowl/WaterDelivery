package com.eme.waterdelivery.injector.component;


import com.eme.waterdelivery.annotation.ActivityScope;
import com.eme.waterdelivery.injector.module.ViewModule;
import com.eme.waterdelivery.ui.HomeActivity;
import com.eme.waterdelivery.ui.LoginActivity;
import com.eme.waterdelivery.ui.fragment.DelayFragment;

import dagger.Component;

/**
 * Created by dijiaoliang on 17/3/2.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = ViewModule.class)
public interface ViewComponent {

    void inject(LoginActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(DelayFragment delayFragment);

}
