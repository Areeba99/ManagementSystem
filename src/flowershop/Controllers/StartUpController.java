/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class StartUpController implements Initializable {
    
    @FXML
    private Button admin;
    @FXML
    private Button employee;
    
    private Connection con = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
        admin.setOnAction(event -> toAdminLoginScreen());
        employee.setOnAction(event -> toEmployeeLoginScreen());
    }
    
    public void toAdminLoginScreen(){ 
        admin.getScene().getWindow().hide();
        Stage stage = new Stage();
        try{ 
            Parent root = FXMLLoader.load(getClass().getResource("/flowershop/AdminPath/adminLogin.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Administrator Login");
            stage.show();
        }
        catch(IOException e){}
    }
    
    public void toEmployeeLoginScreen(){ 
        employee.getScene().getWindow().hide();
        Stage stage = new Stage();
        try{ 
            Parent root = FXMLLoader.load(getClass().getResource("/flowershop/EmployeePath/empLogin.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Employee Login");
            stage.show();
        }
        catch(IOException e){}
    }      
}
