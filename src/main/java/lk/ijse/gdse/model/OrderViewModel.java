package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.OrderViewDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderViewModel {
    public ArrayList<OrderViewDto> getAllOrders() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT o.orderId, o.date,  o.custId, o.empId, t.tireId,  t.description, t.payment_method, t.qty,  t.total_amount FROM   orders o JOIN tire_order t ON o.orderId = t.orderId");

        ArrayList<OrderViewDto> orderViewDTOS = new ArrayList<>();
        while (rst.next()) {
            OrderViewDto orderViewDto = new OrderViewDto();
            orderViewDto.setOrderId(rst.getString(1));
            orderViewDto.setDate(rst.getString(2));
            orderViewDto.setCustId(rst.getString(3));
            orderViewDto.setEmpId(rst.getString(4));
            orderViewDto.setTireId(rst.getString(5));
            orderViewDto.setDescription(rst.getString(6));
            orderViewDto.setPayment_method(rst.getString(7));
            orderViewDto.setQty(rst.getInt(8));
            orderViewDto.setTotal_amount(rst.getDouble(9));

            orderViewDTOS.add(orderViewDto);
        }
        return orderViewDTOS;
    }

    public ArrayList<OrderViewDto> searchByCustId(String custId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT o.orderId, o.date,  o.custId, o.empId, t.tireId,  t.description, t.payment_method, t.qty,  t.total_amount FROM   orders o JOIN tire_order t ON o.orderId = t.orderId WHERE o.custId = ?", custId);

        ArrayList<OrderViewDto> orderViewDTOS = new ArrayList<>();

        while (rst.next()) {
            OrderViewDto orderViewDto = new OrderViewDto();
            orderViewDto.setOrderId(rst.getString(1));
            orderViewDto.setDate(rst.getString(2));
            orderViewDto.setCustId(rst.getString(3));
            orderViewDto.setEmpId(rst.getString(4));
            orderViewDto.setTireId(rst.getString(5));
            orderViewDto.setDescription(rst.getString(6));
            orderViewDto.setPayment_method(rst.getString(7));
            orderViewDto.setQty(rst.getInt(8));
            orderViewDto.setTotal_amount(rst.getDouble(9));

            orderViewDTOS.add(orderViewDto);
        }
        return orderViewDTOS;
    }

    public ArrayList<OrderViewDto> searchByDay(String day1, String day2) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT o.orderId, o.date,  o.custId, o.empId, t.tireId,  t.description, t.payment_method, t.qty,  t.total_amount FROM   orders o JOIN tire_order t ON o.orderId = t.orderId WHERE o.date BETWEEN ? AND ?",day1,day2);

        ArrayList<OrderViewDto> orderViewDTOS = new ArrayList<>();

        while (rst.next()) {
            OrderViewDto orderViewDto = new OrderViewDto();
            orderViewDto.setOrderId(rst.getString(1));
            orderViewDto.setDate(rst.getString(2));
            orderViewDto.setCustId(rst.getString(3));
            orderViewDto.setEmpId(rst.getString(4));
            orderViewDto.setTireId(rst.getString(5));
            orderViewDto.setDescription(rst.getString(6));
            orderViewDto.setPayment_method(rst.getString(7));
            orderViewDto.setQty(rst.getInt(8));
            orderViewDto.setTotal_amount(rst.getDouble(9));

            orderViewDTOS.add(orderViewDto);
        }
        return orderViewDTOS;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }
}
