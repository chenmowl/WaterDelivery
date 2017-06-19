package com.eme.waterdelivery.model.net.api;

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

import java.util.List;

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
     * 分页拉取固定订单接口(getFixedOrdersByPage)
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getFixedOrdersByPage")
    Observable<Result<WaitingOrderVo>> getFixedOrders(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize);

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

    /**
     * 订单签收
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/signOrder")
    Observable<StatusResult> orderSign(@Field("orderId")String orderId,@Field("payType")String payType,@Field("payAmountGoods")String payAmountGoods);

    /**
     * 获取申请的一级数据
     * @return
     */
    @POST("/xbz-api/purchase/getFirstCategory")
    Observable<Result<ApplyOneLevelBo>> getApplyOneLevel();

    /**
     * 获取申请的二级数据
     * @param parentId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/purchase/getSecondCategory")
    Observable<Result<ApplyOneLevelBo>> getApplyTwoLevel(@Field("parentId")String parentId);

    /**
     * 拉取二级分类下商品接口
     * @param categoryId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/purchase/getGoodsBySecondCategory")
    Observable<Result<ApplyTwoLevelGoodBo>> getApplyTwoLevelGoods(@Field("categoryId")String categoryId);

    /**
     * 分页拉取历史订购单
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/purchase/geHistoryPurchaseOrdersByPage")
    Observable<Result<HistoryPurchaseVo>> getHistoryPurchaseOrders(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize);

    /**
     * 拉取订购单详情
     * @param trafficNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/purchase/getPurchaseOrderDetail")
    Observable<Result<ApplyDetailVo>> getPurchaseOrderDetail(@Field("trafficNo")String trafficNo);

    /**
     * 提交订购单
     * @param storeId
     * @param createMemo
     * @param purchaseGoods
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/purchase/submitPurchaseOrder")
    Observable<StatusResult> submitApplications(@Field("storeId")String storeId, @Field("createMemo")String createMemo, @Field("purchaseGoods")String purchaseGoods);

    /**
     * 订购单确认收货接口
     * @param trafficNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/purchase/confirmPurchaseOrder")
    Observable<StatusResult> confirmPurchaseOrder(@Field("trafficNo")String trafficNo);


    /**
     * 计算价格（calculationPayAmount）
     * @param orderId
     * @param payAmountGoods
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/calculationPayAmount")
    Observable<Result<CalculationPayAmountBo>> calculationPayAmount(@Field("orderId")String orderId, @Field("payAmountGoods")String payAmountGoods);

    /**
     * 拉取微信支付二维码（getQRCode）
     * @param storeId
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/getQRCode")
    Observable<Result<GetQRCodeBo>> getQRCode(@Field("storeId")String storeId);

    /**
     * 下拉选择水票接口（getTicketInfo）
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/ticket/getTicketInfo")
    Observable<Result<GetTicketInfoBo>> getTicketInfo(@Field("ticketsModel")String ticketsModel);

    /**
     * 根据电话检索地址信息（getAddressByPhone）
     * @param phoneNumber
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/ticket/getAddressByPhone")
    Observable<Result<GetAddressByPhoneBo>> getAddressByPhone(@Field("phoneNumber")String phoneNumber);

    /**
     * 获取购票活动信息（getActiveInfoByTicket）
     * @param ticketsModel
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/ticket/getActiveInfoByTicket")
    Observable<Result<GetActiveInfoByTicketBo>> getActiveInfoByTicket(@Field("ticketsModel")String ticketsModel, @Field("tickets")String tickets);

    /**
     * 售票提交（sellTicketSubmit）
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
    @FormUrlEncoded
    @POST("/xbz-api/ticket/sellTicketSubmit")
    Observable<StatusResult> sellTicketSubmit(@Field("storeId")String storeId, @Field("memberName")String memberName, @Field("memberPhone")String memberPhone,
                                                                 @Field("memberAddress")String memberAddress, @Field("invoiceTitle")String invoiceTitle, @Field("ticketsModel")String ticketsModel,
                                                                 @Field("payType")String payType, @Field("memberAdressId")String memberAdressId, @Field("tickets")String tickets);

    /**
     * 申请购票提交（applyTicketSubmit）
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
    @FormUrlEncoded
    @POST("/xbz-api/ticket/applyTicketSubmit")
    Observable<StatusResult> applyTicketSubmit(@Field("storeId")String storeId, @Field("memberName")String memberName, @Field("memberPhone")String memberPhone,
                                              @Field("memberAddress")String memberAddress, @Field("invoiceTitle")String invoiceTitle, @Field("ticketsModel")String ticketsModel,
                                              @Field("payType")String payType, @Field("payStatus")String payStatus, @Field("memberAdressId")String memberAdressId, @Field("tickets")String tickets);

    /**
     * 分页拉取售票记录（getSellTicketByPage）
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/ticket/getSellTicketByPage")
    Observable<Result<SaleTicketRecordBo>> getSellTicketByPage(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize);

    /**
     * 分页拉取购票记录（getApplyTicketByPage）
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/ticket/getApplyTicketByPage")
    Observable<Result<SaleTicketRecordBo>> getApplyTicketByPage(@Field("storeId")String storeId, @Field("pageNo")int pageNo, @Field("pageSize")String pageSize);

    /**
     * 分页拉取运单记录
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/dispatch/getTrafficList")
    Observable<AResult<TrafficVo>> getTrafficList(@Field("pageNo")int pageNo);

    /**
     * 获取运单详情
     * @param trafficNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/dispatch/getTrafficDetail")
    Observable<AResult<TrafficDetailVo>> getTrafficDetail(@Field("trafficNo")String trafficNo);

    /**
     * 运单确认
     * @param trafficNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/dispatch/confirmTraffic")
    Observable<AStatusResult> confirmTraffic(@Field("trafficNo")String trafficNo);

    /**
     * 分页拉追欠记录
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/dispatch/getCrefditRecords")
    Observable<AResult<ChaseOrderVo>> getCrefditRecords(@Field("pageNo")int pageNo);

    /**
     * 欠款完结接口
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/dispatch/confirmCrefdit")
    Observable<AStatusResult> confirmCrefdit(@Field("id")String id);

    /**
     * 拉取应缴金额
     * @return
     */
    @POST("/xbz-api/dispatch/cashStatements")
    Observable<AResult<AssessMoneyVo>> cashStatements();

    /**
     * 拉取应缴水票
     * @return
     */
    @POST("/xbz-api/dispatch/ticketsStatements")
    Observable<AResult<AssessTicketVo>> ticketsStatements();

    /**
     * 拉取历史缴帐记录
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/dispatch/cashStatementRecord")
    Observable<AResult<AssessMoneyRecordVo>> cashStatementRecord(@Field("pageNo")int pageNo);

    /**
     * 拉取历史缴票记录
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/dispatch/ticketsStatementRecord")
    Observable<AResult<AssessTicketRecordVo>> ticketsStatementRecord(@Field("pageNo")int pageNo);

    /**
     * 拉取应缴空桶
     * @return
     */
    @POST("/xbz-api/dispatch/backBarrel")
    Observable<AResult<List<BackBarrelBo>>> backBarrel();

    /**
     * 取消订单接口（cancelOrder）
     * @param orderId
     * @param nextSendTime
     * @return
     */
    @FormUrlEncoded
    @POST("/xbz-api/order/cancelOrder")
    Observable<StatusResult> cancelOrder(@Field("orderId")String orderId,@Field("nextSendTime")String nextSendTime);

}
