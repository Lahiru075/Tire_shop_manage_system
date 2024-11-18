package lk.ijse.gdse.dto;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierOrderDto {
    private String orderId;
    private String supId;
    private String employeeId;
    private String stockId;
    private String tireModel;
    private int qty;
    private Date orderDate;
    private Date requestDate;
    private double total;
    private String tireBrand;
    private int year;
    private String orderSize;
    private String orderStatus;
    private Button remove;

    public SupplierOrderDto(String orderId, String stockId, String supId, String employeeId, Date orderDate, int year, Date requestDate, String model, String brand, String status, String orderSize, double total, int qty) {
        this.orderId = orderId;
        this.stockId = stockId;
        this.supId = supId;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
        this.year = year;
        this.requestDate = requestDate;
        this.tireModel = model;
        this.tireBrand = brand;
        this.orderStatus = status;
        this.orderSize = orderSize;
        this.total = total;
        this.qty = qty;
    }
}
