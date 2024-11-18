package lk.ijse.gdse.dto;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    private String discId;
    private double amount;
    private Date date;
    private String paymentId;
}
