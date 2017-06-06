package com.eme.waterdelivery.injector.component;


import com.eme.waterdelivery.injector.ActivityScope;
import com.eme.waterdelivery.injector.module.ViewModule;
import com.eme.waterdelivery.ui.ApplyDetailActivity;
import com.eme.waterdelivery.ui.ApplyTicketActivity;
import com.eme.waterdelivery.ui.ApplyTicketRecordActivity;
import com.eme.waterdelivery.ui.ApplyVacationActivity;
import com.eme.waterdelivery.ui.AssessmentActivity;
import com.eme.waterdelivery.ui.AssessmentHistoryActivity;
import com.eme.waterdelivery.ui.ChaseOrderActivity;
import com.eme.waterdelivery.ui.CollectBucketActivity;
import com.eme.waterdelivery.ui.CollectWaterActivity;
import com.eme.waterdelivery.ui.CompleteActivity;
import com.eme.waterdelivery.ui.CompleteDetailActivity;
import com.eme.waterdelivery.ui.FixedDetailActivity;
import com.eme.waterdelivery.ui.GoodsSignActivity;
import com.eme.waterdelivery.ui.HomeActivity;
import com.eme.waterdelivery.ui.HomeCollectWaterActivity;
import com.eme.waterdelivery.ui.LaunchActivity;
import com.eme.waterdelivery.ui.LoginActivity;
import com.eme.waterdelivery.ui.MyApplyActivity;
import com.eme.waterdelivery.ui.SaleTicketActivity;
import com.eme.waterdelivery.ui.SaleTicketRecordActivity;
import com.eme.waterdelivery.ui.SendingDetailActivity;
import com.eme.waterdelivery.ui.SendingInstantActivity;
import com.eme.waterdelivery.ui.WaterSignActivity;
import com.eme.waterdelivery.ui.fragment.AllOrderFragment;
import com.eme.waterdelivery.ui.fragment.ApplyFragment;
import com.eme.waterdelivery.ui.fragment.ApplyRecordFragment;
import com.eme.waterdelivery.ui.fragment.AssessMoneyFragment;
import com.eme.waterdelivery.ui.fragment.AssessTicketFragment;
import com.eme.waterdelivery.ui.fragment.CurrentDayFragment;
import com.eme.waterdelivery.ui.fragment.DelayFragment;
import com.eme.waterdelivery.ui.fragment.FixedFragment;
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

    void inject(ApplyVacationActivity applyVacationActivity);

    void inject(CollectWaterActivity collectWaterActivity);

    void inject(HomeCollectWaterActivity homeCollectWaterActivity);

    void inject(CollectBucketActivity collectBucketActivity);

    void inject(AssessmentActivity assessmentActivity);

    void inject(AssessmentHistoryActivity assessmentHistoryActivity);

    void inject(ChaseOrderActivity chaseOrderActivity);

    void inject(SaleTicketActivity saleTicketActivity);

    void inject(ApplyTicketActivity applyTicketActivity);

    void inject(SaleTicketRecordActivity saleTicketRecordActivity);

    void inject(ApplyTicketRecordActivity applyTicketRecordActivity);

    void inject(SendingDetailActivity orderDetailActivity);

    void inject(SendingInstantActivity sendingInstantActivity);

    void inject(FixedDetailActivity fixedDetailActivity);

    void inject(WaterSignActivity waterSignActivity);

    void inject(GoodsSignActivity goodsSignActivity);

    void inject(CompleteActivity completeActivity);

    void inject(CompleteDetailActivity completeDetailActivity);

    void inject(MyApplyActivity myApplyActivity);

    void inject(ApplyDetailActivity applyDetailActivity);

    void inject(DelayFragment delayFragment);

    void inject(FixedFragment fixedFragment);

    void inject(SendingFragment sendingFragment);

    void inject(CurrentDayFragment currentDayFragment);

    void inject(MonthOrderFragment monthOrderFragment);

    void inject(AllOrderFragment allOrderFragment);

    void inject(ApplyFragment applyFragment);

    void inject(ApplyRecordFragment applyRecordFragment);

    void inject(AssessMoneyFragment assessMoneyFragment);

    void inject(AssessTicketFragment assessTicketFragment);

}
