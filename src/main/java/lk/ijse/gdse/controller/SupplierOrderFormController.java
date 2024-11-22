package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.dto.SupplierOrderDto;
import lk.ijse.gdse.dto.Tm.SupplierOrderTm;
import lk.ijse.gdse.dto.Tm.SupplierTm;
import lk.ijse.gdse.model.EmployeeModel;
import lk.ijse.gdse.model.StockModel;
import lk.ijse.gdse.model.SupplierModel;
import lk.ijse.gdse.model.SupplierOrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierOrderFormController implements Initializable {

    StockModel stockModel = new StockModel();
    SupplierModel supplierModel = new SupplierModel();
    EmployeeModel employeeModel = new EmployeeModel();

    SupplierOrderModel supplierOrderModel = new SupplierOrderModel();

    ObservableList<SupplierOrderTm> tbOrderCartTm = FXCollections.observableArrayList();


    @FXML
    private ComboBox<String> cmbEmployee;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbStockId;

    @FXML
    private ComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartBrand;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartEmpId;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartModel;

    @FXML
    private TableColumn<SupplierOrderTm, Date> colCartOrderDate;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartOrderId;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartOrderSize;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartOrderStatus;

    @FXML
    private TableColumn<SupplierOrderTm, Double> colCartOrderTotal;

    @FXML
    private TableColumn<SupplierOrderTm, Integer> colCartQty;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartStockId;

    @FXML
    private TableColumn<SupplierOrderTm, String> colCartSupplierId;

    @FXML
    private TableColumn<SupplierOrderTm, Integer> colCartYear;

    @FXML
    private TableColumn<SupplierOrderTm, Date> colRequestDate;

    @FXML
    private DatePicker dateOderDate;

    @FXML
    private DatePicker dateReuestDate;

    @FXML
    private Label lbOrderId;

    @FXML
    private TableView<SupplierOrderTm> tbOrderCart;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtTotalAmount;

    @FXML
    private TextField txtYear;

    @FXML
    void butAddOnAction(ActionEvent event) throws SQLException {
        String orderId = lbOrderId.getText();
        String stockId = cmbStockId.getValue();
        String supId = cmbSupplierId.getValue();
        String employeeId = cmbEmployee.getValue();
        String status = cmbStatus.getValue();
        String orderSize = txtSize.getText();
        String tireModel = txtModel.getText();
        String tireBrand = txtBrand.getText();

        LocalDate date1 = dateOderDate.getValue();
        LocalDate date2 = dateReuestDate.getValue();

        if (orderId.isEmpty() || stockId == null || supId == null || employeeId == null ||
                date1 == null || date2 == null || status.isEmpty() ||
                orderSize == null || txtYear.getText().isEmpty() || txtTotalAmount.getText().isEmpty() ||
                txtQty.getText().isEmpty() || tireModel == null || tireBrand == null ) {

            new Alert(Alert.AlertType.ERROR, "Empty Fields").showAndWait();
            return;
        }

        int year;
        double total;
        int qty;

        try {
            year = Integer.parseInt(txtYear.getText());
            total = Double.parseDouble(txtTotalAmount.getText());
            qty = Integer.parseInt(txtQty.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "All fields must be filled correctly.").showAndWait();
            return;
        }

        Date orderDate = Date.valueOf(dateOderDate.getValue());
        Date requestDate = Date.valueOf(dateReuestDate.getValue());

        String qtyPattern = "^[1-9][0-9]*$";
        String brandPattern = "^[a-zA-Z ]{2,30}$";
        String modelPattern = "^[a-zA-Z0-9-_]{2,20}$";
        String sizePattern = "^\\d{3}(\\/(\\d{1,3}|R\\d{2}))?(R\\d{2})?(\\s?[0-9]{2,3}[A-Z]{1,2})?$";
        String yearPattern = "^\\d{4}$";
        String amountPattern = "^[1-9][0-9]*(\\.[0-9]{1,2})?$";
        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";

        if (!tireModel.matches(modelPattern)) {
            new Alert(Alert.AlertType.ERROR, "Tire Model must be filled correctly.").showAndWait();
            return;
        }

        if (!tireBrand.matches(brandPattern)) {
            new Alert(Alert.AlertType.ERROR, "Tire Brand must be filled correctly.").showAndWait();
            return;
        }

        if (!orderSize.matches(sizePattern)) {
            new Alert(Alert.AlertType.ERROR, "Order Size must be filled correctly.").showAndWait();
            return;
        }

        if (!txtYear.getText().matches(yearPattern)) {
            new Alert(Alert.AlertType.ERROR, "Year must be filled correctly.").showAndWait();
            return;
        }

        if (!txtTotalAmount.getText().matches(amountPattern)) {
            new Alert(Alert.AlertType.ERROR, "Total Amount must be filled correctly.").showAndWait();
            return;
        }

        if (!txtQty.getText().matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Quantity must be filled correctly.").showAndWait();
            return;
        }

        if (!orderDate.toString().matches(datePattern)) {
            new Alert(Alert.AlertType.ERROR, "Order Date must be filled correctly.").showAndWait();
            return;
        }

        if (!requestDate.toString().matches(datePattern)) {
            new Alert(Alert.AlertType.ERROR, "Request Date must be filled correctly.").showAndWait();
            return;
        }

        if (!date1.isBefore(date2)) {
            new Alert(Alert.AlertType.ERROR, "The first day must be before the second day.").showAndWait();
            return;
        }

        Button button = new Button("Remove");

        SupplierOrderTm supplierOrderTm = new SupplierOrderTm(
                orderId,
                supId,
                employeeId,
                stockId,
                tireModel,
                qty,
                orderDate,
                requestDate,
                total,
                tireBrand,
                year,
                orderSize,
                status,
                button
        );

        button.setOnAction(event1 -> {
            tbOrderCartTm.remove(supplierOrderTm);
            tbOrderCart.refresh();
        });

        tbOrderCartTm.add(supplierOrderTm);
        reset();

    }


    void setCellValues(){
        colCartOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCartOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCartSupplierId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colCartStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colCartEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        colCartBrand.setCellValueFactory(new PropertyValueFactory<>("tireBrand"));
        colCartModel.setCellValueFactory(new PropertyValueFactory<>("tireModel"));
        colCartYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCartOrderTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCartOrderSize.setCellValueFactory(new PropertyValueFactory<>("orderSize"));
        colCartOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));

        tbOrderCart.setItems(tbOrderCartTm);
    }

    @FXML
    void butConfirmOnAction(ActionEvent event) throws SQLException {
        ArrayList<SupplierOrderDto> supplierOrderDtos = new ArrayList<>();

        for (SupplierOrderTm supplierOrderTm : tbOrderCartTm) {
            SupplierOrderDto supplierOrderDto = new SupplierOrderDto();

            supplierOrderDto.setOrderId(supplierOrderTm.getOrderId());
            supplierOrderDto.setStockId(supplierOrderTm.getStockId());
            supplierOrderDto.setSupId(supplierOrderTm.getSupId());
            supplierOrderDto.setEmployeeId(supplierOrderTm.getEmployeeId());
            supplierOrderDto.setQty(supplierOrderTm.getQty());
            supplierOrderDto.setTireModel(supplierOrderTm.getTireModel());
            supplierOrderDto.setTireBrand(supplierOrderTm.getTireBrand());
            supplierOrderDto.setOrderDate(supplierOrderTm.getOrderDate());
            supplierOrderDto.setYear(supplierOrderTm.getYear());
            supplierOrderDto.setOrderSize(supplierOrderTm.getOrderSize());
            supplierOrderDto.setOrderStatus(supplierOrderTm.getOrderStatus());
            supplierOrderDto.setRequestDate(supplierOrderTm.getRequestDate());
            supplierOrderDto.setTotal(supplierOrderTm.getTotal());

            supplierOrderDtos.add(supplierOrderDto);
        }

        if (supplierOrderDtos.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty Cart").showAndWait();
            return;
        }

        boolean isSaveOrder = supplierOrderModel.saveOrder(supplierOrderDtos);

        if (isSaveOrder){
            new Alert(Alert.AlertType.INFORMATION,"Successfully saved").showAndWait();
            tbOrderCart.getItems().clear();
            reset();
        }else{
            new Alert(Alert.AlertType.ERROR,"Unsuccessful save").showAndWait();
        }
    }


    @FXML
    void butViewOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/ViewSupplierOrder.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        Image image = new Image(getClass().getResourceAsStream("/images/tire.png"));
        stage.getIcons().add(image);
        stage.setTitle("View Supplier Orders");
        stage.setResizable(false);
        stage.show();
    }

    void loadComboBoxValues() throws SQLException {

        ArrayList<String> stockId = stockModel.getAllStockId();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (String id : stockId) {
            observableList.addAll(id);
        }
        cmbStockId.setItems(observableList);

        ArrayList<String> supplierId = supplierModel.getAllSupplierId();
        ObservableList<String> observableList1 = FXCollections.observableArrayList();
        for (String id : supplierId) {
            observableList1.addAll(id);
        }
        cmbSupplierId.setItems(observableList1);

        ArrayList<String> empId = employeeModel.getAllEmployeesId();
        ObservableList<String> observableList2 = FXCollections.observableArrayList();
        for (String id : empId) {
            observableList2.addAll(id);
        }
        cmbEmployee.setItems(observableList2);

        ObservableList<String> paymentOptions = FXCollections.observableArrayList("Pending", "Completed");
        cmbStatus.setItems(paymentOptions);
    }

    private void getNextOrderId() throws SQLException {
        String orderId = supplierOrderModel.getNextOrderId();
        lbOrderId.setText(orderId);
    }

    private void reset() throws SQLException {
        getNextOrderId();

        txtQty.setText("");
        txtModel.setText("");
        txtSize.setText("");
        dateOderDate.setValue(null);
        txtYear.setText("");
        dateReuestDate.setValue(null);
        txtTotalAmount.setText("");
        txtBrand.setText("");
        cmbStatus.setValue("");

    }

    private void configureDatePicker() {
        dateOderDate.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(java.time.LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        dateReuestDate.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(java.time.LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCartOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCartOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCartSupplierId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colCartStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colCartEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        colCartBrand.setCellValueFactory(new PropertyValueFactory<>("tireBrand"));
        colCartModel.setCellValueFactory(new PropertyValueFactory<>("tireModel"));
        colCartYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCartOrderTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCartOrderSize.setCellValueFactory(new PropertyValueFactory<>("orderSize"));
        colCartOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));

        try {
            configureDatePicker();
            loadComboBoxValues();
            reset();
            setCellValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
