package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.gdse.dto.CustomerDto;
import lk.ijse.gdse.model.CustomerModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendEmailController implements Initializable {

    CustomerModel customerModel = new CustomerModel();

    @FXML
    private ComboBox<String> cmbCustId;

    @FXML
    private Label labCustName;

    @FXML
    private Label labEmail;

    @FXML
    private TextField txtBody;

    @FXML
    private TextField txtSubject;

    @FXML
    void butSendOnAction(ActionEvent event) throws MessagingException {
        String recipientEmail = labEmail.getText();
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (recipientEmail.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields before sending email!").show();
            return;
        }

        final String FROM_EMAIL = "sanjeewalahiru057@gmail.com";
        final String PASSWORD = "tyni mxyw bcgi hywr";


        sendEmailWithGmail(FROM_EMAIL, PASSWORD, recipientEmail,subject,body);

    }

    private void sendEmailWithGmail(String fromEmail, String password, String toEmail, String subject, String messageBody) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = prepareMassage(session, fromEmail, toEmail, subject, messageBody);
        if (message != null) {
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully..!").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Email send unsuccessfully..!").showAndWait();
        }
    }

    private Message prepareMassage(Session session, String fromEmail, String toEmail, String subject, String messageBody) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(toEmail)});
            message.setSubject(subject);
            message.setText(messageBody);

            return message;
        } catch (MessagingException e) {
            Logger.getLogger(SendEmailController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private void CustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (String id : customerIds) {
            observableList.addAll(id);
        }
        cmbCustId.setItems(observableList);
    }

    @FXML
    void cmbOnAction(ActionEvent event) {
        String id = cmbCustId.getValue();

        try {
            CustomerDto customerDto = customerModel.getCustomer(id);
            labCustName.setText(customerDto.getCusName());
            labEmail.setText(customerDto.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CustomerIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
