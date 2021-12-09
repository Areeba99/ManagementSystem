/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author areeb
 */
public class AlertDialog {
    public static void display(String title, String message){
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(300);
        window.setMaxHeight(700);
        
        Label label = new Label();
        label.setText(message);
        Button ok = new Button("Ok");
        ok.setOnAction(e -> window.close());
        
        VBox layout = new VBox();
        layout.getChildren().addAll(label,ok);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/Dialog/AlertDialog.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
