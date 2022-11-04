package com.yhlo.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.entity.GeneralOrderApprovalVO;
import com.yhlo.oa.enums.OrderStatuEnum;
import com.yhlo.oa.service.GeneralOrderService;
import com.yhlo.oa.service.iml.GeneralOrderServiceImpl;
import com.yhlo.oa.util.*;
import com.yhlo.oa.webservice.GetSapConfigTableInfo;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.net.URL;
import java.util.*;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

/**
 * @author cy
 * @ClassName: 订单审批
 * @Description:
 * @date 2022/10/1311:28
 */
@Slf4j
@FXMLController
public class GeneralOrderApprovalController implements Initializable {


    private final static String[] ROWS = {
            "id", "orderType", "orderReference", "sapSalesOrderNo","sapPurchaseOrderNo",
            "ddsSalesOrderNo","salesOrg","productGroup","customerName","customerCode",
            "songdfmc","songdfdm","saleDept","saleGroup","termOfPayment","orderReason",
            "endCustomer","salesman","preparedBy","documentDate","totalAmount","amountNotTaxed",
            "shippingPoint","shippingConditions","approvalStatus","deliveryStatus","orderSummary",
            "shippingRemarks","multiAngleTradeCode","id_detail","lineItem","mnemonicCode",
            "materialNo","materialDesc","specification","orderQty","qtyDelivered","openQty",
            "orderUnit","basicUnit","factory","inventoryLocation","pricingDate","standardPrice",
            "manualPrice","orderBuomQty","discountRate","rebateAmount","amountBeforeDiscount",
            "discountedAmount","storageTemperature","taxRate","currency","batch","productionLicense",
            "fullNameOfManufacturer","approvalNo","customerMaterial","customerMaterialDesc",
            "provincialBiddingCode","cityBiddingCode","reservedCode","brand","materialGroup",
            "businessLicense","productionRecordCertificate","lastUnitPrice","multiAngleTradeType",
            "sto1No","sto1LineItem","lineItemType","lineItemText","createTime","createBy",
            "updateTime","updateBy"};



    @Autowired
    private GeneralOrderService orderService;
    private String userName ;


    @FXML
    public TextField ddsOrderNoAssembly;
    @FXML
    public TextField name1Assembly;
    @FXML
    public ComboBox<String> approvalStatusAssembly;

    @FXML
    public Button searchBt;
    @FXML
    public Button approvalBt;
    @FXML
    public Button disagreeBt;

    @FXML
    public ChoiceBox<String> selectPage;
    @FXML
    public Button btnPrev;
    @FXML
    public Button btnNext;
    @FXML
    public TextField toPageNoText;
    @FXML
    public TextField totalCountText;
    @FXML
    public TextField totalPageText;
    @FXML
    public HBox pageNoHBox;


    public List<String> checkedIdList = new ArrayList<>();

    Integer currentPage = 1;
    int count = 0;

    List<Button> buttonList = new ArrayList<>();


    @FXML
    public TableView<GeneralOrderApprovalVO> tableView;
    @FXML
    public TableColumn<GeneralOrderApprovalVO, CheckBox> mColumnSelect;
    @FXML
    private CheckBox mselectAll;

    @FXML
    public TableColumn<GeneralOrderApprovalVO, String> mColLineItem;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        orderService = SpringBeanUtil.getBean(GeneralOrderServiceImpl.class);



        KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);



        Preferences userPreferences = Preferences.userRoot();
        userName = userPreferences.get("userName","name");


        ImageView searchImage = new ImageView( CommonUtil.getImage("/img/icon/search.png"));
        searchBt.setGraphic(searchImage);

        ImageView approvalImage = new ImageView( CommonUtil.getImage("/img/icon/approve.png"));
        approvalBt.setGraphic(approvalImage);

        ImageView disagreeImage = new ImageView( CommonUtil.getImage("/img/icon/disagree.png"));
        disagreeBt.setGraphic(disagreeImage);


        approvalStatusAssembly.getItems().addAll("全部",OrderStatuEnum.ORDER_STATU_SUBMIT.getName(),
                OrderStatuEnum.ORDER_STATU_BUSINESS.getName(),OrderStatuEnum.ORDER_STATU_FINANCIAL.getName(),
                OrderStatuEnum.ORDER_STATU_RETURN.getName());


        //mColLineItem.setCellValueFactory(new PropertyValueFactory<>("xuh"));

        //NumberTableCellFactory.createNumberColumn(mColLineItem, 1);

        setTableTitle();
        initTable();

        // 初始化所有checkbox复选框
        mColumnSelect.setCellValueFactory(param -> {
            final ObservableValue<CheckBox> observableValue = param.getValue().getCb().getCheckBox();
            return observableValue;
        });

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//选多行
        //tableView.getSelectionModel().setCellSelectionEnabled(true);//点击选中单个单元格

        tableView.setEditable(true);//表格设置为可编辑

        // 单击选中行
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                Checkbox newCheckbox = newValue.getCb();
                ObservableList<GeneralOrderApprovalVO> items = tableView.getItems();
                //System.err.println("newValue=="+newValue);
                if (newCheckbox.isSelected()) {
                    newCheckbox.setSelected(false);
                } else {
                    newCheckbox.setSelected(true);
                }
            }

        });


        //单独选择的监听，开启复制功能
        tableView.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {

            @Override
            public void invalidated(javafx.beans.Observable observable) {
                ObservableList<TablePosition> observableList = (ObservableList<TablePosition>) observable;
                for(int i=0;i<observableList.size();i++){
                    TablePosition tablePosition = observableList.get(i);
                    String TableColumnName = tablePosition.getTableColumn().getText();//列名
                    Object cellData = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                   // System.out.println("您选择的坐标为:("+tablePosition.getColumn()+","+tablePosition.getRow()+")_内容为:"+cellData.toString());
                    //System.out.println("列名=="+TableColumnName);

                    tableView.setOnKeyPressed(event -> {
                        if (keyCodeCopy.match(event)) {
                            String content = cellData == null ? "" : cellData.toString();
                            ClipboardContent clipboardContent = new ClipboardContent();
                            clipboardContent.putString(content);
                            Clipboard.getSystemClipboard().setContent(clipboardContent);
                        }
                    });

                }
            }
        });



        tableView.getColumns().addListener(new ListChangeListener() {//禁止列拖动
            boolean isturnback = false;

            @Override
            public void onChanged(Change c) {

                if (!isturnback) {
                    while (c.next()) {
                        if (!c.wasPermutated() && !c.wasUpdated()) {
                            isturnback = true;
                            tableView.getColumns().setAll(c.getRemoved());
                        }
                    }
                }
                else {
                    isturnback = false;
                }
            }
        });

    }




    /**
     * @Author cy
     * @Description 设置标题
     * @Return
     * @Date 2022/10/14 8:50
     */
    public void setTableTitle(){
        ObservableList<TableColumn<GeneralOrderApprovalVO, ?>> columns = tableView.getColumns();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(GeneralOrderApprovalVO.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<GeneralOrderApprovalVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }


    /**
     * @Author cy
     * @Description 初始化表格控件
     * @Return
     * @Date 2022/10/14 8:52
     */
    public void initTable(){

        List<String> stringList = pageSizeSelectData().values().stream().sorted(Comparator.comparingInt(String::hashCode)).collect(Collectors.toList());

        selectPage.getItems().addAll(FXCollections.observableList(stringList));
        selectPage.setValue(stringList.get(0));

        selectPage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int pageSize = getPageSize((Integer) newValue);
            final PageInfo<GeneralOrderApprovalVO> page = getTablePage(currentPage, pageSize);
            final int pagePages = page.getPages();
            if (currentPage == 1) {
                btnPrev.setDisable(true);
                btnNext.setDisable(false);
            } else if (currentPage > 1 && currentPage < pagePages) {
                btnPrev.setDisable(false);
                btnNext.setDisable(false);
            } else {
                btnPrev.setDisable(false);
                btnNext.setDisable(true);
            }
            final String text = toPageNoText.getText();
            if (page.getTotal() == 0 || text.equals("")) {
                toPageNoText.setText("1");
                totalCountText.setText("0");
            } else {
                if (currentPage > pagePages) {
                    currentPage = pagePages;
                }
                toPageNoText.setText(currentPage + "");
                totalCountText.setText(page.getTotal() + "");
            }
            totalPageText.setText(pagePages + "");
            setHBoxPaginationBtn(pageSize, page.getNavigatepageNums());
            setTableViewItems(page.getList());
        });
    }

    /**
     * @Author cy
     * @Description 初始化每页显示数量
     * @Return
     * @Date 2022/10/14 8:53
     */
    public Map<String, String> pageSizeSelectData() {
        Map<String, String> map = new HashMap<>(5);
        map.put("10", "10条/页");
        map.put("20", "20条/页");
        map.put("50", "50条/页");
        map.put("100", "100条/页");
        map.put("200", "200条/页");
        return map;
    }


    /**
     * @Author cy
     * @Description 获取页面大小
     * @Return
     * @Date 2022/10/14 9:10
     */
    private int getPageSize(Integer newValue) {
        final String value = selectPage.getItems().get(newValue);
        return pageSizeSelected(pageSizeSelectData(), value);
    }

    /**
     * @Author cy
     * @Description 获取 已选择页面大小
     * @Return
     * @Date 2022/10/14 9:09
     */
    public Integer pageSizeSelected(Map<String, String> map, String value) {
        int pageSize = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Objects.equals(entry.getValue(), value)) {
                pageSize = Integer.parseInt(entry.getKey());
                break;
            }
        }
        return pageSize;
    }

    /**
     * @Author cy
     * @Description 获取表格页面
     * @param currentPage 当前页
     * @param pageSize 每页显示多少条
     * @Return
     * @Date 2022/10/14 9:06
     */
    private PageInfo<GeneralOrderApprovalVO> getTablePage(Integer currentPage, Integer pageSize) {
        String ddsSalesOrderNo = ddsOrderNoAssembly.getText();
        String customerName  = name1Assembly.getText();
        String approvalStatus = approvalStatusAssembly.getValue()=="全部"?"":approvalStatusAssembly.getValue();
        return orderService.getGeneralOrderApprovalList(currentPage, pageSize,ddsSalesOrderNo,customerName,approvalStatus);
    }


    /**
     * @Author cy
     * @Description 添加分页控件按钮
     * @Return
     * @Date 2022/10/14 9:11
     */
    private void setHBoxPaginationBtn(int pageSize, int[] pageNums) {
        if (pageNums.length > 0) {
            buttonList.clear();
            final ObservableList<Node> noHBoxChildren = pageNoHBox.getChildren();
            noHBoxChildren.clear();
            for (int pageNum : pageNums) {
                final Button button = new Button(String.valueOf(pageNum));
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setOnAction(event -> {
                    toPageNoText.setText(pageNum + "");
                    PageInfo<GeneralOrderApprovalVO> pageInfo = getTablePage(pageNum, pageSize);
                    totalCountText.setText(pageInfo.getTotal() + "");
                    setTableViewItems(pageInfo.getList());
                });
                buttonList.add(button);
            }
            noHBoxChildren.addAll(buttonList);
        }
    }

    /**
     * @Author cy
     * @Description 设置表格视图项
     * @Return
     * @Date 2022/10/14 9:12
     */
    public void setTableViewItems(List<GeneralOrderApprovalVO> list) {
        tableView.setItems(FXCollections.observableList(list));
        tableView.refresh();
    }


    /**
     * @Author cy
     * @Description 加载数据列表
     * @Return
     * @Date 2022/10/14 9:16
     */
    public void loadTable() {

        String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
        int pageSize = pageSizeSelected(pageSizeSelectData(), value);
        PageInfo<GeneralOrderApprovalVO> tablePage = getTablePage(currentPage, pageSize);
        long pageTotal = tablePage.getTotal();
        if (pageTotal == 0) {
            btnPrev.setDisable(true);
            btnNext.setDisable(true);
        } else {
            btnPrev.setDisable(false);
            btnNext.setDisable(false);
        }

        int[] pageNums = tablePage.getNavigatepageNums();
        log.info("分页组件----{}", pageNums);
        setHBoxPaginationBtn(pageSize, pageNums);

        btnPrev.setDisable(tablePage.getPrePage() == 0);
        btnNext.setDisable(tablePage.getNextPage() == 0);

        toPageNoText.setText(currentPage + "");
        totalCountText.setText(pageTotal + "");
        totalPageText.setText(tablePage.getPages() + "");
        setTableViewItems(tablePage.getList());


    }


    /**
     * @Author cy
     * @Description 下一页
     * @Return
     * @Date 2022/10/14 9:19
     */
    public void nextPage(ActionEvent actionEvent) {
        final String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
        int pageSize = pageSizeSelected(pageSizeSelectData(), value);

        // 当前页码
        final String pageNoTextText = toPageNoText.getText();
        this.currentPage = Integer.parseInt(pageNoTextText) + 1;

        Button buttonNext = (Button) actionEvent.getSource();

        final String countTextText = totalCountText.getText();
        if (countTextText.equals("0")) {
            buttonNext.setDisable(true);
            btnPrev.setDisable(true);
        } else {
            toPageNoText.setText(this.currentPage + "");
            final PageInfo<GeneralOrderApprovalVO> page = getTablePage(currentPage, pageSize);
            final int pagePages = page.getPages();
            if (currentPage > pagePages) {
                toPageNoText.setText(String.valueOf(pagePages));
                buttonNext.setDisable(true);
                btnPrev.setDisable(false);
                CommonUtil._alertInformation("已经到最后一页啦");
                return;
            }
            buttonNext.setDisable(false);
            btnPrev.setDisable(false);
            totalCountText.setText(page.getTotal() + "");
            totalPageText.setText(page.getPages() + "");
            setTableViewItems(page.getList());
            final int[] pageNums = page.getNavigatepageNums();
            log.info("下一页分页页码数组----{}", pageNums);
            setHBoxPaginationBtn(pageSize, pageNums);
        }
    }


    /**
     * @Author cy
     * @Description 全选
     * @Return
     * @Date 2022/10/14 9:17
     */
    public void selectAll(ActionEvent actionEvent) {
        checkedIdList.clear();
        ObservableList<GeneralOrderApprovalVO> items = tableView.getItems();
        //System.err.println("items=="+items.size()+"---"+items);
        if (mselectAll.isSelected()) {
            for (GeneralOrderApprovalVO rp : items) {
                rp.getCb().setSelected(true);
                checkedIdList.add(String.valueOf(rp.getId()));
            }
        } else {
            for (GeneralOrderApprovalVO go : items) {
                go.getCb().setSelected(false);
            }
            checkedIdList.clear();
        }
    }

    /**
     * @Author cy
     * @Description 上一页
     * @Return
     * @Date 2022/10/14 9:18
     */
    public void prevPage(ActionEvent actionEvent) {
        final String pageNoTextText = toPageNoText.getText();
        this.currentPage = Integer.parseInt(pageNoTextText) - 1;

        Button buttonPrev = (Button) actionEvent.getSource();

        btnPrev.setDisable(pageNoTextText.equals("1"));

        final String countTextText = totalCountText.getText();
        if (countTextText.equals("0")) {
            buttonPrev.setDisable(true);
            btnNext.setDisable(true);
        } else {
            toPageNoText.setText(this.currentPage + "");
            if (currentPage == 0) {
                toPageNoText.setText("1");
                this.currentPage = 1;
                CommonUtil._alertInformation("已经到第一页啦");
                buttonPrev.setDisable(true);
                btnNext.setDisable(false);
                return;
            }
            buttonPrev.setDisable(false);
            btnNext.setDisable(false);
            final String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
            int pageSize = pageSizeSelected(pageSizeSelectData(), value);
            final PageInfo<GeneralOrderApprovalVO> page = getTablePage(currentPage, pageSize);
            totalCountText.setText(page.getTotal() + "");
            totalPageText.setText(page.getPages() + "");
            setTableViewItems(page.getList());
            final int[] pageNums = page.getNavigatepageNums();
            log.info("上一页分页页码数组----{}", pageNums);
            setHBoxPaginationBtn(pageSize, pageNums);
        }
    }


    /**
     * @Author cy
     * @Description 审批通过
     * @Return
     * @Date 2022/10/14 9:22
     */
    public void approval(ActionEvent actionEvent) {
        log.info("批准");
        ObservableList<GeneralOrderApprovalVO> dataLsit = tableView.getItems();
        List<GeneralOrderApprovalVO> list = new ArrayList<>();
        int size = dataLsit.size();
        if (size <= 0) {
            CommonUtil._alertInformation("无可选列表，请勾选要操作的数据");
            return;
        }

        for (int i = 0; i < size; i++) {
            GeneralOrderApprovalVO s = dataLsit.get(i);
            if (s.getCb().isSelected()) {
                list.add(s);
            }
        }

        if (list.size() <= 0) {
            CommonUtil._alertInformation("请选择要提交审批的订单");
            return;
        }


        List<GeneralOrderApprovalVO> subList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            GeneralOrderApprovalVO s = list.get(i);
            if (!"".equals(s.getApprovalStatus()) &&
                    s.getApprovalStatus().equals(OrderStatuEnum.ORDER_STATU_BUSINESS.getName()))
            {
                CommonUtil._alertInformation("勾选列表中包含已审批的订单，请勿勾选，DDS订单号："+s.getDdsSalesOrderNo());
                return;
            }

            s.setUpdateBy(userName);
            subList.add(s);
        }


        List<GeneralOrderApprovalVO> headList = removeDupliById(subList);//订单头信息
        for (int i = 0; i < headList.size(); i++) {
            GeneralOrderApprovalVO s = headList.get(i);
            Map headMap = new HashMap<>();
            headMap.put("AUART","ZOR5");
            headMap.put("ZVBELN_RE",s.getOrderReference()==null?"":s.getOrderReference());
            headMap.put("BSTKD",s.getDdsSalesOrderNo()==null?"":s.getDdsSalesOrderNo());
            headMap.put("VKORG",s.getSalesOrg()==null?"":s.getSalesOrg());
            headMap.put("SPART",s.getProductGroup()==null?"":s.getProductGroup());
            headMap.put("VTWEG","20");
            headMap.put("KUNAG",s.getCustomerCode()==null?"":s.getCustomerCode());
            headMap.put("KUNNR",s.getSongdfdm()==null?"":s.getSongdfdm());
            headMap.put("VKBUR",s.getSaleDept()==null?"":s.getSaleDept());
            headMap.put("VKGRP",s.getSaleGroup()==null?"":s.getSaleGroup());
            headMap.put("ZTERM",s.getTermOfPayment()==null?"":s.getTermOfPayment());
            headMap.put("NAME_EC",s.getEndCustomer()==null?"":s.getEndCustomer());
            headMap.put("NAME_C1",s.getSalesman()==null?"":s.getSalesman());
            headMap.put("ERNAM",s.getPreparedBy()==null?"":s.getPreparedBy());
            headMap.put("AUDAT",s.getDocumentDate()==null?"":s.getDocumentDate());
           // headMap.put("VSTEL",s.getShippingPoint()==null?"":s.getShippingPoint());
            headMap.put("VSBED",s.getShippingConditions()==null?"":s.getShippingConditions());
            headMap.put("KZAZU","X");
            headMap.put("PODKZ","X");
            headMap.put("AUGRU",s.getOrderReason()==null?"":s.getOrderReason());
            headMap.put("TXT_Z001",s.getOrderSummary()==null?"":s.getOrderSummary());
            headMap.put("TXT_0001",s.getShippingRemarks()==null?"":s.getShippingRemarks());
            headMap.put("item","");

            List<GeneralOrderDetailVO> detailList = orderService.getOrderListByDdsSalesOrderNo(s.getDdsSalesOrderNo());

            List<Map> bodyDetailList = new ArrayList<>();
            for(int j = 0;j < detailList.size(); j++){
                GeneralOrderDetailVO de = detailList.get(j);
                Map detailMap = new HashMap<>();
                detailMap.put("POSNR",de.getLineItem());
                detailMap.put("MATNR",de.getMaterialNo()==null?"":de.getMaterialNo());
                detailMap.put("KWMENG",de.getOrderQty()==null?0:de.getOrderQty());//订单数量
                detailMap.put("VRKME",de.getOrderUnit()==null?"":de.getOrderUnit());//订单单位
                detailMap.put("WERKS",de.getFactoryCode()==null?"":de.getFactoryCode());//工厂代码
                detailMap.put("LGORT",de.getInventoryLocationCode()==null?"":de.getInventoryLocationCode());//库存地点
                detailMap.put("VSTEL",s.getShippingPoint()==null?"":s.getShippingPoint());//装运点
                detailMap.put("ZPR1",de.getManualPrice()==null?0:de.getManualPrice());//手工价
                detailMap.put("KBETR_Z005",de.getDiscountRate()==null?"":de.getDiscountRate());//折扣率
                detailMap.put("KBETR_Z006",de.getRebateAmount()==null?"":de.getRebateAmount());//返利金额
                detailMap.put("ZKNUMH",s.getMultiAngleTradeCode()==null?"":s.getMultiAngleTradeCode());//多角贸易代码
                detailMap.put("ZDJMY_TYPE",de.getMultiAngleTradeType()==null?"":de.getMultiAngleTradeType());//多角贸易类型
                detailMap.put("PSTYV",de.getLineItemTypeCode()==null?"":de.getLineItemTypeCode());//行项目类型
                detailMap.put("ZPTXT_0001",de.getLineItemText()==null?"":de.getLineItemText());//行项目文本
                bodyDetailList.add(detailMap);

            }

            headMap.put("item",bodyDetailList);

            JSONObject bodyJson = new JSONObject(headMap);

            String JSONSTR1 = "<![CDATA[["+bodyJson+"]]]>";
            //String JSONSTR1 = "<![CDATA[[{"KUNNR":"0000110074","KUNAG":"0000110074","item":[{"VRKME":"ZHI","ZPTXT_0001":"0.5ml离心管","WERKS":"3001","ZKNUMH":"","zpr1":100.0,"pstyv":"正常","POSNR":"0010","KWMENG":10.0,"lgort":"C002","KBETR_Z006":0.0,"KBETR_Z005":"","ZDJMY_TYPE":"1","VSTEL":"3001","MATNR":"XK-LXG05"}],"TXT_Z001":"深圳市龙华区中心医院","VSBED":"06","KZAZU":"X","NAME_C1":"E000000001","AUGRU":"","VKGRP":"","ZVBELN_RE":"","VKORG":"3000","SPART":"20","AUART":"ZOR5","VKBUR":"3001","BSTKD":"DDS202210190003","VTWEG":"20","AUDAT":"2022-06-07","PODKZ":"X","NAME_EC":"","ZTERM":"Z002","ERNAM":"cy","TXT_0001":""}]]]>";

            String result = GetSapConfigTableInfo.sendRequest("SD146_1",JSONSTR1);

            //result===[{"BSTKD":"1000000001","VBELN_SO":"3500000300","VBELN_DN":"","EBELN":"","TYPE":"S","MESSAGE":"订单创建成功！"}]
            log.info("订单创建接口返回结果："+result);

            log.info(userName+"在"+DateUtils.getTime()+"执行了订单<批准>操作，操作数据："
                    +"主数据【"+s+"】");

            if(!"".equals(result)&& !"error".equals(result) ){
                try {
                    JSONArray objects = JSON.parseArray(result);
                    if(objects.size()>0){
                        JSONObject job = objects.getJSONObject(i);
                        if(job.getString("TYPE").equals("S")){
                            String orderNo = job.getString("VBELN_SO");//SAP销售订单号
                            s.setSapSalesOrderNo(orderNo);
                            s.setApprovalStatus(OrderStatuEnum.ORDER_STATU_BUSINESS.getName());

                            String rs = orderService.updateOrderApprovalStatuAndOrderNo(s);
                            if(rs.indexOf("error")!=-1){
                                CommonUtil._alertErrorMessage("出错拉！！！","订单批准失败："+rs);
                                return;
                            }
                        }else{
                            CommonUtil._alertErrorMessage("出错拉！！！","订单传SAP失败，错误信息："+job.getString("MESSAGE"));
                            return;
                        }
                    }
                }catch (Exception e){
                    CommonUtil._alertErrorMessage("出错拉！！！","SAP订单接口返回解析失败："+e.getMessage());
                    e.printStackTrace();
                }
            }
        }


        /*String rs = orderService.updateOrderApprovalStatu(headList,"批准");
        if(rs.indexOf("error")!=-1){
            CommonUtil._alertErrorMessage("出错了！！！","批准失败"+rs);
            return;
        }*/


        ToastUtil.customizeToast("操作成功",2000,500.00,300.00);
        loadTable();

    }


    /**
     * @Author cy
     * @Description 去除List重复项
     * @Return
     * @Date 2022/10/18 11:32
     */
    public static List<GeneralOrderApprovalVO> removeDupliById(List<GeneralOrderApprovalVO> ls) {
        Set<GeneralOrderApprovalVO> set = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        set.addAll(ls);
        return new ArrayList<>(set);

    }

    /**
     * @Author cy
     * @Description 退回，退回后状态变为未审批
     * @Return
     * @Date 2022/10/14 9:22
     */
    public void disagree(ActionEvent actionEvent) {
        log.info("退回");
    }




    /**
     * @Author cy
     * @Description 复制表格行，可复制多行
     * demo：
     * tableView.setOnKeyPressed(event -> {
     *    if (keyCodeCopy.match(event)) {
     *       copySelectionToClipboard(tableView);
     *    }
     *  });
     * @Date 2022/10/17 16:24
     */
    public void copySelectionToClipboard(final TableView<?> table) {
        final Set<Integer> rows = new TreeSet<>();
        for (final TablePosition tablePosition : table.getSelectionModel().getSelectedCells()) {
            rows.add(tablePosition.getRow());
        }
        final StringBuilder strb = new StringBuilder();
        boolean firstRow = true;
        for (final Integer row : rows) {
            if (!firstRow) {
                strb.append('\n');
            }
            firstRow = false;
            boolean firstCol = true;
            for (final TableColumn<?, ?> column : table.getColumns()) {
                if (!firstCol) {
                    strb.append('\t');
                }
                firstCol = false;
                final Object cellData = column.getCellData(row);
                strb.append(cellData == null ? "" : cellData.toString());
            }
        }
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(strb.toString());
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }


}


