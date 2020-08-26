package fa.cpl_java_05.app.controller.login;

import fa.cpl_java_05.dao.IMPL.UserDAO;
import fa.cpl_java_05.model.user.UserModel;
import fa.cpl_java_05.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class LoginController implements Serializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;


    @FXML
    public void submit(ActionEvent actionEvent){
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        UserModel userModel = new UserDAO().findByUserNameAndPassWord(username,password);
        if(userModel != null){
            UserSession.setInstance(userModel);
            Parent root;
            try {
                root =  FXMLLoader.load(getClass().getResource("/fa/cpl_java_05/app/views/main_window/user/user_window.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                // Hide this current window (if this is what you want)
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
