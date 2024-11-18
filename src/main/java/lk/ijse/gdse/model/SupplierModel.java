package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.EmployeeDto;
import lk.ijse.gdse.dto.SupplierDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supId from supplier order by supId desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

        ArrayList<SupplierDto> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            supplierDTOS.add(supplierDto);
        }
        return supplierDTOS;
    }

    public boolean seveSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("insert into supplier values (?,?,?,?,?)",
                supplierDto.getSupId(),
                supplierDto.getName(),
                supplierDto.getEmail(),
                supplierDto.getContact(),
                supplierDto.getAddress()
        );
    }

    public boolean deleteSupplier(String supId) throws SQLException {
        return CrudUtil.execute("delete from supplier where supId=?", supId);
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("update supplier set name=?, email=?, coNo=?, address=? where supId=?",
                supplierDto.getName(),
                supplierDto.getEmail(),
                supplierDto.getContact(),
                supplierDto.getAddress(),
                supplierDto.getSupId()
        );
    }

    public ArrayList<String> getAllSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }
        return supplierIds;
    }
}
