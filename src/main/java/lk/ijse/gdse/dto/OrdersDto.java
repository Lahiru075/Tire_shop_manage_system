package lk.ijse.gdse.dto;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrdersDto {
    private String orderId;
    private String date;
    private String custId;
    private String empId;

}
