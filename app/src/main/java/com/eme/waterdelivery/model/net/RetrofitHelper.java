package com.eme.waterdelivery.model.net;


import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.entity.ApplyDetailVo;
import com.eme.waterdelivery.model.bean.entity.ApplyOneLevelBo;
import com.eme.waterdelivery.model.bean.entity.ApplyTwoLevelGoodBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderVo;
import com.eme.waterdelivery.model.bean.entity.HistoryPurchaseVo;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderVo;
import com.eme.waterdelivery.model.net.api.WaterApi;

import io.reactivex.Observable;

/**
 * Created by dijiaoliang on 17/3/6.
 */
public class RetrofitHelper {

    private WaterApi waterApi;

    public RetrofitHelper(WaterApi waterApi) {
        this.waterApi = waterApi;
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

    /**
     * 订单签收
     * @param orderId
     * @return
     */
    public Observable<StatusResult> orderSign(String orderId) {
        return waterApi.orderSign(orderId);
    }

    /**
     * 获取申请的一级数据
     * @return
     */
    public Observable<Result<ApplyOneLevelBo>> getApplyOneLevel() {
        return waterApi.getApplyOneLevel();
    }

    /**
     * 获取申请的二级数据
     * @param parentId
     * @return
     */
    public Observable<Result<ApplyOneLevelBo>> getApplyTwoLevel(String parentId) {
        return waterApi.getApplyTwoLevel(parentId);
    }

    /**
     * 拉取二级分类下商品接口
     * @param categoryId
     * @return
     */
    public Observable<Result<ApplyTwoLevelGoodBo>> getApplyTwoLevelGoods(String categoryId) {
        return waterApi.getApplyTwoLevelGoods(categoryId);
    }

    /**
     * 分页拉取历史订购单
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<HistoryPurchaseVo>> getHistoryPurchaseOrders(String storeId,int pageNo,String pageSize) {
        return waterApi.getHistoryPurchaseOrders(storeId,pageNo,pageSize);
    }

    /**
     * 拉取订购单详情
     * @param trafficNo
     * @return
     */
    public Observable<Result<ApplyDetailVo>> getPurchaseOrderDetail(String trafficNo) {
        return waterApi.getPurchaseOrderDetail(trafficNo);
    }

    /**
     * 提交订购单
     * @param storeId
     * @param createMemo
     * @param purchaseGoods
     * @return
     */
    public Observable<StatusResult> submitApplications(String storeId,String createMemo,String purchaseGoods) {
        return waterApi.submitApplications(storeId,createMemo,purchaseGoods);
    }

    /**
     * 订购单确认收货接口
     * @param trafficNo
     * @return
     */
    public Observable<StatusResult> confirmPurchaseOrder(String trafficNo) {
        return waterApi.confirmPurchaseOrder(trafficNo);
    }

}
