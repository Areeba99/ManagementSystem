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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class AddCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField contact;
    @FXML
    private ImageView back;
    @FXML
    private Label chkcontact;
    @FXML
    private Label chkfields;
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
        chkfields.setVisible(false);
        chkcontact.setVisible(false);
        String sql = "insert into customer(fname,lname,contactno) values (?,?,?)";
        String fname = firstname.getText();
        String lname;
        if(lastname.getText().isEmpty() == true)
            lname = null;
        else
            lname = lastname.getText();
        String contactno;
        if(contact.getText().isEmpty() == true)
            contactno = null;
        else
            contactno = contact.getText();
        if(firstname.getText().isEmpty() == true)
            chkfields.setVisible(true);
        else if(contact.getText().isEmpty() == false && contact.getText().length() != 11)
            chkcontact.setVisible(true);
        else if(loadDataFromDatabase() == true){
             AlertDialog.display("Redundancy Check", "This customer already exists.");
        }
        else{     
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,fname);
            pst.setString(2,lname);
            pst.setString(3,contactno);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Customer Added successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        firstname.clear();
        lastname.clear();
        contact.clear();
    }
    
    private boolean loadDataFromDatabase() throws SQLException{
        try {
            pst = con.prepareStatement("Select fname,lname from customer where fname = ? and lname = ?");
            String fname = firstname.getText();
            String lname = lastname.getText();
            pst.setString(1,fname);
            pst.setString(2,lname);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
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
