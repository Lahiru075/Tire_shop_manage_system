package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.CustomerDto;
import lk.ijse.gdse.dto.EmployeeDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public ArrayList<String> getAllEmployeesContact() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee");

        ArrayList<String> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            employeeDtos.add(rst.getString(6));
        }
        return employeeDtos;
    }


    public String getEmpId(String value) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT empId FROM employee WHERE coNo=?", value);

        if (resultSet != null && resultSet.next()) {
            return resultSet.getString("empId");
        }
        return null;
    }

    public String getNextEmployeeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select empId from employee order by empId desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee");

        ArrayList<EmployeeDto> employeeDTOS = new ArrayList<>();

        while (rst.next()) {
            EmployeeDto employeeDto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDouble(7)
            );
            employeeDTOS.add(employeeDto);
        }
        return employeeDTOS;
    }

    public boolean seveEmployee(EmployeeDto employeeDto) throws SQLException {
        return CrudUtil.execute("insert into employee values (?,?,?,?,?,?,?)",
                employeeDto.getEmpId(),
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getEmail(),
                employeeDto.getAddress(),
                employeeDto.getContact(),
                employeeDto.getSalary()
        );
    }

    public boolean deleteEmployee(String empId) throws SQLException {
        return CrudUtil.execute("delete from employee where empId=?", empId);
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        return CrudUtil.execute("update employee set name=?, role=?, email=?, address=?, coNo=?, salary=? where empId=?",
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getEmail(),
                employeeDto.getAddress(),
                employeeDto.getContact(),
                employeeDto.getSalary(),
                employeeDto.getEmpId()
        );
    }

    public ArrayList<String> getAllEmployeesId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee");
        ArrayList<String> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            employeeDtos.add(rst.getString(1));
        }
        return employeeDtos;
    }
}
