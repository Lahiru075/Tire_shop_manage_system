package lk.ijse.gdse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderViewDto {
    private String orderId;
    private String date;
    private String custId;
    private String empId;
    private String tireId;
    private String description;
    private String payment_method;
    private int qty;
    private double total_amount;
}
