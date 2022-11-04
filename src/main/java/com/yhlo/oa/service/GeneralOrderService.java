package com.yhlo.oa.service;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.GeneralOrderApprovalVO;
import com.yhlo.oa.entity.GeneralOrderDetailVO;
import com.yhlo.oa.entity.GeneralOrderVO;
import com.yhlo.oa.entity.RebatePolicyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GeneralOrderService {

    public String insertOrder(GeneralOrderVO order,List <GeneralOrderDetailVO> detailList);

    public String updateOrder(GeneralOrderVO order,List <GeneralOrderDetailVO> detailList);

    public List<GeneralOrderVO> checkDdsSalesOrderNoExists(String nowDate);

    public PageInfo<GeneralOrderApprovalVO> getGeneralOrderApprovalList(Integer currentPage, Integer pageSize, String ddsSalesOrderNo, String customerName,String approvalStatus);

    public String updateOrderApprovalStatu(List<GeneralOrderApprovalVO> list,String operationType);

    public String updateOrderApprovalStatuAndOrderNo(GeneralOrderApprovalVO goa);

    public List<GeneralOrderDetailVO> getOrderListByDdsSalesOrderNo(String ddsSalesOrderNo);

    public List<GeneralOrderVO> getOrderList(String ddsSalesOrderNo);
}
