package lk.ijse.gdse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String usId;
    private String role;
    private String password;
    private String username;
}
