package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.OrderViewDto;
import lk.ijse.gdse.dto.Tm.OrderViewTm;
import lk.ijse.gdse.model.OrderModel;
import lk.ijse.gdse.model.OrderViewModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ViewOrdersController implements Initializable {

    OrderViewModel orderViewModel = new OrderViewModel();

    @FXML
    private Button butReset;

    @FXML
    private Button butSearchCustId;

    @FXML
    private Button butSearchDay;

    @FXML
    private Button butGenarateForCustomerId;

    @FXML
    private Button butGenarateForDays;

    @FXML
    private TableColumn<OrderViewTm, String> ColDescription;

    @FXML
    private TableColumn<OrderViewTm, String> colCustId;

    @FXML
    private TableColumn<OrderViewTm, String> colDate;

    @FXML
    private TableColumn<OrderViewTm, String> colEmpId;

    @FXML
    private TableColumn<OrderViewTm, String> colOrderId;

    @FXML
    private TableColumn<OrderViewTm, String> colPaymentMethod;

    @FXML
    private TableColumn<OrderViewTm, String> colQty;

    @FXML
    private TableColumn<OrderViewTm, String> colTireId;

    @FXML
    private TableColumn<OrderViewTm, String> colTotal;

    @FXML
    private TableView<OrderViewTm> tbOrders;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private TextField txtFirstDay;

    @FXML
    private TextField txtSecondDay;

    @FXML
    void butSearchCustIdOnAction(ActionEvent event) throws SQLException {
        String custId = cmbCustomerId.getValue();

        if (custId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer ID").showAndWait();
            return;
        }

        ArrayList<OrderViewDto> orderViewDtos = orderViewModel.searchByCustId(custId);

        if (orderViewDtos.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "No orders found for customer ID: " + custId).showAndWait();
            reset();
            return;
        }

        ObservableList<OrderViewTm> orderViewTms = FXCollections.observableArrayList();

        for (OrderViewDto orderViewDto : orderViewDtos) {
            OrderViewTm orderViewTm = new OrderViewTm(
                    orderViewDto.getOrderId(),
                    orderViewDto.getDate(),
                    orderViewDto.getCustId(),
                    orderViewDto.getEmpId(),
                    orderViewDto.getTireId(),
                    orderViewDto.getDescription(),
                    orderViewDto.getPayment_method(),
                    orderViewDto.getQty(),
                    orderViewDto.getTotal_amount()
            );
            orderViewTms.add(orderViewTm);
        }

        tbOrders.setItems(orderViewTms);
    }

    @FXML
    void butSearchDayOnAction(ActionEvent event) throws SQLException {
        String day1 = txtFirstDay.getText();
        String day2 = txtSecondDay.getText();

        String dayPatten = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$";

        if (!day1.matches(dayPatten) || !day2.matches(dayPatten)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid date").showAndWait();
            return;
        }

        if (day1.isEmpty() || day2.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter both start and end days").showAndWait();
            return;
        }


        LocalDate date1 = LocalDate.parse(day1);
        LocalDate date2 = LocalDate.parse(day2);

        if (!date1.isBefore(date2)) {
            new Alert(Alert.AlertType.ERROR, "The first day must be before the second day.").showAndWait();
            return;
        }

        ArrayList<OrderViewDto> orderViewDtos = orderViewModel.searchByDay(day1, day2);

        if (orderViewDtos.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "No orders found between " + day1 + " and " + day2).showAndWait();
            reset();
            return;
        }

        ObservableList<OrderViewTm> orderViewTms = FXCollections.observableArrayList();

        for (OrderViewDto orderViewDto : orderViewDtos) {
            OrderViewTm orderViewTm = new OrderViewTm(
                    orderViewDto.getOrderId(),
                    orderViewDto.getDate(),
                    orderViewDto.getCustId(),
                    orderViewDto.getEmpId(),
                    orderViewDto.getTireId(),
                    orderViewDto.getDescription(),
                    orderViewDto.getPayment_method(),
                    orderViewDto.getQty(),
                    orderViewDto.getTotal_amount()
            );
            orderViewTms.add(orderViewTm);
        }

        tbOrders.setItems(orderViewTms);
    }

    @FXML
    void butResetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    public void getAllOrders() throws SQLException {
        ArrayList<OrderViewDto> orderViewDtos = orderViewModel.getAllOrders();

        ObservableList<OrderViewTm> orderViewTms = FXCollections.observableArrayList();

        for (OrderViewDto orderViewDto : orderViewDtos) {
            OrderViewTm orderViewTm = new OrderViewTm(
                    orderViewDto.getOrderId(),
                    orderViewDto.getDate(),
                    orderViewDto.getCustId(),
                    orderViewDto.getEmpId(),
                    orderViewDto.getTireId(),
                    orderViewDto.getDescription(),
                    orderViewDto.getPayment_method(),
                    orderViewDto.getQty(),
                    orderViewDto.getTotal_amount()
            );
            orderViewTms.add(orderViewTm);
        }

        tbOrders.setItems(orderViewTms);
    }

    private void reset() throws SQLException {
        getAllOrders();

        txtFirstDay.setText("");
        txtSecondDay.setText("");
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = orderViewModel.getAllCustomerIds();
        ObservableList<String> observableCustomerIds = FXCollections.observableArrayList(customerIds);
        cmbCustomerId.setItems(observableCustomerIds);
    }

    @FXML
    void butGenarateForDaysOnAction(ActionEvent event) {

        String day1 = txtFirstDay.getText();
        String dya2 = txtSecondDay.getText();

        if (day1.isEmpty() || dya2.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter both start and end days").showAndWait();
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/OrderReportForDays.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("dayOne",txtFirstDay.getText());
            parameters.put("dayTwo",txtSecondDay.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    void butGenarateForCustomerIdOnAction(ActionEvent event) {
        String customerId = cmbCustomerId.getValue();

        if (customerId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer ID").showAndWait();
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/OrderForCustomerIdReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("custId",cmbCustomerId.getValue());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
//            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colTireId.setCellValueFactory(new PropertyValueFactory<>("tireId"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("payment_method"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total_amount"));

        try {
            loadCustomerIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
