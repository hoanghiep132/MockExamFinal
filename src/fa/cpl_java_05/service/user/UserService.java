package fa.cpl_java_05.service.user;

import fa.cpl_java_05.dao.IMPL.UserDAO;
import fa.cpl_java_05.model.user.UserModel;
import fa.cpl_java_05.service.book.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public UserModel findByUserNameAndPassWord(String userName, String passWord) {
        try{
            return userDAO.findByUserNameAndPassWord(userName,passWord);
        }catch (Exception ex){
            LOGGER.error("not found account : " + ex);
            return null;
        }
    }

}
