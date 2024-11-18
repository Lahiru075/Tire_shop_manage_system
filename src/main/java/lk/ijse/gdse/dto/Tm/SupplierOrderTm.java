package lk.ijse.gdse.dto.Tm;

import lombok.*;
import java.sql.Date;
import javafx.scene.control.Button;


@AllArgsConstructor
@Setter
@NoArgsConstructor
@ToString
@Getter
public class SupplierOrderTm {
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

}
