module lk.ijse.gdse.tire_shop_manage_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires java.desktop;
    requires java.mail;
    requires net.sf.jasperreports.core;

    opens lk.ijse.gdse.dto.Tm to javafx.base;
    opens lk.ijse.gdse.controller to javafx.fxml;
    exports lk.ijse.gdse;
}