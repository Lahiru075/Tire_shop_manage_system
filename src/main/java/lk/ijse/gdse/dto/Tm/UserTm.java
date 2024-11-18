package lk.ijse.gdse.dto.Tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTm {
    private String usId;
    private String role;
    private String password;
    private String username;
}
