package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: GeneralOrderApprovalVO
 * @Description:
 * @date 2022/10/1314:54
 */

@Data
@ApiModel("订单审批列表")
public class GeneralOrderApprovalVO {
    @ApiModelProperty(value = "序号")
    private String xuh;
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "订单类型")
    private String orderType;
    @ApiModelProperty(value = "订单参考")
    private String orderReference;
    @ApiModelProperty(value = "SAP销售订单号")
    private String sapSalesOrderNo;
    @ApiModelProperty(value = "SAP采购订单号")
    private String sapPurchaseOrderNo;
    @ApiModelProperty(value = "DDS销售订单号")
    private String ddsSalesOrderNo;
    @ApiModelProperty(value = "销售组织")
    private String salesOrg;
    @ApiModelProperty(value = "产品组")
    private String productGroup;
    @ApiModelProperty(value = "分销渠道")
    private String distributionChannel;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "客户代码")
    private String customerCode;
    @ApiModelProperty(value = "送达方名称")
    private String songdfmc;
    @ApiModelProperty(value = "送达方代码")
    private String songdfdm;
    @ApiModelProperty(value = "销售部门")
    private String saleDept;
    @ApiModelProperty(value = "销售小组")
    private String saleGroup;
    @ApiModelProperty(value = "付款条件")
    private String termOfPayment;
    @ApiModelProperty(value = "订单原因")
    private String orderReason;
    @ApiModelProperty(value = "终端客户")
    private String endCustomer;
    @ApiModelProperty(value = "业务员")
    private String salesman;
    @ApiModelProperty(value = "制单人")
    private String preparedBy;
    @ApiModelProperty(value = "凭证日期")
    private String documentDate;
    @ApiModelProperty(value = "总金额")
    private Float totalAmount;
    @ApiModelProperty(value = "未税金额")
    private Float amountNotTaxed;
    @ApiModelProperty(value = "装运点")
    private String shippingPoint;
    @ApiModelProperty(value = "装运条件")
    private String shippingConditions;
    @ApiModelProperty(value = "组合交货")
    private String combinationDelivery;
    @ApiModelProperty(value = "POD")
    private String pod;
    @ApiModelProperty(value = "审核状态")
    private String approvalStatus;
    @ApiModelProperty(value = "交货状态")
    private String deliveryStatus;
    @ApiModelProperty(value = "唛头/订单摘要")
    private String orderSummary;
    @ApiModelProperty(value = "发货信息备注")
    private String shippingRemarks;
    @ApiModelProperty(value = "多角贸易代码")
    private String multiAngleTradeCode;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "修改时间")
    private String updateTime;
    @ApiModelProperty(value = "修改人")
    private String updateBy;


    @ApiModelProperty(value = "明细主键id")
    private int id_detail;
    @ApiModelProperty(value = "行项目")
    private String lineItem;
    @ApiModelProperty(value = "助记码")
    private String mnemonicCode;
    @ApiModelProperty(value = "物料号")
    private String materialNo;
    @ApiModelProperty(value = "物料描述")
    private String materialDesc;
    @ApiModelProperty(value = "规格型号")
    private String specification;
    @ApiModelProperty(value = "订单数量")
    private Float orderQty;
    @ApiModelProperty(value = "已交货数量（基本单位）")
    private Float qtyDelivered;
    @ApiModelProperty(value = "未清数量（基本单位）")
    private Float openQty;
    @ApiModelProperty(value = "订单单位")
    private String orderUnit;
    @ApiModelProperty(value = "基本单位")
    private String basicUnit;
    @ApiModelProperty(value = "工厂")
    private String factory;
    @ApiModelProperty(value = "库存地点")
    private String inventoryLocation;
    @ApiModelProperty(value = "库存地点代码")
    private String inventoryLocationCode;
    @ApiModelProperty(value = "定价日期")
    private String pricingDate;
    @ApiModelProperty(value = "标准价")
    private Float standardPrice;
    @ApiModelProperty(value = "手工价")
    private Float manualPrice;
    @ApiModelProperty(value = "订单基本单位数量")
    private Float orderBuomQty;
    @ApiModelProperty(value = "折扣率")
    private Float discountRate;
    @ApiModelProperty(value = "返利金额")
    private Float rebateAmount;
    @ApiModelProperty(value = "折前金额")
    private Float amountBeforeDiscount;
    @ApiModelProperty(value = "折后金额")
    private Float discountedAmount;
    @ApiModelProperty(value = "存储温度")
    private String storageTemperature;
    @ApiModelProperty(value = "税率")
    private String taxRate;
    @ApiModelProperty(value = "货币")
    private String currency;
    @ApiModelProperty(value = "是否批次/序列号管理")
    private String batch;
    @ApiModelProperty(value = "生产许可/备案凭证")
    private String productionLicense;
    @ApiModelProperty(value = "生产厂家全称")
    private String fullNameOfManufacturer;
    @ApiModelProperty(value = "批准文号")
    private String approvalNo;
    @ApiModelProperty(value = "客户物料")
    private String customerMaterial;
    @ApiModelProperty(value = "客户物料描述")
    private String customerMaterialDesc;
    @ApiModelProperty(value = "省招标编码")
    private String provincialBiddingCode;
    @ApiModelProperty(value = "市招标编码")
    private String cityBiddingCode;
    @ApiModelProperty(value = "预留编码")
    private String reservedCode;
    @ApiModelProperty(value = "品牌")
    private String brand;
    @ApiModelProperty(value = "物料组")
    private String materialGroup;
    @ApiModelProperty(value = "经营许可证/执业许可证")
    private String businessLicense;
    @ApiModelProperty(value = "生产备案凭证")
    private String productionRecordCertificate;
    @ApiModelProperty(value = "是否认领")
    private String claim;
    @ApiModelProperty(value = "上一次单价")
    private Float lastUnitPrice;
    @ApiModelProperty(value = "多角贸易类型")
    private String multiAngleTradeType;
    @ApiModelProperty(value = "STO1单号")
    private String sto1No;
    @ApiModelProperty(value = "STO1行项目")
    private String sto1LineItem;
    @ApiModelProperty(value = "行项目类型")
    private String lineItemType;
    @ApiModelProperty(value = "行项目文本")
    private String lineItemText;

    @ApiModelProperty(value = "工厂代码")
    private String factoryCode;
    @ApiModelProperty(value = "基本计量单位转换分子")
    private String umrez;
    @ApiModelProperty(value = "转换为基本计量单位的分母")
    private String umren;
    @ApiModelProperty(value = "未使用额度")
    private Float unusedQuota;
    @ApiModelProperty(value = "物料组代码")
    private String materialGroupCode;
    @ApiModelProperty(value = "存储条件代码")
    private String storageTemperatureCode;
    @ApiModelProperty(value = "行项目类型代码")
    private String lineItemTypeCode;


    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }
}
