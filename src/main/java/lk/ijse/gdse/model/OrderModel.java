package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.OrdersDto;
import lk.ijse.gdse.dto.TireOrderDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderModel {
    private final TireOrderModel tireOrderModel = new TireOrderModel();

    public  boolean saveOrder(OrdersDto ordersDto, ArrayList<TireOrderDto> tireOrderDtos) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isOrderSave = CrudUtil.execute("insert into orders values(?,?,?,?)",
                    ordersDto.getOrderId(),
                    ordersDto.getDate(),
                    ordersDto.getCustId(),
                    ordersDto.getEmpId()
            );

            if (isOrderSave){
                System.out.println("Hi1");
                boolean isOrderDetailSave = tireOrderModel.saveTireOrder(tireOrderDtos);

                if (isOrderDetailSave){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        }catch (Exception e){
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select orderId from orders order by orderId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("O%03d", newIdIndex);
        }
        return "O001";
    }
}
