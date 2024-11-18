package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.PlaceOrderDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringJoiner;

public class PlaceOrderModel {
    public String getNextTireId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select tireId from tire order by tireId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("T%03d", newIdIndex);
        }
        return "T001";
    }

    public ArrayList<PlaceOrderDto> getAllTires() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from tire");

        ArrayList<PlaceOrderDto> placeOrderDTOS = new ArrayList<>();

        while (rst.next()){
            PlaceOrderDto placeOrderDto = new PlaceOrderDto();

            placeOrderDto.setTireId(rst.getString(1));
            placeOrderDto.setTireBrand(rst.getString(2));
            placeOrderDto.setTireModel(rst.getString(3));
            placeOrderDto.setTireSize(rst.getString(4));
            placeOrderDto.setYear(Integer.parseInt(rst.getString(5)));
            placeOrderDto.setTirePrice(Double.parseDouble(rst.getString(6)));


            placeOrderDTOS.add(placeOrderDto);
        }
        return placeOrderDTOS;
    }

    public int getQty(String tireId) throws SQLException {
        ResultSet rst1 = CrudUtil.execute("select * from stock where tireId = ?",tireId);

        int qty = 0;
        if(rst1.next()){
            qty = rst1.getInt(5);
        }
        return qty;
    }

    public PlaceOrderDto getTire(String tireId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from tire where tireId = ?",tireId);

        PlaceOrderDto placeOrderDto = new PlaceOrderDto();

        if(rst.next()){
            placeOrderDto.setTireId(rst.getString(1));
            placeOrderDto.setTireBrand(rst.getString(2));
            placeOrderDto.setTireModel(rst.getString(3));
            placeOrderDto.setTireSize(rst.getString(4));
            placeOrderDto.setYear(Integer.parseInt(rst.getString(5)));
            placeOrderDto.setTirePrice(Double.parseDouble(rst.getString(6)));
        }
        return placeOrderDto;
    }

    public ArrayList<PlaceOrderDto> getTireByBrand(String brand) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tire WHERE brand LIKE ?", "%" + brand + "%");

        ArrayList<PlaceOrderDto> placeOrderDTOS = new ArrayList<>();

        while (rst.next()){
            PlaceOrderDto placeOrderDto = new PlaceOrderDto();

            placeOrderDto.setTireId(rst.getString(1));
            placeOrderDto.setTireBrand(rst.getString(2));
            placeOrderDto.setTireModel(rst.getString(3));
            placeOrderDto.setTireSize(rst.getString(4));
            placeOrderDto.setYear(Integer.parseInt(rst.getString(5)));
            placeOrderDto.setTirePrice(Double.parseDouble(rst.getString(6)));

            placeOrderDTOS.add(placeOrderDto);
        }
        return placeOrderDTOS;
    }

    public String checkIsExists(String brand, String model, String size) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from tire where brand = ? and model = ? and size = ?", brand, model, size);

        String result = null;

        if (rst.next()) {
            result = rst.getString(1);
            System.out.println(rst.getString(1));
        }
        System.out.println(result);
        return result;

    }

    public boolean saveTire(PlaceOrderDto placeOrderDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO tire VALUES (?,?,?,?,?,?)",
                placeOrderDto.getTireId(),
                placeOrderDto.getTireBrand(),
                placeOrderDto.getTireModel(),
                placeOrderDto.getTireSize(),
                placeOrderDto.getYear(),
                placeOrderDto.getTirePrice()
        );
    }

    public boolean deleteTire(String tireId) throws SQLException {
        return CrudUtil.execute("delete from tire where tireId = ?", tireId);
    }

    public boolean updateTire(PlaceOrderDto placeOrderDto) throws SQLException {
        return CrudUtil.execute("update tire set brand = ?, model = ?, size = ?, year = ?, price = ? where tireId = ?",
                placeOrderDto.getTireBrand(),
                placeOrderDto.getTireModel(),
                placeOrderDto.getTireSize(),
                placeOrderDto.getYear(),
                placeOrderDto.getTirePrice(),
                placeOrderDto.getTireId()
        );
    }

    public ArrayList<String> getAllTireId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from tire");

        ArrayList<String> tireIds = new ArrayList<>();

        while(rst.next()){
            tireIds.add(rst.getString(1));
        }

        return tireIds;

    }
}
