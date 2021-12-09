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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author areeb
 */
public class AddEmployeeController implements Initializable {
    
    @FXML
    private Button add;
    @FXML
    private ToggleGroup t_shift;
    @FXML
    private TextField txt_salary;
    @FXML
    private RadioButton Evening;
    @FXML
    private RadioButton Morning;
    @FXML
    private TextField txt_paddress;
    @FXML
    private RadioButton Male;
    @FXML
    private TextField txt_lname;
    @FXML
    private DatePicker txt_birthdate;
    @FXML
    private TextField txt_fname;
    @FXML
    private Button signup;
    @FXML
    private ToggleGroup t_gender;
    @FXML
    private TextField txt_contactno;
    @FXML
    private RadioButton Female;
    @FXML
    private DatePicker txt_hiredate;
    @FXML
    private ImageView back;
    @FXML
    private Label chkcontact;
    @FXML
    private Label chkfields;
    private int loginid;
    
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
      
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        chkfields.setVisible(false);
        chkcontact.setVisible(false);
        String sql = "insert into employee(fname,lname,gender,paddress,birthdate,hiredate,salary,contactno,tshift) values (?,?,?,?,?,?,?,?,?)";
        String fname = txt_fname.getText();
        String lname;
        if(txt_lname.getText().isEmpty() == true)
            lname = null;
        else
            lname = txt_lname.getText();
        RadioButton chk = (RadioButton)t_gender.getSelectedToggle(); // Cast object to radio button
        String gender = chk.getText();
        String paddress;
        if(txt_paddress.getText().isEmpty() == true)
            paddress = null;
        else
            paddress = txt_paddress.getText();
        String birthdate = String.valueOf(txt_birthdate.getValue());
        String hiredate = String.valueOf(txt_hiredate.getValue());
        
        Double salary;
        if (txt_salary.getText().isEmpty()){
            salary = Double.valueOf(0);
        }
        else{
        salary = Double.parseDouble(txt_salary.getText());
        }
        String contactno;
        if(txt_contactno.getText().isEmpty() == true)
            contactno = null;
        else
            contactno = txt_contactno.getText();
        String tshift;
        if(Morning.isSelected() == false && Evening.isSelected() == false)
            tshift = null;
        else{
            RadioButton chk1 = (RadioButton)t_shift.getSelectedToggle();
            tshift = chk1.getText();
        }
        
        if(txt_fname.getText().isEmpty() == true  || txt_birthdate.getValue() == null ||
                txt_hiredate.getValue() == null)
            chkfields.setVisible(true);
        else if(txt_contactno.getText().isEmpty() == false && txt_contactno.getText().length() != 11)
            chkcontact.setVisible(true);
        else if(loadDataFromDatabase() == true){
             AlertDialog.display("Redundancy Check", "This employee already exists.");
        }
        
        else{
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,fname);
            pst.setString(2,lname);       
            pst.setString(3,gender);
            pst.setString(4,paddress);
            pst.setString(5,birthdate);
            pst.setString(6,hiredate);
            pst.setDouble(7,salary);
            pst.setString(8,contactno);
            pst.setString(9,tshift);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Employee added successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        txt_fname.clear();
        txt_lname.clear();
        txt_contactno.clear();
        Male.setSelected(true);
        txt_paddress.clear();
        txt_birthdate.setValue(null);
        Morning.setSelected(false);
        Evening.setSelected(false);
        txt_salary.clear();
        txt_hiredate.setValue(null);
    }
    
    private boolean loadDataFromDatabase() throws SQLException{
        try {
            pst = con.prepareStatement("Select fname,lname,birthdate from employee where fname = ? and lname = ? and birthdate = ?");
            String fname = txt_fname.getText();
            String lname = txt_lname.getText();
            String birthdate = String.valueOf(txt_birthdate.getValue());
            pst.setString(1,fname);
            pst.setString(2,lname);
            pst.setString(3,birthdate);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private boolean checkAccount() throws SQLException{
        try{
        pst = con.prepareStatement("select emploginid from emplogin,employee where employeeid = emploginid and employeeid in "
                + "(select top 1 employeeid from employee order by employeeid desc) ");
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);   
        }
        return false;
    }
    
    @FXML
    private void handleBackButton() throws SQLException{
        if(checkAccount() == true)
            toPreviousScreen();
        else
            AlertDialog.display("New Account Alert", "First create an account for the new Employee.");
    }
    
    @FXML
    private void handleCreateButton() throws SQLException{
        if(checkAccount() == true)
            AlertDialog.display("New Account Alert", "An account already exists for the associated Employee.");
        else
            toSignupScreen();
    }
    
    public void toSignupScreen(){
        signup.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/AdminPath/empSignup.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        EmpSignupController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
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
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
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

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }
}
