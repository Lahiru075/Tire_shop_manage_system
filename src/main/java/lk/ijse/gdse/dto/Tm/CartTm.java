package lk.ijse.gdse.dto.Tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartTm {
    private String tireId;
    private int qty;
    private String desc;
    private double price;
    private Button butRemove;
}
