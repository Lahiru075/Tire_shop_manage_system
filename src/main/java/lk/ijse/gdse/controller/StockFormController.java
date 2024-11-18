package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.dto.CustomerDto;
import lk.ijse.gdse.dto.StockDto;
import lk.ijse.gdse.dto.Tm.CustomerTm;
import lk.ijse.gdse.dto.Tm.StockTm;
import lk.ijse.gdse.model.PlaceOrderModel;
import lk.ijse.gdse.model.StockModel;
import lk.ijse.gdse.util.CrudUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockFormController implements Initializable {

    StockModel stockModel = new StockModel();
    PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button butGenearateReport;

    @FXML
    private TableColumn<StockTm, String> colDesc;

    @FXML
    private TableColumn<StockTm, String> colLastpdate;

    @FXML
    private TableColumn<StockTm, String> colLocation;

    @FXML
    private TableColumn<StockTm, String> colQty;

    @FXML
    private TableColumn<StockTm, String> colRecodeLevel;

    @FXML
    private TableColumn<StockTm, String> colStockId;

    @FXML
    private TableColumn<StockTm, String> colTireId;

    @FXML
    private Label lbStockId;

    @FXML
    private TableView<StockTm> tbStock;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtLastUpdate;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtRecodeLevel;

    @FXML
    private ComboBox<String> cmbTireId;

    @FXML
    void OnActionbutGenearateReport(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        boolean result = stockModel.deleteStock(lbStockId.getText());

        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Stock Delete Successful").show();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Stock Delete UnSuccessful").show();
            reset();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String stockId = lbStockId.getText();
        String description = txtDesc.getText();
        String last_update = txtLastUpdate.getText();
        String tireId = cmbTireId.getValue();

        if (stockId.isEmpty() || description.isEmpty() || last_update.isEmpty() || txtRecodeLevel.getText().isEmpty() || txtQty.getText().isEmpty() || tireId == null) {
            new Alert(Alert.AlertType.ERROR, "Missing Information").showAndWait();
            return;
        }

        int recode_level;
        int qty;

        try {
            recode_level = Integer.parseInt(txtRecodeLevel.getText());
            qty = Integer.parseInt(txtQty.getText());
        }catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "recode_level or qty Invalid Input").showAndWait();
            return;
        }

        String descPattern = "^[a-zA-Z0-9\\s/-]{5,255}$";
        String lastUpdatePatten = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$";
        String recodeLevelPatten = "^[0-9]{1,3}$";
        String qtyPatten = "^[0-9]{1,3}$";

        boolean isValidDesc = description.matches(descPattern);
        boolean isValidLastUpdate = last_update.matches(lastUpdatePatten);
        boolean isValidRecodeLevel = txtRecodeLevel.getText().matches(recodeLevelPatten);
        boolean isValidQty = txtQty.getText().matches(qtyPatten);

        if (!isValidDesc) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description").showAndWait();
            return;
        }
        if (!isValidLastUpdate) {
            new Alert(Alert.AlertType.ERROR, "Invalid Last Update").showAndWait();
            return;
        }
        if (!isValidRecodeLevel) {
            new Alert(Alert.AlertType.ERROR, "Invalid Recode Level").showAndWait();
            return;
        }
        if (!isValidQty) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity").showAndWait();
            return;
        }

        boolean isSaved = stockModel.isSaved(new StockDto(
                stockId,
                description,
                last_update,
                recode_level,
                qty,
                tireId)
        );
        
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Stock Save Successful").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Stock Save UnSuccessful").showAndWait();
            reset();
        }
    }
    
    void reset() throws SQLException {

        getAllStock();
        getNextStockId();

        cmbTireId.setValue("");
        txtDesc.setText("");
        txtLastUpdate.setText("");
        txtRecodeLevel.setText("");
        txtQty.setText("");

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String stockId = lbStockId.getText();
        String description = txtDesc.getText();
        String last_update = txtLastUpdate.getText();
        String tireId = cmbTireId.getValue();

        if (stockId.isEmpty() || description.isEmpty() || last_update.isEmpty() || txtRecodeLevel.getText().isEmpty() || txtQty.getText().isEmpty() || tireId == null) {
            new Alert(Alert.AlertType.ERROR, "Missing Information").showAndWait();
            return;
        }

        int recode_level;
        int qty;

        try {
            recode_level = Integer.parseInt(txtRecodeLevel.getText());
            qty = Integer.parseInt(txtQty.getText());
        }catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "recode_level or qty Invalid Input").showAndWait();
            return;
        }

        String descPattern = "^[a-zA-Z0-9\\s/-]{5,255}$";
        String lastUpdatePatten = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$";
        String recodeLevelPatten = "^[0-9]{1,3}$";
        String qtyPatten = "^[0-9]{1,3}$";

        boolean isValidDesc = description.matches(descPattern);
        boolean isValidLastUpdate = last_update.matches(lastUpdatePatten);
        boolean isValidRecodeLevel = txtRecodeLevel.getText().matches(recodeLevelPatten);
        boolean isValidQty = txtQty.getText().matches(qtyPatten);

        if (!isValidDesc) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description").showAndWait();
            return;
        }
        if (!isValidLastUpdate) {
            new Alert(Alert.AlertType.ERROR, "Invalid Last Update").showAndWait();
            return;
        }
        if (!isValidRecodeLevel) {
            new Alert(Alert.AlertType.ERROR, "Invalid Recode Level").showAndWait();
            return;
        }
        if (!isValidQty) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity").showAndWait();
            return;
        }

        boolean isUpdate = stockModel.isUpdate(new StockDto(
                stockId,
                description,
                last_update,
                recode_level,
                qty,
                tireId)
        );

        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Stock update Successful").showAndWait();
            reset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Stock update UnSuccessful").showAndWait();
            reset();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        StockTm stockTm  = tbStock.getSelectionModel().getSelectedItem();
        if (stockTm != null) {
            lbStockId.setText(stockTm.getStockId());
            txtDesc.setText(stockTm.getDescription());
            txtLastUpdate.setText(stockTm.getLast_update());
            txtRecodeLevel.setText(String.valueOf(stockTm.getRecode_level()));
            txtQty.setText(String.valueOf(stockTm.getQty()));
            cmbTireId.setValue(stockTm.getTireId());
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        reset();
    }

    void getAllStock() throws SQLException {
        ArrayList<StockDto> stockDTOS = stockModel.getAllStock();

        ObservableList<StockTm> stockTms = FXCollections.observableArrayList();

        for (StockDto stockDto : stockDTOS) {
            StockTm stockTm = new StockTm(
                    stockDto.getStockId(),
                    stockDto.getDescription(),
                    stockDto.getLast_update(),
                    stockDto.getRecode_level(),
                    stockDto.getQty(),
                    stockDto.getTireId()
            );
            stockTms.add(stockTm);
        }

        tbStock.setItems(stockTms);
    }

    void getNextStockId () throws SQLException {
        String stockId = stockModel.getNextStockId();
        lbStockId.setText(stockId);
    }

    void loadTireId() throws SQLException {
        ArrayList<String> tireId = placeOrderModel.getAllTireId();
        ObservableList<String> tireIds = FXCollections.observableArrayList(tireId);
        cmbTireId.setItems(tireIds);
    }
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLastpdate.setCellValueFactory(new PropertyValueFactory<>("last_update"));
        colRecodeLevel.setCellValueFactory(new PropertyValueFactory<>("recode_level"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTireId.setCellValueFactory(new PropertyValueFactory<>("tireId"));

        try {
            reset();
            loadTireId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
