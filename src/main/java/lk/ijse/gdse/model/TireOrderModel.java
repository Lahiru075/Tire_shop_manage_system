package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.TireOrderDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class TireOrderModel {

    private final StockModel stockModel = new StockModel();

    public boolean saveTireOrder(ArrayList<TireOrderDto> tireOrderDtos) throws SQLException {

        for (TireOrderDto tireOrderDto : tireOrderDtos) {

            boolean isTireOrderSave = saveTireOrderDetails(tireOrderDto);

            if (!isTireOrderSave) {
                return false;
            }

            boolean isStockUpdate = stockModel.reduceQty(tireOrderDto);

            if (!isStockUpdate) {
                return false;
            }
        }

        return true;
    }

    public boolean saveTireOrderDetails(TireOrderDto tireOrderDto) throws SQLException {
        return CrudUtil.execute("insert into tire_order values (?,?,?,?,?,?)",
                tireOrderDto.getOrderId(),
                tireOrderDto.getTireId(),
                tireOrderDto.getDescription(),
                tireOrderDto.getPayment_method(),
                tireOrderDto.getQty(),
                tireOrderDto.getTotal_amount()
        );
    }
}
