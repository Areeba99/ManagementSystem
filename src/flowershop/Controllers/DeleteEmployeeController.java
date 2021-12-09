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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class DeleteEmployeeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField eid;
    @FXML
    private Button delete;
    @FXML
    private ImageView back;
    @FXML
    private Label enter;
    @FXML
    private Label exist;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        enter.setVisible(false);
        exist.setVisible(false);
        String sql = "delete from employee where employeeid = ?";
        if(eid.getText().isEmpty() == true){
            enter.setVisible(true);}
        
        
        else if (checkEmployee() == false){
            exist.setVisible(true);
            eid.clear();
        }
        else{
        try {
            int employeeid = Integer.parseInt(eid.getText());
            pst = con.prepareStatement(sql);
            pst.setInt(1,employeeid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                deleteAccount();
                AlertDialog.display("Info", "Employee deleted successfully.");
                eid.clear();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DeleteEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void deleteAccount() throws SQLException {
        String sql = "delete from emplogin where emploginid = ?";
        
        try {
            Integer emploginid = Integer.parseInt(eid.getText());
            pst = con.prepareStatement(sql);
            pst.setInt(1,emploginid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DeleteEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkEmployee() throws SQLException{
        try{
        pst = con.prepareStatement("select employeeid from employee where employeeid = ?");
        Integer employeeid = Integer.parseInt(eid.getText());
        pst.setInt(1,employeeid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DeleteEmployeeController.class.getName()).log(Level.SEVERE, null, ex);   
        }
        return false;
    }
    
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/employee.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DeleteEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        EmployeeController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
    }    
    
}
