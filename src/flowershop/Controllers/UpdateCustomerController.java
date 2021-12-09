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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class UpdateCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField cid;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField contact;
    @FXML
    private Button go;
    @FXML
    private Button update;
    @FXML
    private GridPane customergrid;
    @FXML
    private Label chkfields;
    @FXML
    private Label chkcontact;
    @FXML
    private Label enter;
    @FXML
    private Label exist;
    @FXML
    private Label nec;
    @FXML
    private ImageView back;
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
        String sql = "update customer set fname = ?,lname = ?,contactno = ? where customerid = ?";
        int customerid = Integer.parseInt(cid.getText());
        String fname = firstname.getText();
        String lname;
        if(lastname.getText().isEmpty() == true){
            lname = null;}
        else{
            lname = lastname.getText();}
        String contactno;
        if(contact.getText().isEmpty() == true){
            contactno = null;}
        else{
            contactno = contact.getText();}
        if(firstname.getText().isEmpty() == true)
            chkfields.setVisible(true);
        else if(contact.getText().length() != 11)
            chkcontact.setVisible(true);
        else{
                
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,fname);
            pst.setString(2,lname);
            pst.setString(3,contactno);
            pst.setInt(4,customerid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Customer updated successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        firstname.clear();
        lastname.clear();
        contact.clear();
        customergrid.setVisible(false);
        update.setVisible(false);
        nec.setVisible(false);
        cid.clear();
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
            Logger.getLogger(UpdateCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CustomerController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @FXML
    private void loadDataFromDatabase(ActionEvent event) throws SQLException{
        enter.setVisible(false);
        exist.setVisible(false);
        cid.setEditable(false);
        customergrid.setVisible(false);
            String sql = ("Select fname,lname,contactno from customer where customerid = ?");
            if (cid.getText().isEmpty()){
                enter.setVisible(true);
                cid.setEditable(true);
            }
            else if(checkCustomer() == false){
                exist.setVisible(true);
                cid.clear();
                cid.setEditable(true);
            }
            else{
                try{
            int customerid = Integer.parseInt(cid.getText());
            pst = con.prepareStatement(sql);
            pst.setInt(1,customerid);
            rs = pst.executeQuery();
            while(rs.next()){
                customergrid.setVisible(true);
                update.setVisible(true);
                nec.setVisible(true);
                
                String fname = rs.getString(1); 
                firstname.setText(fname);
                String lname = rs.getString(2); 
                lastname.setText(lname);
                String contactno = rs.getString(3); 
                contact.setText(contactno);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateCustomerController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
    }    
    
}
