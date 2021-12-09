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
public class UpdateSupplierController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField sid;
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
    private Button go;
    @FXML
    private Button update;
    @FXML
    private GridPane suppliergrid;
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
        String sql = "update supplier set fname = ?,lname = ?,gender = ?,contactno = ?,city = ?, company = ? where supplierid = ?";
        int supplierid = Integer.parseInt(sid.getText());
        String fname = firstname.getText();
        String lname;
        if(lastname.getText().isEmpty() == true){
            lname = null;}
        else{
            lname = lastname.getText();}
        
        RadioButton chk = (RadioButton)t_gender.getSelectedToggle(); // Cast object to radio button
        String gender = chk.getText();
        String contactno;
        if(contact.getText().isEmpty() == true){
            contactno = null;}
        else{
            contactno = contact.getText();}
        String city;
        if(scity.getText().isEmpty() == true){
            city = null;}
        else{
            city = scity.getText();}
        String company = scompany.getText();
        if(firstname.getText().isEmpty() == true  || scompany.getText().isEmpty() == true )
            chkfields.setVisible(true);
        else if(contact.getText().length() != 11)
            chkcontact.setVisible(true);
        else{
                
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,fname);
            pst.setString(2,lname);   
            pst.setString(3,gender);
            pst.setString(4,contactno);
            pst.setString(5,city);
            pst.setString(6,company);
            pst.setInt(7,supplierid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Supplier updated successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateSupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        firstname.clear();
        lastname.clear();
        contact.clear();
        Male.setSelected(true);
        scity.clear();
        scompany.clear();
        suppliergrid.setVisible(false);
        update.setVisible(false);
        nec.setVisible(false);
        sid.clear();
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
            Logger.getLogger(UpdateSupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SupplierController e = loader.getController();
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
        sid.setEditable(false);
        suppliergrid.setVisible(false);
            String sql = ("Select fname,lname,gender,contactno,city,company from supplier where supplierid = ?");
            if (sid.getText().isEmpty()){
                enter.setVisible(true);
                sid.setEditable(true);
            }
            
            else if(checkSupplier() == false){
                System.out.println("99");
                exist.setVisible(true);
                sid.clear();
                sid.setEditable(true);
            }
            else{
                try{
            int supplierid = Integer.parseInt(sid.getText());
            pst = con.prepareStatement(sql);
            pst.setInt(1,supplierid);
            rs = pst.executeQuery();
            while(rs.next()){
                suppliergrid.setVisible(true);
                update.setVisible(true);
                nec.setVisible(true);
                
                String fname = rs.getString(1); 
                firstname.setText(fname);
                String lname = rs.getString(2); 
                lastname.setText(lname);
                String gender = rs.getString(3);
                if(Male.getText().equals(gender))
                    t_gender.selectToggle(Male);
                else if (Female.getText().equals(gender))
                    t_gender.selectToggle(Female);
                String contactno = rs.getString(4); 
                contact.setText(contactno);
                String city = rs.getString(5); 
                scity.setText(city);
                String company = rs.getString(6); 
                scompany.setText(company);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateSupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private boolean checkSupplier() throws SQLException{
        System.out.println("3");
        try{
        pst = con.prepareStatement("select supplierid from supplier where supplierid = ?");
        int supplierid = Integer.parseInt(sid.getText());
        pst.setInt(1,supplierid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UpdateSupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
    }    
    
}
