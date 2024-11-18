package lk.ijse.gdse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDto {
    private String cusId;
    private String cusName;
    private String email;
    private String contact;
    private String address;
}
