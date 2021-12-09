/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class AdminLoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField t_username;
    @FXML
    private PasswordField t_password;
    @FXML
    private Button login;
    @FXML
    private ImageView back;
    @FXML
    private Label incorrect;
    private int loginid;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @FXML  //login button
    private void handleButtonAction(ActionEvent event) throws SQLException {
        try {
            incorrect.setVisible(false);
            pst = con.prepareStatement("select uname,pword from adminlogin where uname = ? and pword = ?");
            String uname = t_username.getText();
            String pword = t_password.getText();
            pst.setString(1,uname);
            pst.setString(2,pword);
            rs = pst.executeQuery();
            if(rs.next() == false){
                incorrect.setVisible(true);
                t_username.clear();
                t_password.clear();
            }
            else{
            
                toAdminDashboard();                    
            }            
        } catch (SQLException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getAdminidFromDatabase() throws SQLException{
        pst = con.prepareStatement("select adminloginid from adminlogin where uname = ? and pword = ?");
            String uname = t_username.getText();
            String pword = t_password.getText();
            pst.setString(1,uname);
            pst.setString(2,pword);
            try{
            rs = pst.executeQuery();
            while(rs.next())
            setLoginid(rs.getInt(1));
                       
        } catch (SQLException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toAdminDashboard() throws SQLException{
        getAdminidFromDatabase();
        login.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/AdminPath/AdminDashboard.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AdminDashboardController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();/*
        Stage stage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/flowershop/AdminPath/AdminDashboard.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){}*/
    }
    
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        Stage stage = new Stage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/flowershop/Views/startUp.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
       // back.setOnAction(event -> toPreviousScreen());
       
    }    

    /**
     * @return the adminid
     */
    public int getLoginid() {
        return loginid;
    }

    
    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }
    
}
