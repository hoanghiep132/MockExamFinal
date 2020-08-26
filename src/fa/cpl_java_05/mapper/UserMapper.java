package fa.cpl_java_05.mapper;

import fa.cpl_java_05.model.user.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:08 PM
 */
public class UserMapper implements RowMapper<UserModel> {

    @Override
    public UserModel mapRow(ResultSet rs) {
        try {
            UserModel userModel = new UserModel();
            userModel.setId(rs.getInt("user_id"));
            userModel.setUsername(rs.getString("user_name"));
            userModel.setPassword(rs.getString("password"));
            userModel.setDeleted(rs.getBoolean("deleted"));
            userModel.setRole(rs.getBoolean("Role"));

            return userModel;
        }catch (SQLException e){
            return null;
        }
    }
}
