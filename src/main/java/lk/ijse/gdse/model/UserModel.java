package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.CustomerDto;
import lk.ijse.gdse.dto.Tm.UserTm;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select usId from user order by usId desc limit 1");
        if (rst.next()){
            String usId = rst.getString(1);
            String subString = usId.substring(1);
            int value = Integer.parseInt(subString);
            int newIdIndex = value + 1; // Increment the number by 1
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    public boolean seveUser(UserDto userDto) throws SQLException {
        return CrudUtil.execute("insert into user values (?,?,?,?)",
                userDto.getUsId(),
                userDto.getRole(),
                userDto.getPassword(),
                userDto.getUsername()
        );
    }

    public ArrayList<UserDto> getAllUsers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from user");

        ArrayList<UserDto> userDtos = new ArrayList<>();

        while (rst.next()) {
            UserDto userDto = new UserDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public boolean updateUser(UserDto userDto) throws SQLException {
        return CrudUtil.execute("update user set role = ?, username = ?, password = ? where usId = ?",
                userDto.getRole(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getUsId()
        );
    }

    public boolean deleteUser(String userId) throws SQLException {
        return CrudUtil.execute("delete from user where usId = ?", userId);
    }
}
