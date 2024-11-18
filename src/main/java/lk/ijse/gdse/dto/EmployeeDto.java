package lk.ijse.gdse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class EmployeeDto {
    private String empId;
    private String name;
    private String role;
    private String email;
    private String address;
    private String contact;
    private double salary;
}
