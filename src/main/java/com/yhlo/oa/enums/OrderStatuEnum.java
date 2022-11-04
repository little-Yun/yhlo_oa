package com.yhlo.oa.enums;

/**
 * 订单状态
 */
public enum OrderStatuEnum {


    /** 已取消 */

    ORDER_STATU_CANCEL {public String getName(){return "已取消";}},

    /** 保存 */

    ORDER_STATU_SUBMIT {public String getName(){return "待审批";}},

    /** 商务审核 */

    ORDER_STATU_BUSINESS {public String getName(){return "商务审核";}},

    /** 财务审核传 */

    ORDER_STATU_FINANCIAL {public String getName(){return "财务审核";}},

    /** 退回 */

    ORDER_STATU_RETURN {public String getName(){return "退回";}},




   /** ----------------------------------以下是返利审批状态----------------------------------- **/

    /** 保存 */

    REBATE_STATU_SUBMIT {public String getName(){return "保存";}},

    /** 待审核 */

    REBATE_STATU_WAITCONFIRM {public String getName(){return "待审核";}},

    /** 已审核 */

    REBATE_STATU_REVIEWED {public String getName(){return "已审核";}},

    /** 已审核 */

    REBATE_STATU_CANCEL {public String getName(){return "已取消";}},



    /** ----------------------------------以下是交货状态----------------------------------- **/

    /** 未交货 */
    DELIVERY_STATU_UNDELIVERED {public String getName(){return "未交货";}},

    /** 部分交货 */
    DELIVERY_STATU_PARTIAL {public String getName(){return "部分交货";}},

    /** 完全交货 */
    DELIVERY_STATU_COMPLETELY {public String getName(){return "完全交货";}},



    ;





    public abstract String getName();

}
