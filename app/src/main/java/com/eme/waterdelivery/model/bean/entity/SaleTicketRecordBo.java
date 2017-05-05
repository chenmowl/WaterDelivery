package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 售票记录的实体类
 *
 * Created by dijiaoliang on 17/5/5.
 */
public class SaleTicketRecordBo {


    /**
     * hasMore : true
     * list : [{"amount":380,"createTime":"2017-05-05 13:38:47","id":1041,"memberAddress":"nsidg","memberName":"qw","memberPhone":"15809365678","payType":2,"ticket":[{"model":2,"name":"冰川五加仑水票","number":19,"price":20}]},{"amount":288,"createTime":"2017-05-05 11:51:11","id":1040,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088888","payType":2,"ticket":[{"model":2,"name":"冰纯六加仑水票","number":12,"price":24}]},{"amount":200,"createTime":"2017-05-05 11:50:03","id":1039,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088888","payType":2,"ticket":[{"model":2,"name":"冰川五加仑水票","number":10,"price":20}]},{"amount":380,"createTime":"2017-05-04 14:00:29","id":1038,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088882","payType":2,"ticket":[{"model":2,"name":"冰川五加仑水票","number":19,"price":20}]},{"amount":380,"createTime":"2017-05-04 13:59:46","id":1037,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088882","payType":2,"ticket":[{"model":2,"name":"冰川五加仑水票","number":19,"price":20}]},{"amount":200,"createTime":"2017-05-04 10:01:50","id":1035,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088888","payType":2,"ticket":[{"model":2,"name":"冰川五加仑水票","number":10,"price":20}]},{"amount":600,"createTime":"2017-05-04 10:01:50","id":1036,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088888","payType":2,"ticket":[{"model":1,"name":"冰纯六加仑水票","number":12,"price":50}]},{"amount":200,"createTime":"2017-05-04 10:01:49","id":1033,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088888","payType":2,"ticket":[{"model":2,"name":"冰川五加仑水票","number":10,"price":20}]},{"amount":600,"createTime":"2017-05-04 10:01:49","id":1034,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088888","payType":2,"ticket":[{"model":1,"name":"冰纯六加仑水票","number":12,"price":50}]},{"amount":200,"createTime":"2017-05-04 10:01:47","id":1031,"memberAddress":"i am address","memberName":"nick","memberPhone":"18614088888","payType":2,"ticket":[{"model":2,"name":"冰川五加仑水票","number":10,"price":20}]}]
     */

    private boolean hasMore;
    private List<ListBean> list;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * amount : 380
         * createTime : 2017-05-05 13:38:47
         * id : 1041
         * memberAddress : nsidg
         * memberName : qw
         * memberPhone : 15809365678
         * payType : 2
         * ticket : [{"model":2,"name":"冰川五加仑水票","number":19,"price":20}]
         */

        private String amount;
        private String createTime;
        private String id;
        private String memberAddress;
        private String memberName;
        private String memberPhone;
        private String payType;
        private String invoiceTitle;
        private String payStatus;//支付状态
        private String status;//审核状态
        private List<TicketBean> ticket;

        public String getInvoiceTitle() {
            return invoiceTitle;
        }

        public void setInvoiceTitle(String invoiceTitle) {
            this.invoiceTitle = invoiceTitle;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }


        public String getMemberAddress() {
            return memberAddress;
        }

        public void setMemberAddress(String memberAddress) {
            this.memberAddress = memberAddress;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberPhone() {
            return memberPhone;
        }

        public void setMemberPhone(String memberPhone) {
            this.memberPhone = memberPhone;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public List<TicketBean> getTicket() {
            return ticket;
        }

        public void setTicket(List<TicketBean> ticket) {
            this.ticket = ticket;
        }

        public static class TicketBean {
            /**
             * model : 2
             * name : 冰川五加仑水票
             * number : 19
             * price : 20
             */

            private String model;
            private String name;
            private int number;
            private String price;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
