package com.eme.waterdelivery.model.net.api;

import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderVo;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderVo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 雪百真api
 *
 * Created by dijiaoliang on 17/3/20.
 */
public interface WaterApi {

    @POST("/xbz-api/employ/cookieLogin")
    Observable<Result<LoginBo>> cookieLogin();

    @FormUrlEncoded
    @POST("/xbz-api/employ/passwordLogin")
    Observable<Result<LoginBo>> pwdLogin(@Field("uid")String uid, @Field("password")String password);

    /**
     * 待接单订单列表
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getWaitingOrdersByPage")
    Observable<Result<WaitingOrderVo>> getWaitingOrders(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize);

    /**
     * 配送中订单列表
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getDistributingOrdersByPage")
    Observable<Result<WaitingOrderVo>> getDistributingOrders(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize);

    /**
     * 获取待接单和配送中的订单个数
     * @param storeId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getOrderSum")
    Observable<Result<OrderSumBo>> getOrderSum(@Field("storeId")String storeId);

    /**
     * 接单
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/takeOrder")
    Observable<StatusResult> receiveOrder(@Field("orderId")String orderId);

    /**
     * 分页拉取历史订单接口
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @param timeFlag
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getHistoryOrdersByPage")
    Observable<Result<HistoryOrderVo>> getHistoryOrders(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize, @Field("timeFlag")String timeFlag);

    /**
     * 获取历史订单（日，月，总）数量接口
     * @param storeId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getHistoryOrderSum")
    Observable<Result<HistoryOrderSumBo>> getHistoryOrderSum(@Field("storeId")String storeId);

    /**
     * 订单详情接口
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getOrderDetail")
    Observable<Result<OrderDetailBo>> getOrderDetail(@Field("orderId")String orderId);

}
