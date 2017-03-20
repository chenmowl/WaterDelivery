package com.eme.waterdelivery.model.net.api;

import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.LoginVo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 雪百真api
 *
 * Created by dijiaoliang on 17/3/20.
 */
public interface WaterApi {

    @FormUrlEncoded
    @POST("/xbz-api/employ/passwordLogin")
    Observable<Result<LoginVo>> pwdLogin(@Field("uid")String uid, @Field("password")String password);

}
