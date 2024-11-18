package lk.ijse.gdse.dto.Tm;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportTm {
    private String paymentId;
    private double paymentAmount;
    private String date;
    private String paymentStatus;
    private String discountId;
    private double discountAmount;
    private String paymentMethod;
}
