package lk.ijse.gdse.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    private String pId;
    private double amount;
    private Date date;
    private String status;
    private String paymentMethod;

}
