package com.eme.waterdelivery.model.net;


import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.AStatusResult;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.entity.ApplyDetailVo;
import com.eme.waterdelivery.model.bean.entity.ApplyOneLevelBo;
import com.eme.waterdelivery.model.bean.entity.ApplyTwoLevelGoodBo;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyRecordVo;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyVo;
import com.eme.waterdelivery.model.bean.entity.AssessTicketRecordVo;
import com.eme.waterdelivery.model.bean.entity.AssessTicketVo;
import com.eme.waterdelivery.model.bean.entity.BackBarrelBo;
import com.eme.waterdelivery.model.bean.entity.CalculationPayAmountBo;
import com.eme.waterdelivery.model.bean.entity.ChaseOrderVo;
import com.eme.waterdelivery.model.bean.entity.GetActiveInfoByTicketBo;
import com.eme.waterdelivery.model.bean.entity.GetAddressByPhoneBo;
import com.eme.waterdelivery.model.bean.entity.GetQRCodeBo;
import com.eme.waterdelivery.model.bean.entity.GetTicketInfoBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderVo;
import com.eme.waterdelivery.model.bean.entity.HistoryPurchaseVo;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.SaleTicketRecordBo;
import com.eme.waterdelivery.model.bean.entity.TrafficDetailVo;
import com.eme.waterdelivery.model.bean.entity.TrafficVo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderVo;
import com.eme.waterdelivery.model.net.api.WaterApi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;

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
     *
     * @return
     */
    public Observable<Result<LoginBo>> cookieLogin() {
        return waterApi.cookieLogin();
    }

    /**
     * 密码登录
     *
     * @param uid
     * @param password
     * @return
     */
    public Observable<Result<LoginBo>> pwdLogin(String uid, String password) {
        return waterApi.pwdLogin(uid, password);
    }

    /**
     * 分页拉取待接订单接口
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<WaitingOrderVo>> getWaitingOrders(String storeId, int pageNo, String pageSize) {
        return waterApi.getWaitingOrders(storeId, pageNo, pageSize);
    }

    /**
     * 分页拉取固定订单接口(getFixedOrdersByPage)
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<WaitingOrderVo>> getFixedOrders(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize){
         return waterApi.getFixedOrders(storeId, pageNo, pageSize);
    }

    /**
     * 分页拉取派送中的订单列表
     *
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
     *
     * @param storeId
     * @return
     */
    public Observable<Result<OrderSumBo>> getOrderSum(String storeId) {
        return waterApi.getOrderSum(storeId);
    }

    /**
     * 接单
     *
     * @param orderId
     * @return
     */
    public Observable<StatusResult> receiveOrder(String orderId) {
        return waterApi.receiveOrder(orderId);
    }

    /**
     * 分页拉取历史订单接口
     *
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
     *
     * @param storeId
     * @return
     */
    public Observable<Result<HistoryOrderSumBo>> getHistoryOrderSum(String storeId) {
        return waterApi.getHistoryOrderSum(storeId);
    }

    /**
     * 订单详情接口
     *
     * @param orderId
     * @return
     */
    public Observable<Result<OrderDetailBo>> getOrderDetail(String orderId) {
        return waterApi.getOrderDetail(orderId);
    }

    /**
     * 订单签收
     *
     * @param orderId
     * @return
     */
    public Observable<StatusResult> orderSign(String orderId,String payType,String payAmountGoods) {
        return waterApi.orderSign(orderId,payType,payAmountGoods);
    }

    /**
     * 获取申请的一级数据
     *
     * @return
     */
    public Observable<Result<ApplyOneLevelBo>> getApplyOneLevel() {
        return waterApi.getApplyOneLevel();
    }

    /**
     * 获取申请的二级数据
     *
     * @param parentId
     * @return
     */
    public Observable<Result<ApplyOneLevelBo>> getApplyTwoLevel(String parentId) {
        return waterApi.getApplyTwoLevel(parentId);
    }

    /**
     * 拉取二级分类下商品接口
     *
     * @param categoryId
     * @return
     */
    public Observable<Result<ApplyTwoLevelGoodBo>> getApplyTwoLevelGoods(String categoryId) {
        return waterApi.getApplyTwoLevelGoods(categoryId);
    }

    /**
     * 分页拉取历史订购单
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<HistoryPurchaseVo>> getHistoryPurchaseOrders(String storeId, int pageNo, String pageSize) {
        return waterApi.getHistoryPurchaseOrders(storeId, pageNo, pageSize);
    }

    /**
     * 拉取订购单详情
     *
     * @param trafficNo
     * @return
     */
    public Observable<Result<ApplyDetailVo>> getPurchaseOrderDetail(String trafficNo) {
        return waterApi.getPurchaseOrderDetail(trafficNo);
    }

    /**
     * 提交订购单
     *
     * @param storeId
     * @param createMemo
     * @param purchaseGoods
     * @return
     */
    public Observable<StatusResult> submitApplications(String storeId, String createMemo, String purchaseGoods) {
        return waterApi.submitApplications(storeId, createMemo, purchaseGoods);
    }

    /**
     * 订购单确认收货接口
     *
     * @param trafficNo
     * @return
     */
    public Observable<StatusResult> confirmPurchaseOrder(String trafficNo) {
        return waterApi.confirmPurchaseOrder(trafficNo);
    }


    /**
     * 计算价格（calculationPayAmount）
     *
     * @param orderId
     * @param payAmountGoods
     * @return
     */
    public Observable<Result<CalculationPayAmountBo>> calculationPayAmount(String orderId, String payAmountGoods) {
        return waterApi.calculationPayAmount(orderId, payAmountGoods);
    }

    /**
     * 拉取微信支付二维码（getQRCode）
     *
     * @param storeId
     * @return
     */
    public Observable<Result<GetQRCodeBo>> getQRCode(String storeId) {
        return waterApi.getQRCode(storeId);
    }

    /**
     * 下拉选择水票接口（getTicketInfo）
     *
     * @return
     */
    public Observable<Result<GetTicketInfoBo>> getTicketInfo(String ticketsModel) {
        return waterApi.getTicketInfo(ticketsModel);
    }

    /**
     * 根据电话检索地址信息（getAddressByPhone）
     *
     * @param phoneNumber
     * @return
     */
    public Observable<Result<GetAddressByPhoneBo>> getAddressByPhone(String phoneNumber) {
        return waterApi.getAddressByPhone(phoneNumber);
    }

    /**
     * 获取购票活动信息（getActiveInfoByTicket）
     *
     * @param ticketsModel
     * @return
     */
    public Observable<Result<GetActiveInfoByTicketBo>> getActiveInfoByTicket(String ticketsModel, String tickets) {
        return waterApi.getActiveInfoByTicket(ticketsModel, tickets);
    }

    /**
     * 售票提交（sellTicketSubmit）
     *
     * @param storeId
     * @param memberName
     * @param memberPhone
     * @param memberAddress
     * @param invoiceTitle
     * @param ticketsModel
     * @param payType
     * @param memberAdressId
     * @param tickets
     * @return
     */
    public Observable<StatusResult> sellTicketSubmit(String storeId, String memberName, String memberPhone,
                                                     String memberAddress, String invoiceTitle, String ticketsModel,
                                                     String payType, String memberAdressId, String tickets) {
        return waterApi.sellTicketSubmit(storeId, memberName, memberPhone, memberAddress, invoiceTitle, ticketsModel, payType, memberAdressId, tickets);
    }

    /**
     * 申请购票提交（applyTicketSubmit）
     *
     * @param storeId
     * @param memberName
     * @param memberPhone
     * @param memberAddress
     * @param invoiceTitle
     * @param ticketsModel
     * @param payType
     * @param payStatus
     * @param memberAdressId
     * @param tickets
     * @return
     */
    public Observable<StatusResult> applyTicketSubmit(String storeId, String memberName, String memberPhone, String memberAddress, String invoiceTitle,
                                                      String ticketsModel, String payType, String payStatus, String memberAdressId, String tickets) {
        return waterApi.applyTicketSubmit(storeId, memberName, memberPhone, memberAddress, invoiceTitle, ticketsModel, payType, payStatus, memberAdressId, tickets);
    }

    /**
     * 分页拉取售票记录（getSellTicketByPage）
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<SaleTicketRecordBo>> getSellTicketByPage(String storeId, int pageNo, String pageSize){
        return waterApi.getSellTicketByPage(storeId,pageNo,pageSize);
    }

    /**
     * 分页拉取购票记录（getApplyTicketByPage）
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Observable<Result<SaleTicketRecordBo>> getApplyTicketByPage(String storeId, int pageNo, String pageSize){
        return waterApi.getApplyTicketByPage(storeId,pageNo,pageSize);
    }

    /**
     * 分页拉取运单记录
     * @param pageNo
     * @return
     */
    public Observable<AResult<TrafficVo>> getTrafficList(int pageNo){
        return waterApi.getTrafficList(pageNo);
    }

    /**
     * 获取运单详情
     * @param trafficNo
     * @return
     */
    public Observable<AResult<TrafficDetailVo>> getTrafficDetail(String trafficNo){
        return waterApi.getTrafficDetail(trafficNo);
    }

    /**
     * 运单确认
     * @param trafficNo
     * @return
     */
    public Observable<AStatusResult> confirmTraffic(String trafficNo){
        return waterApi.confirmTraffic(trafficNo);
    }

    /**
     * 分页拉追欠记录
     * @param pageNo
     * @return
     */
    public Observable<AResult<ChaseOrderVo>> getCrefditRecords(int pageNo){
        return waterApi.getCrefditRecords(pageNo);
    }

    /**
     * 欠款完结接口
     * @param id
     * @return
     */
    public Observable<AStatusResult> confirmCrefdit(@Field("id")String id){
        return waterApi.confirmCrefdit(id);
    }

    /**
     * 拉取应缴金额
     * @return
     */
    public Observable<AResult<AssessMoneyVo>> cashStatements(){
        return waterApi.cashStatements();
    }

    /**
     * 拉取应缴水票
     * @return
     */
    public Observable<AResult<AssessTicketVo>> ticketsStatements(){
        return waterApi.ticketsStatements();
    }

    /**
     * 拉取历史缴帐记录
     * @param pageNo
     * @return
     */
    public Observable<AResult<AssessMoneyRecordVo>> cashStatementRecord(int pageNo){
        return waterApi.cashStatementRecord(pageNo);
    }

    /**
     * 拉取历史缴票记录
     * @param pageNo
     * @return
     */
    public Observable<AResult<AssessTicketRecordVo>> ticketsStatementRecord(int pageNo){
        return waterApi.ticketsStatementRecord(pageNo);
    }

    /**
     * 拉取应缴空桶
     * @return
     */
    public Observable<AResult<List<BackBarrelBo>>> backBarrel(){
        return waterApi.backBarrel();
    }

    /**
     * 取消订单接口（cancelOrder）
     * @param orderId
     * @param nextSendTime
     * @return
     */
    public Observable<StatusResult> cancelOrder(String orderId,String nextSendTime){
        return waterApi.cancelOrder(orderId,nextSendTime);
    }

}
