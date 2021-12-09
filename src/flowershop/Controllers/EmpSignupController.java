/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import Dialog.AlertDialog;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class EmpSignupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField t_username;
    @FXML
    private PasswordField t_password;
    @FXML
    private PasswordField t_confirmpassword;
    @FXML
    private Button signup;
    @FXML
    private Label match;
    @FXML
    private Label enter;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }
    
    
    private Connection con1 = null;
    private PreparedStatement pst1 = null;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        // TODO
        enter.setVisible(false);
        match.setVisible(false);
        String uname = t_username.getText();
        String pword = t_password.getText();
        String cpword = t_confirmpassword.getText();
        if(t_username.getText().isEmpty()==true || t_password.getText().isEmpty() == true || 
                t_confirmpassword.getText().isEmpty() == true){
            enter.setVisible(true);
        }
        else if(cpword.equals(pword)){
            String sql = "insert into emplogin(uname,pword) values (?,?)";
            enter.setVisible(false);
            match.setVisible(false);
        try {
            pst1 = con1.prepareStatement(sql);
            pst1.setString(1,uname);
            pst1.setString(2,pword);
            int i = pst1.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Employee account created.");
                toPreviousScreen();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpSignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
            match.setVisible(true);
            t_password.clear();
            t_confirmpassword.clear();
        }
    }  
    
    public void toPreviousScreen(){
        signup.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/AddEmployee.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmpSignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddEmployeeController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con1 = dba.DbConnection.flowerConnection();
    } 
    
}
