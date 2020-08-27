package fa.cpl_java_05.app.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mainStage = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fa/cpl_java_05/app/views/login/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
