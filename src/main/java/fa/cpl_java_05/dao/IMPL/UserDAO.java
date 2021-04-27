package fa.cpl_java_05.dao.IMPL;


import fa.cpl_java_05.dao.IBookCaseDAO;
import fa.cpl_java_05.dao.IUserDAO;
import fa.cpl_java_05.mapper.UserMapper;
import fa.cpl_java_05.model.user.UserModel;

import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:24 PM
 */
public class UserDAO extends AbstractDAO<UserModel> implements IUserDAO {
    @Override
    public UserModel findByUserNameAndPassWord(String userName, String passWord) {
        StringBuilder sql = new StringBuilder("SELECT * FROM User AS u");
        sql.append(" WHERE user_name = ? AND password = ? AND deleted = false");
        List<UserModel> users = query(sql.toString(), new UserMapper(), userName, passWord);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<UserModel> findByID(int id) {
        String sql = "SELECT * FROM User WHERE user_id=? and deleted = false";
        return query(sql, new UserMapper(), id);
    }

    @Override
    public List<UserModel> findAllUser() {
        String sql="SELECT * FROM User WHERE deleted = false";
        return query(sql, new UserMapper());
    }
}
