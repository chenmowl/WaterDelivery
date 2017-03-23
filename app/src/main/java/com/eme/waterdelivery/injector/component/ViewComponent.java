package com.eme.waterdelivery.injector.component;


import com.eme.waterdelivery.injector.ActivityScope;
import com.eme.waterdelivery.injector.module.ViewModule;
import com.eme.waterdelivery.ui.ApplyDetailActivity;
import com.eme.waterdelivery.ui.CompleteActivity;
import com.eme.waterdelivery.ui.CompleteDetailActivity;
import com.eme.waterdelivery.ui.HomeActivity;
import com.eme.waterdelivery.ui.LaunchActivity;
import com.eme.waterdelivery.ui.LoginActivity;
import com.eme.waterdelivery.ui.MyApplyActivity;
import com.eme.waterdelivery.ui.SendingDetailActivity;
import com.eme.waterdelivery.ui.fragment.AllOrderFragment;
import com.eme.waterdelivery.ui.fragment.ApplyFragment;
import com.eme.waterdelivery.ui.fragment.ApplyRecordFragment;
import com.eme.waterdelivery.ui.fragment.CurrentDayFragment;
import com.eme.waterdelivery.ui.fragment.DelayFragment;
import com.eme.waterdelivery.ui.fragment.MonthOrderFragment;
import com.eme.waterdelivery.ui.fragment.SendingFragment;

import dagger.Component;

/**
 * Created by dijiaoliang on 17/3/2.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = ViewModule.class)
public interface ViewComponent {

    void inject(LaunchActivity launchActivity);

    void inject(LoginActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(SendingDetailActivity orderDetailActivity);

    void inject(CompleteActivity completeActivity);

    void inject(CompleteDetailActivity completeDetailActivity);

    void inject(MyApplyActivity myApplyActivity);

    void inject(ApplyDetailActivity applyDetailActivity);

    void inject(DelayFragment delayFragment);

    void inject(SendingFragment sendingFragment);

    void inject(CurrentDayFragment currentDayFragment);

    void inject(MonthOrderFragment monthOrderFragment);

    void inject(AllOrderFragment allOrderFragment);

    void inject(ApplyFragment applyFragment);

    void inject(ApplyRecordFragment applyRecordFragment);

}
