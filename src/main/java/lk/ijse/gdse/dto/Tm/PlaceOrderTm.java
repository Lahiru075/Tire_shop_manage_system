package lk.ijse.gdse.dto.Tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceOrderTm {
    private String tireId;
    private String tireBrand;
    private String tireModel;
    private String tireSize;
    private int year;
    private double tirePrice;
}
