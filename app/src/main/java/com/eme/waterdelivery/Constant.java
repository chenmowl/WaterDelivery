package com.eme.waterdelivery;

/**
 * Created by dijiaoliang on 17/3/20.
 */

public class Constant {

    //空字符串
    public static final String STR_EMPTY = "";

    public static final String STR_ZERO = "0";

    public static final String STR_ONE = "1";

    public static final String STR_TWO = "2";

    //数字0,1
    public static final int MINUS = -1;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int TWENTY = 20;

    //一页显示的条目个数
    public static final String PAGE_SIZE = "8";


    /***************************  业务常量数据  *****************************/

    public static final String LOGIN_INFO = "LOGIN_INFO";

    public static final String ORDER_ID = "ORDER_ID";

    public static final String TRAFFIC_NO = "TRAFFIC_NO";

    public static final String ORDER_SIGN_BEAN = "ORDER_SIGN_BEAN";

    public static final String ORDER_BUCKETS = "ORDER_BUCKETS";

    public static final int REQUEST_CODE = 1001;//跳转配送中商品详情的请求码

    public static final int REQUEST_CODE_COLLECT_WATER = 2001;//收水确认请求码


    //现金、微信 、欠款
    public static final String PAY_TYPE_MONEY = "1";
    public static final String PAY_TYPE_WEIXIN = "2";
    public static final String PAY_TYPE_DEBT = "3";

    //售票的支付类型  1、现金 2、微信 3白条
    public static final String PAY_MODE_MONEY = "1";
    public static final String PAY_MODE_WEIXIN = "2";
    public static final String PAY_MODE_DEBT = "3";

    //支付状态 1、已支付 2、待支付
    public static final String PAY_STATUS_YES = "1";
    public static final String PAY_STATUS_NO = "2";

    //水票类型 1 电子水票，2纸质水票
    public static final String TICKET_TYPE_ELECTRON = "1";
    public static final String TICKET_TYPE_PAPER = "2";

    //payMethod     1、货到付款 2、 在线支付
    public static final String PAY_METHOD_RECEIVE = "1";
    public static final String PAY_METHOD_ONLINE = "2";

    //历史订单的传入参数
    public static final String ORDER_FLAG = "ORDER_FLAG";
    public static final String ORDER_TODAY = "1";
    public static final String ORDER_MONTH = "2";
    public static final String ORDER_ALL = "3";

    //待接单和配送中的标记
    public static final int ORDER_DELAY = 1001;
    public static final int ORDER_FIXED = 1002;
    public static final int ORDER_SEND = 1003;


    //采购单状态 0待处理 1派送中 2完结
    public static final String APPLY_RECORD_STATUS_UNHANDLE = "0";
    public static final String APPLY_RECORD_STATUS_DELIVERY = "1";
    public static final String APPLY_RECORD_STATUS_COMPLETE = "2";

    //采购单确认收货的结果码
    public static final int REQUEST_CODE_PURCHASE_DETAIL = 2001;
    public static final int CONFIRM_PURCHASE_SUCCESS = 200;

    //运单状态 0、待 配车(新申请) 1、 已配车待发货 2、 运输中待收货 3、已收货待回执 4、货运回执(缴空桶)
    public static final int TRAFFIC_STATUS_0 = 0;
    public static final int TRAFFIC_STATUS_1 = 1;
    public static final int TRAFFIC_STATUS_2 = 2;
    public static final int TRAFFIC_STATUS_3 = 3;
    public static final int TRAFFIC_STATUS_4 = 4;

    //欠款原因 1、订单赊欠  2、 购票赊欠 3、固定送水赊欠 4、其他
    public static final int DEBT_REASON_1 = 1;
    public static final int DEBT_REASON_2 = 2;
    public static final int DEBT_REASON_3 = 3;
    public static final int DEBT_REASON_4 = 4;

    //缴款状态。0、待上缴 1、水站确认 2、已上缴 3、逾期拖欠 4、逾期上缴
    public static final int ASSESS_MONEY_0 = 0;
    public static final int ASSESS_MONEY_1 = 1;
    public static final int ASSESS_MONEY_2 = 2;
    public static final int ASSESS_MONEY_3 = 3;
    public static final int ASSESS_MONEY_4 = 4;


    /***************************  网络常量数据  *****************************/
    //数据请求成功同时业务逻辑正常
    public static final String CODE_COMPLETE = "10000";


    /***************************  刷新常量数据  *****************************/

    public static final int REFRESH_NORMAL = 1;//正常刷新
    public static final int REFRESH_UP_LOADMORE = 2;//上拉加载更多
    public static final int REFRESH_DOWN = 3;//下拉刷新

    /***************************  版本校验参数  *****************************/

    public static final String username = "admin";
    public static final String password = "123456";

}
