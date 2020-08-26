package fa.cpl_java_05.app.views.common;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static boolean display(String title, String message){
        Stage window = new Stage();
        boolean cancel = false;

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);

        Label label = new Label();
        label.setText(message);

        Button yesBtn = new Button("Confirm");
        Button noBtn = new Button("Cancel");
        yesBtn.setOnAction(e -> {

        });
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(yesBtn,noBtn);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
        return true;
    }
}
