package com.yhlo.oa.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.GeneralOrderApprovalVO;
import com.yhlo.oa.entity.GeneralOrderDetailVO;
import com.yhlo.oa.entity.GeneralOrderVO;
import com.yhlo.oa.enums.OrderStatuEnum;
import com.yhlo.oa.mapper.GeneralOrderMapper;
import com.yhlo.oa.service.GeneralOrderService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @create: 2022-04-24 16:25
 * @description:
 **/
@Service
public class GeneralOrderServiceImpl implements GeneralOrderService {

    @Resource
    private GeneralOrderMapper orderMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;



    @Override
    public List<GeneralOrderVO> checkDdsSalesOrderNoExists(String nowDate) {
        return orderMapper.checkDdsSalesOrderNoExists(nowDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertOrder(GeneralOrderVO order, List<GeneralOrderDetailVO> detailList) {

        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GeneralOrderMapper mapper = session.getMapper(GeneralOrderMapper.class);

        String rs = "ok，操作成功";

        try {
            orderMapper.insertOrder(order);
            rs = String.valueOf(order.getId());
            for (int i = 0; i < detailList.size(); i++) {
                detailList.get(i).setDdsSalesOrderNo(order.getDdsSalesOrderNo());
                detailList.get(i).setMultiAngleTradeCode(order.getMultiAngleTradeCode());
                detailList.get(i).setCreateBy(order.getCreateBy());
                mapper.insertOrderDetails(detailList.get(i));
                if (i % 50 == 0 || i == detailList.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateOrder(GeneralOrderVO order, List<GeneralOrderDetailVO> detailList) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GeneralOrderMapper mapper = session.getMapper(GeneralOrderMapper.class);

        String rs = "ok，操作成功";

        try {
            orderMapper.deleteOrderDetails(order);
            orderMapper.updateOrder(order);
            for (int i = 0; i < detailList.size(); i++) {
                detailList.get(i).setDdsSalesOrderNo(order.getDdsSalesOrderNo());
                detailList.get(i).setMultiAngleTradeCode(order.getMultiAngleTradeCode());
                detailList.get(i).setCreateBy(order.getCreateBy());
                mapper.insertOrderDetails(detailList.get(i));
                if (i % 50 == 0 || i == detailList.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }


    @Override
    public PageInfo<GeneralOrderApprovalVO> getGeneralOrderApprovalList(Integer currentPage, Integer pageSize, String ddsSalesOrderNo, String customerName,String approvalStatus) {
        PageHelper.startPage(currentPage, pageSize);
        List<GeneralOrderApprovalVO> list = orderMapper.getGeneralOrderApprovalList(ddsSalesOrderNo,customerName,approvalStatus);
        return new PageInfo<>(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateOrderApprovalStatu(List<GeneralOrderApprovalVO> list,String operationType) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GeneralOrderMapper mapper = session.getMapper(GeneralOrderMapper.class);
        String rs = "ok，操作成功";
        try {
            if("批准".equals(operationType)){
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setApprovalStatus(OrderStatuEnum.ORDER_STATU_BUSINESS.getName());
                    mapper.updateOrderApprovalStatu(list.get(i));
                    if (i % 50 == 0 || i == list.size() - 1) {
                        session.commit();
                        session.clearCache(); //清理缓存，防止溢出
                    }
                }

            }else  if("退回".equals(operationType)){
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setApprovalStatus(OrderStatuEnum.ORDER_STATU_RETURN.getName());
                    mapper.updateOrderApprovalStatu(list.get(i));
                    if (i % 50 == 0 || i == list.size() - 1) {
                        session.commit();
                        session.clearCache(); //清理缓存，防止溢出
                    }
                }
            }

            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }


    @Override
    public String updateOrderApprovalStatuAndOrderNo(GeneralOrderApprovalVO goa) {
        String rs = "ok，操作成功";
        try {
            orderMapper.updateOrderApprovalStatuAndOrderNo(goa);
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<GeneralOrderDetailVO> getOrderListByDdsSalesOrderNo(String ddsSalesOrderNo) {
        return orderMapper.getOrderListByDdsSalesOrderNo(ddsSalesOrderNo);
    }

    @Override
    public List<GeneralOrderVO> getOrderList(String ddsSalesOrderNo) {
        return orderMapper.getOrderList(ddsSalesOrderNo);
    }


}
