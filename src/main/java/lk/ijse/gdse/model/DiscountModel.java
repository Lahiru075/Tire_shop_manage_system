package lk.ijse.gdse.model;


import lk.ijse.gdse.dto.DiscountDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountModel {

    public String getNextDiscId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select descId from discount order by descId desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }
        return "D001";
    }

    public boolean addDiscount(DiscountDto discountDto) throws SQLException {
        return CrudUtil.execute("insert into discount values (?,?,?,?)",
                discountDto.getDiscId(),
                discountDto.getAmount(),
                discountDto.getDate(),
                discountDto.getPaymentId()
        );
    }
}
