package com.eme.waterdelivery.model.net;


import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.ZhihuDaily;
import com.eme.waterdelivery.model.bean.entity.LoginVo;
import com.eme.waterdelivery.model.net.api.WaterApi;
import com.eme.waterdelivery.model.net.api.ZhihuApi;

import rx.Observable;

/**
 * Created by dijiaoliang on 17/3/6.
 */
public class RetrofitHelper {

    private ZhihuApi zhihuApi;

    private WaterApi waterApi;

    public RetrofitHelper(ZhihuApi zhihuApi, WaterApi waterApi) {
        this.zhihuApi = zhihuApi;
        this.waterApi = waterApi;
    }

    public Observable<ZhihuDaily> getLastDaily() {
        return zhihuApi.getLastDaily();
    }

    public Observable<Result<LoginVo>> pwdLogin(String uid, String password) {
        return waterApi.pwdLogin(uid,password);
    }

}
