package fa.cpl_java_05.app.controller.login;

import fa.cpl_java_05.entities.user.User;
import fa.cpl_java_05.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.Serializable;

public class LoginController implements Serializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signupBtn;


    public void submit(ActionEvent actionEvent){
        String username = usernameTextField.getText();
        String password = passwordField.getText();
    }
}
