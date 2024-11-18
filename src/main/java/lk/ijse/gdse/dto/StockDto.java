package lk.ijse.gdse.dto;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StockDto {
    private String stockId;
    private String description;
    private String last_update;
    private int recode_level;
    private int qty;
    private String tireId;

}
