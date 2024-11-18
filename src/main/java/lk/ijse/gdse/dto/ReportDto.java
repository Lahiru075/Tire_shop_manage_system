package lk.ijse.gdse.dto;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDto {
    private String paymentId;
    private double paymentAmount;
    private String date;
    private String paymentStatus;
    private String discountId;
    private double discountAmount;
    private String paymentMethod;
}
