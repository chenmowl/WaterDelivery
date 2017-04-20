package com.eme.waterdelivery;

/**
 * Created by dijiaoliang on 17/3/20.
 */

public class Constant {

    //空字符串
    public static final String STR_EMPTY="";

    //数字0,1
    public static final int ZERO=0;
    public static final int ONE=1;
    public static final int TWO=2;

    //一页显示的条目个数
    public static final String PAGE_SIZE="8";


    /***************************  业务常量数据  *****************************/

    public static final String LOGIN_INFO="LOGIN_INFO";

    public static final String ORDER_ID="ORDER_ID";

    public static final String TRAFFIC_NO="TRAFFIC_NO";

    public static final int REQUEST_CODE=1001;//跳转配送中商品详情的请求码


    //现金、微信
    public static final String PAY_TYPE_MONEY="1";
    public static final String PAY_TYPE_WEIXIN="2";

    //历史订单的传入参数
    public static final String ORDER_FLAG="ORDER_FLAG";
    public static final String ORDER_TODAY="1";
    public static final String ORDER_MONTH="2";
    public static final String ORDER_ALL="3";

    //待接单和配送中的标记
    public static final int ORDER_DELAY=1001;
    public static final int ORDER_FIXED=1002;
    public static final int ORDER_SEND=1003;


    //采购单状态 0待处理 1派送中 2完结
    public static final String APPLY_RECORD_STATUS_UNHANDLE="0";
    public static final String APPLY_RECORD_STATUS_DELIVERY="1";
    public static final String APPLY_RECORD_STATUS_COMPLETE="2";

    //采购单确认收货的结果码
    public static final int REQUEST_CODE_PURCHASE_DETAIL=2001;
    public static final int CONFIRM_PURCHASE_SUCCESS=200;

    /***************************  网络常量数据  *****************************/
    //数据请求成功同时业务逻辑正常
    public static final String CODE_COMPLETE="10000";


    /***************************  刷新常量数据  *****************************/

    public static final int REFRESH_NORMAL=1;//正常刷新
    public static final int REFRESH_UP_LOADMORE=2;//上拉加载更多
    public static final int REFRESH_DOWN=3;//下拉刷新

    /***************************  版本校验参数  *****************************/

    public static final String username="admin";
    public static final String password="123456";

}
