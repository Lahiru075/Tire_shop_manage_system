package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.StockDto;
import lk.ijse.gdse.dto.TireOrderDto;
import lk.ijse.gdse.dto.Tm.StockTm;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockModel {

    public ArrayList<StockDto> getAllStock() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from stock");

        ArrayList<StockDto> stockDtos = new ArrayList<>();

        while (rst.next()) {
            StockDto stockDto = new StockDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getString(6)
            );
            stockDtos.add(stockDto);
        }
        return stockDtos;
    }

    public boolean isSaved(StockDto stockDto) throws SQLException {
        return CrudUtil.execute("insert into stock values(?,?,?,?,?,?)",
                stockDto.getStockId(),
                stockDto.getDescription(),
                stockDto.getLast_update(),
                stockDto.getRecode_level(),
                stockDto.getQty(),
                stockDto.getTireId()
        );
    }

    public boolean isUpdate(StockDto stockDto) throws SQLException {
        return CrudUtil.execute("update stock set description = ?, last_update = ?, recode_level = ?, qty = ?, tireId = ? where stockId = ?",
                stockDto.getDescription(),
                stockDto.getLast_update(),
                stockDto.getRecode_level(),
                stockDto.getQty(),
                stockDto.getTireId(),
                stockDto.getStockId()
        );
    }

    public boolean deleteStock(String text) throws SQLException {
        return CrudUtil.execute("delete from stock where stockId = ?", text);
    }

    public String getNextStockId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select stockId from stock order by stockId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public boolean reduceQty(TireOrderDto tireOrderDto) throws SQLException {
        return CrudUtil.execute("update stock set qty = qty - ? where tireId = ?",
                tireOrderDto.getQty(),
                tireOrderDto.getTireId()
        );

    }

    public ArrayList<String> getAllStockId() throws SQLException {
        ArrayList<String> stockId = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("select * from stock");
        while (rst.next()) {
            stockId.add(rst.getString(1));
        }
        return stockId;
    }

    public boolean checkIsExists(String description) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from stock where description = ?", description);

        String desc = null;

        if (rst.next()){
            desc = rst.getString(2);
        }

        if (desc != null) {
            return true;
        }
        return false;
    }

    public String getStockId(String description) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from stock where description = ?", description);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public boolean updateStock(String getStockId, int qty, String date) throws SQLException {
        return CrudUtil.execute("update stock set qty = qty + ? , last_update = ? where stockId = ?", qty, date ,getStockId);
    }

    public String getRecodLevel(String tireId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from stock where tireId = ?", tireId);
        if (rst.next()) {
            return rst.getString(4);
        }
        return null;
    }
}
