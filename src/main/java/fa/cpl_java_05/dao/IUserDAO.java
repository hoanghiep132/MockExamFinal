package fa.cpl_java_05.dao;


import fa.cpl_java_05.dao.GenericDAO;
import fa.cpl_java_05.model.user.UserModel;

import java.util.List;

/**
 * Created by Ngô Đức Nam on 26-08-2020, 2020, 1:20 PM
 */
public interface IUserDAO extends GenericDAO<UserModel> {

    UserModel findByUserNameAndPassWord(String userName, String passWord);
    List<UserModel> findByID(int id);
    List<UserModel> findAllUser();
}
