package lk.ijse.gdse.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import lk.ijse.gdse.dto.DiscountDto;
import lk.ijse.gdse.dto.OrdersDto;
import lk.ijse.gdse.dto.PaymentDto;
import lk.ijse.gdse.dto.TireOrderDto;
import lk.ijse.gdse.dto.Tm.CartTm;
import lk.ijse.gdse.model.ConformOrderModel;
import lk.ijse.gdse.model.DiscountModel;
import lk.ijse.gdse.model.OrderModel;
import lk.ijse.gdse.model.PaymentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConformOrderController implements Initializable {

    private ConformOrderModel conformOrderModel = new ConformOrderModel();

    PlaceOrderController placeOrderController;
    OrderModel orderModel = new OrderModel();
    PaymentModel paymentModel = new PaymentModel();
    DiscountModel discountModel = new DiscountModel();

    @FXML
    private ComboBox<String> cmbPayment;

    @FXML
    private Button butConfirmOrder;

    @FXML
    private Label lbDate;

    @FXML
    private Label labAfterDiscountTotal;

    @FXML
    private Label labBalance;

    @FXML
    private Label labPaymentId;

    @FXML
    private Label labTotalAmount;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtPayment;

    @FXML
    void butBackOnAction(ActionEvent event) {

    }

    @FXML
    void butConfirmOrderOnAction(ActionEvent event) throws SQLException {

        if (cmbPayment.getValue() == null || txtDiscount.getText() == null || txtDiscount.getText().isEmpty() || txtPayment.getText().isEmpty() || txtPayment.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill the required fields").showAndWait();
            return;
        }

        OrdersDto ordersDto = this.placeOrderController.getOrdersDto();

        ArrayList<TireOrderDto> tireOrderDtos = new ArrayList<>();

        //TireOrderDto tireOrderDto = this.placeOrderController.getTireOrderDto();

        for (CartTm cartTm : this.placeOrderController.getCartTms()) {
            TireOrderDto tireOrderDto1 = new TireOrderDto();

            tireOrderDto1.setOrderId(this.placeOrderController.getLabOrderId().getText());
            tireOrderDto1.setTireId(cartTm.getTireId());
            tireOrderDto1.setDescription(cartTm.getDesc());
            tireOrderDto1.setQty(cartTm.getQty());
            tireOrderDto1.setTotal_amount(cartTm.getPrice());
            tireOrderDto1.setPayment_method(cmbPayment.getValue());

            tireOrderDtos.add(tireOrderDto1);

        }

        boolean isSaveOrder = orderModel.saveOrder(ordersDto, tireOrderDtos);

        if (isSaveOrder) {
            new Alert(Alert.AlertType.INFORMATION, "Order Save Successful").showAndWait();
            addPayment();
            addDiscount();
            reset();
        }else{
            new Alert(Alert.AlertType.ERROR, "Order Save UnSuccessful").showAndWait();
            reset();
        }

    }

    public void addDiscount() throws SQLException {
        String stringAmount = txtDiscount.getText();

        if (stringAmount != null){
            double amount = Double.parseDouble(stringAmount);

            if (amount < 0){
                return;
            }

            String discountId = discountModel.getNextDiscId();

            String dateString = this.placeOrderController.getDate().getText();
            Date date = Date.valueOf(dateString);

            String paymentId = labPaymentId.getText();

            DiscountDto discountDto = new DiscountDto(discountId,amount,date,paymentId);

            boolean result = discountModel.addDiscount(discountDto);
        }
    }

    public void addPayment() throws SQLException {

        String pId = labPaymentId.getText();
        String amountString = labAfterDiscountTotal.getText();

        double amount = 0.0;
        if (amountString != null && !amountString.isEmpty()) {
            amount = Double.parseDouble(amountString);
        } else {
            System.out.println("Amount input is empty.");
        }


        String dateString = this.placeOrderController.getDate().getText();
        Date date = Date.valueOf(dateString);

        String status = "Completed";
        String paymentMethod = cmbPayment.getValue();

        PaymentDto dto = new PaymentDto(pId,amount,date,status,paymentMethod);

        boolean result = paymentModel.addPayment(dto);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lbDate.setText(LocalDate.now().toString());

        loadPaymentMethods();

        txtDiscount.setOnAction(event -> calculateAfterdiscount());
        txtPayment.setOnAction(event -> calculateBalance());

        try {
            getNextPaymentId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void getNextPaymentId() throws SQLException {
        String paymentId = conformOrderModel.getNextPaymentId();
        labPaymentId.setText(paymentId);
    }

    void loadPaymentMethods(){
        ObservableList<String> paymentOptions = FXCollections.observableArrayList("Cash", "Credit", "Debit");
        cmbPayment.setItems(paymentOptions);
    }

    void calculateAfterdiscount(){
        if (txtDiscount.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Discount amount cannot be empty").show();
            return;
        }

        try {
            double totalAmount = Double.parseDouble(labTotalAmount.getText());
            double discount = Double.parseDouble(txtDiscount.getText());

            if (discount > totalAmount) {
                new Alert(Alert.AlertType.ERROR, "Discount amount cannot be greater than total amount").showAndWait();
                return;
            }

            double afterDiscount = totalAmount - discount;
            labAfterDiscountTotal.setText(String.valueOf(afterDiscount));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Discount amount must be a valid number").showAndWait();
            txtDiscount.clear();
            return;
        }

//        double totalAmount = Double.parseDouble(labTotalAmount.getText());
//        double discount = Double.parseDouble(txtDiscount.getText());
//
//
//        double afterDiscount = totalAmount - discount;
//
//        if (totalAmount < discount) {
//           new Alert(Alert.AlertType.ERROR, "Discount amount cannot be greater than total amount").show();
//        }
//
//        labAfterDiscountTotal.setText(String.valueOf(afterDiscount));
    }

    void calculateBalance(){
        if (txtPayment.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Payment amount cannot be empty").show();
            return;
        }

        try {
            int payment = Integer.parseInt(txtPayment.getText());

            double afterDiscount = Double.parseDouble(labAfterDiscountTotal.getText());
            double balance = payment - afterDiscount;

            if (balance < 0) {
                new Alert(Alert.AlertType.ERROR, "Payment amount cannot be greater than total amount").showAndWait();
                labBalance.setText("");
                txtPayment.setText("");
                return;
            }

            labBalance.setText(String.valueOf(balance));

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Payment amount must be a valid integer").showAndWait();
            txtPayment.clear();
            return;
        }

//        double afterDiscount = Double.parseDouble(labAfterDiscountTotal.getText());
//        double payment = Double.parseDouble(txtPayment.getText());
//
//        double balance = payment - afterDiscount;
//
//        labBalance.setText(String.valueOf(balance));
    }

    public void setPlaceOrderController(PlaceOrderController placeOrderController) {
        this.placeOrderController = placeOrderController;

        if (this.placeOrderController != null) {
            double totalAmount = this.placeOrderController.getTotal();
            labTotalAmount.setText(String.valueOf(totalAmount));
            System.out.println(labTotalAmount.getText());
        } else {
            System.out.println("placeOrderController is null");
        }
    }

    public void reset() throws SQLException {
        cmbPayment.setValue("");
        labPaymentId.setText("");
        labTotalAmount.setText("");
        txtDiscount.setText("");
        labAfterDiscountTotal.setText("");
        txtPayment.clear();
        labBalance.setText("");

        String paymentId = paymentModel.getNextPaymentId();
        labPaymentId.setText(paymentId);

        this.placeOrderController.reset();
    }
}
