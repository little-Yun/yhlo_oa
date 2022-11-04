package com.yhlo.oa.mapper;

import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicDataMapper {

    public List<It_Kna1VO> queryCustomerList(@Param("kunnr") String kunnr);
    public List<It_Kna1VO> querySalesmanList(@Param("kunnr") String kunnr);
    public List<TvkotVO> querySaleOrgList(@Param("vkorg") String vkorg);
    public List<T023tVO> queryMaterialGroupList();
    public List<TspatVO> queryProductGroupList(@Param("spart") String spart);
    public List<TvkbtVO> querySalesDeptList(@Param("vkbur") String vkbur);
    public List<TvgrtVO> querySalesGroupList(@Param("vkgrp") String vkgrp);
    public List<It_KnvvVO> queryCustomerSalesViewList(@Param("vkorg") String vkorg);
    public List<TvautVO> queryOrderReasonList(@Param("augru") String augru);
    public List<TvsttVO> queryShippingPointList(@Param("vstel") String vstel);
    public List<TvsbtVO> queryshippingConditionsList(@Param("vsbed") String vsbed);
    public List<TvzbtVO> getTermOfPayment(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg);
    public List<TvzbtVO> queryTermOfPaymentList();
    public List<It_MaraVO> queryMaterialList(@Param("param") String param);
    public List<It_MarmVO> queryOrderUnitList(@Param("matnr") String matnr);
    public List<T001wVO> getFactoryList(@Param("matnr") String matnr,@Param("vkorg") String vkorg);
    public List<T001wVO> queryAllFactoryList();
    public List<T001lVO> queryPositionList(@Param("werks") String werks);
    public List<RebatePolicyVO> queryRebatePolicyList1(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg,
                                                      @Param("name_ec") String name_ec, @Param("matkl") String matkl);

    public List<RebatePolicyVO> queryRebatePolicyList2(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg,
                                                      @Param("name_ec") String name_ec);

    public List<RebatePolicyVO> queryRebatePolicyList3(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg,
                                                      @Param("matkl") String matkl);

    public List<RebatePolicyVO> queryRebatePolicyList4(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg);

    public List<RebatePolicyVO> queryRebatePolicyList5(@Param("kunnr") String kunnr);

    public List<It_MvkeVO> getTaxList(@Param("matnr") String matnr,@Param("vkorg") String vkorg);

    public List<It_MarcVO> getMarcListByParam(@Param("matnr") String matnr,@Param("werks") String werks);

    public List<T023tVO> getMaterialGroupListByMatkl(@Param("matkl") String matkl);

    public List<T142tVO> getStorageTemperatureByRaube(@Param("raube") String raube);

    public List<It_Ztsd_012VO> getCustomerMaterial(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg,
                                                   @Param("matnr") String matnr);

    public List<It_But000VO> queryCustomerGeneralData(@Param("partner") String partner);

    public List<It_Ztsd_002VO> queryMultiAngleTrade(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg);

    public List<It_MaraVO> getMaterialListByMaterialNo(@Param("matnr") String matnr);
}
