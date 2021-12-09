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
public class DeleteCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private TextField cid;
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
        String sql = "delete from customer where customerid = ?";
        if(cid.getText().isEmpty() == true){
            enter.setVisible(true);}
        
        else if (checkCustomer() == false){
            exist.setVisible(true);
            cid.clear();
        }
        else{
        try {
            int customerid = Integer.parseInt(cid.getText());
            pst = con.prepareStatement(sql);
            pst.setInt(1,customerid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Customer deleted successfully.");
                cid.clear();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private boolean checkCustomer() throws SQLException{
        try{
        pst = con.prepareStatement("select customerid from customer where customerid = ?");
        int customerid = Integer.parseInt(cid.getText());
        pst.setInt(1,customerid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DeleteCustomerController.class.getName()).log(Level.SEVERE, null, ex);   
        }
        return false;
    }
    
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/Customer.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DeleteCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CustomerController e = loader.getController();
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
