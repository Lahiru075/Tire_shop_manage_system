package lk.ijse.gdse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceOrderDto {
    private String tireId;
    private String tireBrand;
    private String tireModel;
    private String tireSize;
    private int year;
    private double tirePrice;
}
