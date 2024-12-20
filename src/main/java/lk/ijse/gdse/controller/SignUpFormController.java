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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {
    UserModel userModel = new UserModel();

    @FXML
    private AnchorPane mainAnchorpane;

    @FXML
    private Button butSignIn;

    @FXML
    private Label labUserId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtAdminPassword;

    @FXML
    private TextField txtAdminUserName;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    void butSignInOnAction(ActionEvent event) throws SQLException, IOException {
        String usId = labUserId.getText();
        String role = cmbRole.getValue();
        String password = txtPassword.getText();
        String username = txtUsername.getText();

        if (usId.isEmpty() || role == null || password.isEmpty() || username.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter usId , role , username and password").showAndWait();
            return;
        }

        String usernamePattern = "^[a-zA-Z0-9._-]{5,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";

        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);

        if (!isValidUsername) {
            new Alert(Alert.AlertType.ERROR, "Invalid Username").showAndWait();
            return;
        }

        if (!isValidPassword) {
            new Alert(Alert.AlertType.ERROR, "Invalid Password").showAndWait();
            return;
        }

        String adminUsername = "MainAdmin";
        String adminPassword = "password@1234";

        if (txtAdminUserName.getText().equals(adminUsername) && txtAdminPassword.getText().equals(adminPassword)) {
            boolean result = userModel.seveUser(new UserDto(usId, role, password, username));

            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Unsuccessful Sign in..!").showAndWait();
                return;
            }

            Stage currentStage = (Stage) mainAnchorpane.getScene().getWindow();
            currentStage.close();

            Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.setTitle("DashBoard");
            stage.setResizable(false);
            stage.show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Admin Username or Password is incorrect").showAndWait();
            txtAdminUserName.clear();
            txtAdminPassword.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String id = null;

        ObservableList<String> paymentOptions = FXCollections.observableArrayList("Admin", "Cashier");
        cmbRole.setItems(paymentOptions);

        try {
            id = userModel.getNextId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while fetching the next user ID. Please try again.").showAndWait();
        }
        labUserId.setText(id);
    }
}
