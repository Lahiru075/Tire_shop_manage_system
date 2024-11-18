package lk.ijse.gdse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.dto.UserDto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController {


    @FXML
    private Button butCustomerManage;

    @FXML
    private Button butEmployeeManage;

    @FXML
    private Button butLogOut;

    @FXML
    private Button butOrdersView;

    @FXML
    private Button butPlaceOrder;

    @FXML
    private Button butReport;

    @FXML
    private Button butSendEmail;

    @FXML
    private Button butStockManage;

    @FXML
    private Button butSupplierManage;

    @FXML
    private Button butSupplierOrderManage;

    @FXML
    private Button butTiresManage;

    @FXML
    private Button butUserManage;

    @FXML
    private AnchorPane changeAnchorPane;

    @FXML
    private AnchorPane mainAnchorPane;


    public void initializeDashBoard(UserDto userDto){
         if (userDto.getRole() != null && userDto.getRole().toLowerCase().equals("cashier")) {
             System.out.println(userDto.getRole());

             butPlaceOrder.setDisable(false);
             butLogOut.setDisable(false);

             butCustomerManage.setDisable(true);
             butEmployeeManage.setDisable(true);
             butOrdersView.setDisable(true);
             butStockManage.setDisable(true);
             butSupplierManage.setDisable(true);
             butUserManage.setDisable(true);
             butSupplierOrderManage.setDisable(true);
             butTiresManage.setDisable(true);
             butSendEmail.setDisable(true);
             butReport.setDisable(true);

         }
    }

    @FXML
    void butCustomerManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/CustomerForm.fxml");
    }

    @FXML
    void butEmployeeManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/EmployeeForm.fxml");
    }

    @FXML
    void butOrdersViewOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/ViewOrders.fxml");
    }

    @FXML
    void butPlaceOrderOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/PlaceOrder.fxml");
    }

    @FXML
    void butStockManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/StockForm.fxml");
    }

    @FXML
    void butSupplierManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/SupplierForm.fxml");
    }

    @FXML
    void butUserManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/UserForm.fxml");
    }

    @FXML
    void butReportOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/ReportForm.fxml");
    }

    @FXML
    void butSupplierOrderManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/SupplierOrderForm.fxml");
    }

    @FXML
    void butTiresManageOnAction(ActionEvent event) throws IOException {
        navigateToAnchorPane("/view/TiresForm.fxml");
    }

    @FXML
    void butSendEmailOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/SendEmail.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(load);
        stage.setScene(scene);
        Image image = new Image(getClass().getResourceAsStream("/images/tire.png"));
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void butLogOutOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) mainAnchorPane.getScene().getWindow();
        currentStage.close();

        Parent load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        Image image = new Image(getClass().getResourceAsStream("/images/tire.png"));
        stage.getIcons().add(image);
        stage.setTitle("Login Form");
        stage.setResizable(false);
        stage.show();
    }

    void initialize(UserDto userDto) {
        initializeDashBoard(userDto);
    }

    private void navigateToAnchorPane(String path) throws IOException {
        changeAnchorPane.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
        changeAnchorPane.getChildren().add(anchorPane);
    }

}
