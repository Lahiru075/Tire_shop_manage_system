package lk.ijse.gdse.model;

import javafx.scene.control.Alert;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.StockDto;
import lk.ijse.gdse.dto.SupplierOrderDto;
import lk.ijse.gdse.dto.TireOrderDto;
import lk.ijse.gdse.dto.Tm.StockTm;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SupplierOrderModel {

    StockModel stockModel = new StockModel();
    PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supOrderId from supplier_order order by supOrderId desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("SO%03d", newIdIndex);
        }
        return "SO001";
    }

    public boolean saveOrder(ArrayList<SupplierOrderDto> supplierOrderDtos) throws SQLException {
        for (SupplierOrderDto supplierOrderDto : supplierOrderDtos){
            boolean isOrderSave = saveSupplierOrder(supplierOrderDto);

            if (!isOrderSave){
                return false;
            }
        }
        return true;
    }

    public boolean saveSupplierOrder(SupplierOrderDto supplierOrderDto) throws SQLException {
        return CrudUtil.execute("insert into supplier_order values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                supplierOrderDto.getOrderId(),
                supplierOrderDto.getSupId(),
                supplierOrderDto.getEmployeeId(),
                supplierOrderDto.getStockId(),
                supplierOrderDto.getTireModel(),
                supplierOrderDto.getQty(),
                supplierOrderDto.getOrderDate(),
                supplierOrderDto.getRequestDate(),
                supplierOrderDto.getTotal(),
                supplierOrderDto.getTireBrand(),
                supplierOrderDto.getYear(),
                supplierOrderDto.getOrderSize(),
                supplierOrderDto.getOrderStatus()
        );
    }

    public ArrayList<SupplierOrderDto> getAllSupplierOrders() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier_order");

        ArrayList<SupplierOrderDto> supplierOrderDtos = new ArrayList<>();

        while (rst.next()){
            SupplierOrderDto supplierOrderDto = new SupplierOrderDto();

            supplierOrderDto.setOrderId(rst.getString(1));
            supplierOrderDto.setSupId(rst.getString(2));
            supplierOrderDto.setEmployeeId(rst.getString(3));
            supplierOrderDto.setStockId(rst.getString(4));
            supplierOrderDto.setTireModel(rst.getString(5));
            supplierOrderDto.setQty(rst.getInt(6));
            supplierOrderDto.setOrderDate(rst.getDate(7));
            supplierOrderDto.setRequestDate(rst.getDate(8));
            supplierOrderDto.setTotal(rst.getDouble(9));
            supplierOrderDto.setTireBrand(rst.getString(10));
            supplierOrderDto.setYear(rst.getInt(11));
            supplierOrderDto.setOrderSize(rst.getString(12));
            supplierOrderDto.setOrderStatus(rst.getString(13));

            supplierOrderDtos.add(supplierOrderDto);
        }
        return supplierOrderDtos;
    }

    public boolean isUpdate(SupplierOrderDto supplierOrderDto) throws SQLException {
        return CrudUtil.execute("UPDATE supplier_order SET supId=?, empId=? , tireModel=?, qty=?, order_date=?, request_date=?, total_amount=?, tire_brand=?, year=?, size=?, order_status=? WHERE supOrderId=? AND stockId=?",
                supplierOrderDto.getSupId(),
                supplierOrderDto.getEmployeeId(),
                supplierOrderDto.getTireModel(),
                supplierOrderDto.getQty(),
                supplierOrderDto.getOrderDate(),
                supplierOrderDto.getRequestDate(),
                supplierOrderDto.getTotal(),
                supplierOrderDto.getTireBrand(),
                supplierOrderDto.getYear(),
                supplierOrderDto.getOrderSize(),
                supplierOrderDto.getOrderStatus(),
                supplierOrderDto.getOrderId(),
                supplierOrderDto.getStockId()
        );

    }

    public boolean deleteSupplierOrder(String orderId, String stockId) throws SQLException {
        return CrudUtil.execute("delete from supplier_order where supOrderId = ? and stockId = ?", orderId, stockId);
    }

    public boolean addSupplierOrder(SupplierOrderDto supplierOrderDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            boolean isUpdate = CrudUtil.execute("UPDATE supplier_order SET order_status = ? WHERE supOrderId=? AND stockId=?",
                    "Completed",
                    supplierOrderDto.getOrderId(),
                    supplierOrderDto.getStockId()
            );

            if (isUpdate){
                String description = supplierOrderDto.getTireBrand() + " " + supplierOrderDto.getTireModel() + " " + supplierOrderDto.getOrderSize();

                boolean isTireIsExists = stockModel.checkIsExists(description);

                if (isTireIsExists) {
                    String getStockId = stockModel.getStockId(description);

                    String date = LocalDate.now().toString();

                    boolean isStockUpdate = stockModel.updateStock(getStockId, supplierOrderDto.getQty(), date);

                    if (isStockUpdate){
                        connection.commit();
                        return true;
                    }
                }

                if (!isTireIsExists) {
                    String brand = supplierOrderDto.getTireBrand();
                    String model = supplierOrderDto.getTireModel();
                    String size = supplierOrderDto.getOrderSize();

                    String stockId = stockModel.getNextStockId();
                    String tireId = placeOrderModel.checkIsExists(brand, model, size);

                    if (tireId != null) {

                        StockDto stockDto = new StockDto();
                        stockDto.setStockId(stockId);
                        stockDto.setDescription(description);
                        stockDto.setLast_update(LocalDate.now().toString());
                        stockDto.setRecode_level(50);
                        stockDto.setQty(supplierOrderDto.getQty());
                        stockDto.setTireId(tireId);

                        boolean isStockSave = stockModel.isSaved(stockDto);

                        if (isStockSave){
                            connection.commit();
                            return true;
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Please add tire your tire table..").showAndWait();
                    }
                }
            }

            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}