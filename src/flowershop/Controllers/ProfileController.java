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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class ProfileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private TextField empid;

    @FXML
    private TextField firstname;

    @FXML
    private TextField address;

    @FXML
    private TextField hire;

    @FXML
    private TextField shift;

    @FXML
    private TextField birth;

    @FXML
    private TextField unmask;

    @FXML
    private TextField lastname;

    @FXML
    private TextField salarym;

    @FXML
    private CheckBox unhide;

    @FXML
    private TextField contact;

    @FXML
    private ImageView female;

    @FXML
    private ImageView male;

    @FXML
    private TextField username;

    @FXML
    private PasswordField mask;
    @FXML
    private ImageView back;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        empid.setText(String.valueOf(getLoginid()));
        loadDataFromDatabase();
    }

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select fname,lname,gender,paddress,birthdate,hiredate,salary,"
                    + "contactno,tshift,uname,pword from employee,emplogin where employeeid = ? and emploginid = ?");
            System.out.println(empid.getText());
            Integer employeeid = Integer.parseInt(empid.getText());
            pst.setInt(1,employeeid);
            pst.setInt(2,employeeid);
            rs = pst.executeQuery();
            while(rs.next()){
                String fname = rs.getString(1); 
                firstname.setText(fname);
                String lname = rs.getString(2); 
                lastname.setText(lname);
                String gender = rs.getString(3);
                if(gender.equals("Male"))
                    male.setVisible(true);
                else if(gender.equals("Female"))
                    female.setVisible(true);
                String paddress = rs.getString(4); 
                address.setText(paddress);
                String birthdate = rs.getString(5);
                birth.setText(birthdate);
                String hiredate = rs.getString(6);
                hire.setText(hiredate);
                Double salary = rs.getDouble(7); 
                salarym.setText(Double.toString(salary));
                String contactno = rs.getString(8); 
                contact.setText(contactno);
                String tshift = rs.getString(9); 
                shift.setText(tshift);
                String uname = rs.getString(10);
                username.setText(uname);
                String pword = rs.getString(11);
                mask.setText(pword);
                unmask.setText(pword);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void unHidePassword(){
        if(unhide.isSelected()){
            mask.setVisible(false);
            unmask.setVisible(true);
        }
        else{
            mask.setVisible(true);
            unmask.setVisible(false);
        }
    }
    
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/EmployeeDashboard.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
       EmployeeDashboardController e = loader.getController();
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
