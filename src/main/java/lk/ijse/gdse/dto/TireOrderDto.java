package lk.ijse.gdse.dto;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TireOrderDto {
    private String orderId;
    private String tireId;
    private String description;
    private String payment_method;
    private int qty;
    private double total_amount;
}
