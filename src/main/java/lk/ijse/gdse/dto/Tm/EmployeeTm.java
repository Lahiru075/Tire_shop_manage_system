package lk.ijse.gdse.dto.Tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class EmployeeTm {
    private String empId;
    private String name;
    private String role;
    private String email;
    private String address;
    private String contact;
    private double salary;
}
