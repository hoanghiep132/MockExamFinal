package fa.cpl_java_05.service.user;

import fa.cpl_java_05.dao.IMPL.UserDAO;
import fa.cpl_java_05.model.user.UserModel;

import java.util.logging.Level;
import java.util.logging.Logger;


public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public UserModel findByUserNameAndPassWord(String userName, String passWord) {
        try{
            return userDAO.findByUserNameAndPassWord(userName,passWord);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE,"not found account : " + ex);
            return null;
        }
    }

}
