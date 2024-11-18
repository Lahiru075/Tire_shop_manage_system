package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.CustomerDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public String getNextCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select custId from customer order by custId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    public ArrayList<CustomerDto> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDtO = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDTOS.add(customerDtO);
        }
        return customerDTOS;
    }

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {

        return CrudUtil.execute("insert into customer values (?,?,?,?,?)",
                customerDto.getCusId(),
                customerDto.getCusName(),
                customerDto.getEmail(),
                customerDto.getContact(),
                customerDto.getAddress()
        );
    }

    public boolean deleteCustomer(String custId) throws SQLException {
        return CrudUtil.execute("delete from customer where custId=?", custId);
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return CrudUtil.execute("update customer set name=?, email=?, coNo=?, address=? where custId=?",
                customerDto.getCusName(),
                customerDto.getEmail(),
                customerDto.getContact(),
                customerDto.getAddress(),
                customerDto.getCusId()
        );
    }

    public boolean checkCustomer(String contact) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where coNo=?", contact);

        if (rst.next()) {
            return true;
        }

        return false;
    }

    public String getCustomerContactNo() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer order by custId desc limit 1");

        if (rst.next()) {
            return rst.getString(4);
        }
        return null;
    }

    public String getCustId(String text) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where coNo=?", text);

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public ArrayList<String> getAllCustIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");
        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }

    public CustomerDto getCustomer(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where custId = ?",id);

        if (rst.next()){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setCusId(rst.getString(1));
            customerDto.setCusName(rst.getString(2));
            customerDto.setEmail(rst.getString(3));
            customerDto.setContact(rst.getString(4));
            customerDto.setAddress(rst.getString(5));

            return customerDto;
        }
        return null;
    }
}
