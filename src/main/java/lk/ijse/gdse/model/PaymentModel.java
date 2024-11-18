package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.PaymentDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select pId from payment order by pId desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    public boolean addPayment(PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute("insert into payment values (?,?,?,?,?)",
                paymentDto.getPId(),
                paymentDto.getAmount(),
                paymentDto.getDate(),
                paymentDto.getStatus(),
                paymentDto.getPaymentMethod()
        );
    }
}
