package fa.cpl_java_05.app.common;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(200);
        window.setWidth(600);

        Label label = new Label();
        label.setText(message);
        label.setFont(new Font("Arial", 20));
        if("Warning".equals(title)){
            label.setTextFill(Color.web("#ff0000", 0.8));
        }

        Button btn = new Button("OK");
        btn.setOnAction(e -> window.close());
        btn.setPrefWidth(80);
        btn.setPrefHeight(40);

        VBox vbox = new VBox(40);
        vbox.getChildren().addAll(label,btn);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }
}
