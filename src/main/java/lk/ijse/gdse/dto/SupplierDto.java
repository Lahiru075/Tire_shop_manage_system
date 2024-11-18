package lk.ijse.gdse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SupplierDto {
    private String supId;
    private String name;
    private String email;
    private String contact;
    private String address;
}
