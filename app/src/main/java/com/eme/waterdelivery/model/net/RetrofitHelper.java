package com.eme.waterdelivery.model.net;


import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.ZhihuDaily;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderVo;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderVo;
import com.eme.waterdelivery.model.net.api.WaterApi;
import com.eme.waterdelivery.model.net.api.ZhihuApi;

import io.reactivex.Observable;

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

    /**
     * cookie登录
     * @return
     */
    public Observable<Result<LoginBo>> cookieLogin() {
        return waterApi.cookieLogin();
    }

    /**
     * 密码登录
     * @param uid
     * @param password
     * @return
     */
    public Observable<Result<LoginBo>> pwdLogin(String uid, String password) {
        return waterApi.pwdLogin(uid,password);
    }

    /**
     * 分页拉取待接订单接口
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<WaitingOrderVo>> getWaitingOrders(String storeId, int pageNo, String pageSize) {
        return waterApi.getWaitingOrders(storeId, pageNo, pageSize);
    }

    /**
     * 分页拉取派送中的订单列表
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<WaitingOrderVo>> getDistributingOrders(String storeId, int pageNo, String pageSize) {
        return waterApi.getDistributingOrders(storeId, pageNo, pageSize);
    }

    /**
     * 获取订单（待接单，派送中订单）数量接口
     * @param storeId
     * @return
     */
    public Observable<Result<OrderSumBo>> getOrderSum(String storeId) {
        return waterApi.getOrderSum(storeId);
    }

    /**
     * 接单
     * @param orderId
     * @return
     */
    public Observable<StatusResult> receiveOrder(String orderId) {
        return waterApi.receiveOrder(orderId);
    }

    /**
     * 分页拉取历史订单接口
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @param timeFlag
     * @return
     */
    public Observable<Result<HistoryOrderVo>> getHistoryOrders(String storeId, int pageNo, String pageSize, String timeFlag) {
        return waterApi.getHistoryOrders(storeId, pageNo, pageSize, timeFlag);
    }

    /**
     * 获取历史订单（日，月，总）数量接口
     * @param storeId
     * @return
     */
    public Observable<Result<HistoryOrderSumBo>> getHistoryOrderSum(String storeId) {
        return waterApi.getHistoryOrderSum(storeId);
    }

    /**
     * 订单详情接口
     * @param orderId
     * @return
     */
    public Observable<Result<OrderDetailBo>> getOrderDetail(String orderId) {
        return waterApi.getOrderDetail(orderId);
    }
}
