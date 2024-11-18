package lk.ijse.gdse.dto.Tm;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StockTm {
    private String stockId;
    private String description;
    private String last_update;
    private int recode_level;
    private int qty;
    private String tireId;
}
