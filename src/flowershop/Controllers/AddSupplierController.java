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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class AddSupplierController implements Initializable {

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
    private TextField scity;
    @FXML
    private TextField scompany;
    @FXML
    private ToggleGroup t_gender;
    @FXML
    private RadioButton Male;
    @FXML
    private RadioButton Female;
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
        String sql = "insert into supplier(fname,lname,gender,contactno,city,company) values (?,?,?,?,?,?)";
        String fname = firstname.getText();
        String lname;
        if(lastname.getText().isEmpty() == true)
            lname = null;
        else
            lname = lastname.getText();
        RadioButton chk = (RadioButton)t_gender.getSelectedToggle(); // Cast object to radio button
        String gender = chk.getText();
        String contactno;
        if(contact.getText().isEmpty() == true)
            contactno = null;
        else
            contactno = contact.getText();
        String city;
        if(scity.getText().isEmpty() == true)
            city = null;
        else
            city = scity.getText();
        String company = scompany.getText();
        if(firstname.getText().isEmpty() == true  || scompany.getText().isEmpty() == true)
            chkfields.setVisible(true);
        else if(contact.getText().isEmpty() == false && contact.getText().length() != 11)
            chkcontact.setVisible(true);
        else if(loadDataFromDatabase() == true){
             AlertDialog.display("Redundancy Check", "This supplier already exists.");
        }
        else{     
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,fname);
            pst.setString(2,lname);   
            pst.setString(3,gender);
            pst.setString(4,contactno);
            pst.setString(5,city);
            pst.setString(6,company);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Supplier Added successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddSupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        firstname.clear();
        lastname.clear();
        Male.setSelected(true);
        contact.clear();
        scity.clear();
        scompany.clear();
    }
    
    private boolean loadDataFromDatabase() throws SQLException{
        try {
            pst = con.prepareStatement("Select fname,lname,company from employee where fname = ? and lname = ? and company = ?");
            String fname = firstname.getText();
            String lname = lastname.getText();
            String company = scompany.getText();
            pst.setString(1,fname);
            pst.setString(2,lname);
            pst.setString(3,company);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddSupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/Supplier.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddSupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SupplierController e = loader.getController();
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
