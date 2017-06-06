package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.GetActiveInfoByTicketBo;
import com.eme.waterdelivery.model.bean.entity.GetAddressByPhoneBo;
import com.eme.waterdelivery.model.bean.entity.GetTicketInfoBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface SaleTicketContract {

    interface View extends BaseView {

        void showProgress(boolean isShow);

        void showRequestAddressResult(boolean isSuccess, List<GetAddressByPhoneBo.ListBean> list,String message);

        void showRequestTicketResult(boolean isSuccess, List<GetTicketInfoBo.ListBean> list, String message);

        void loadWXImage(String imageUrl);

        void getImageFailure(String message);

        void showRequestTicketActivityInfoResult(boolean isSuccess, List<GetActiveInfoByTicketBo.ListBean> list, String message);

        void showTicketSubmitResult(boolean isSuccess, String message);
    }

    interface Presenter extends BasePresenter {
        void requestAddress(String phoneNumber);

        void requestTicketList(String ticketsModel);

        void getTicketActivityInfo(String ticketsModel,String tickets);

        void getWXImage();

        void sellTicketSubmit(String memberName,String memberPhone,String memberAddress,String invoiceTitle,String ticketsModel,String payType,String memberAdressId,String tickets);
    }

}
