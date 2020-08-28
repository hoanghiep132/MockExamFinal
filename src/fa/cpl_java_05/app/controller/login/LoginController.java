package fa.cpl_java_05.app.controller.login;

import fa.cpl_java_05.app.main.Main;
import fa.cpl_java_05.app.views.common.AlertBox;
import fa.cpl_java_05.dao.IMPL.UserDAO;
import fa.cpl_java_05.model.user.UserModel;
import fa.cpl_java_05.service.user.UserService;
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
    public void cancel(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


    @FXML
    public void submit(ActionEvent actionEvent){
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if(password.length() < 6 || password.length() > 16){
            AlertBox.display("Warning","Password length must be between 6 an 18 character");
        }else {
            UserModel userModel = new UserService().findByUserNameAndPassWord(username,password);
            if(userModel != null){
                UserSession.setInstance(userModel);
                Parent root;
                try {
                    root =  FXMLLoader.load(getClass().getResource("/fa/cpl_java_05/app/views/main_window/user/user_window.fxml"));
                    Scene scene = new Scene(root);
                    Main.mainStage.close();
                    Main.mainStage.setScene(scene);
                    Main.mainStage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                AlertBox.display("Warning","Incorrect username or password");
            }
        }

    }
}
