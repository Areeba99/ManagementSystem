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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class UpdateEmployeeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //textfields
    @FXML
    private TextField empid;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField contact;
    @FXML
    private TextField address;
    @FXML
    private TextField salarym;
    //radiobuttons
    @FXML
    private ToggleGroup t_shift;
    @FXML
    private ToggleGroup t_gender;
    @FXML
    private RadioButton Morning;
    @FXML
    private RadioButton Evening;
    @FXML
    private RadioButton Male;
    @FXML
    private RadioButton Female;
    //buttons
    @FXML
    private Button update;
    @FXML
    private Button go;
    @FXML
    private DatePicker birth;
    @FXML
    private DatePicker hire;
    @FXML
    private GridPane employeegrid; 
    @FXML
    private ImageView back;
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
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("10");
    }
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
   // private ObservableList<EmployeeList> data;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException  {
        System.out.println("6");
        chkfields.setVisible(false);
        chkcontact.setVisible(false);
        String sql = "update employee set fname = ?,lname = ?,salary = ?,contactno = ?,hiredate = ?,gender = ?,paddress = ?,tshift = ?,birthdate = ? where employeeid = ?";
        Integer employeeid = Integer.parseInt(empid.getText());
        
        String fname = firstname.getText();
        String lname;
        if(lastname.getText().isEmpty() == true){
            lname = null;}
        else{
            lname = lastname.getText();}
        RadioButton chk = (RadioButton)t_gender.getSelectedToggle(); // Cast object to radio button
        String gender = chk.getText();
        String paddress;
        if(address.getText().isEmpty() == true){
            paddress = null;}
        else{
            paddress = address.getText();}
        String birthdate = String.valueOf(birth.getValue());
        String hiredate = String.valueOf(hire.getValue());
        String tshift;
        if(Morning.isSelected() == false && Evening.isSelected() == false){
            tshift = null;}
        else{
            RadioButton chk1 = (RadioButton)t_shift.getSelectedToggle();
            tshift = chk1.getText();
        }
        Double salary;
        if (salarym.getText().isEmpty()){
            salary = Double.valueOf(0);
        }
        else{
        salary = Double.parseDouble(salarym.getText());
        }
        String contactno;
        if(contact.getText().isEmpty() == true){
            contactno = null;}
        else{
            contactno = contact.getText();}
        
        if(firstname.getText().isEmpty() == true  || birth.getValue() == null ||
                hire.getValue() == null)
            chkfields.setVisible(true);
        else if(contact.getText().length() != 11)
            chkcontact.setVisible(true);
        else{
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,fname);
            pst.setString(2,lname);       
            pst.setDouble(3,salary);
            pst.setString(4,contactno);
            pst.setString(5,hiredate);
            pst.setString(6,gender);
            pst.setString(7,paddress);
            pst.setString(8,tshift);
            pst.setString(9,birthdate);
            pst.setInt(10,employeeid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Employee updated successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        firstname.clear();
        lastname.clear();
        contact.clear();
        Male.setSelected(true);
        address.clear();
        birth.setValue(null);
        Morning.setSelected(false);
        Evening.setSelected(false);
        salarym.clear();
        hire.setValue(null);
        employeegrid.setVisible(false);
        update.setVisible(false);
        nec.setVisible(false);
        empid.clear();
        empid.setEditable(true);
    }
    
    @FXML
    private void loadDataFromDatabase(ActionEvent event) throws SQLException{
        System.out.println("2");
        enter.setVisible(false);
        exist.setVisible(false);
        empid.setEditable(false);
        employeegrid.setVisible(false);
            String sql = "Select fname,lname,salary,contactno,hiredate,paddress,birthdate,gender,tshift from employee where employeeid = ?";
            if (empid.getText().isEmpty()){
                System.out.println("in if");
                enter.setVisible(true);
                empid.setEditable(true);
            }
            
            else if(checkEmployee() == false){
                System.out.println("99");
                exist.setVisible(true);
                empid.clear();
                empid.setEditable(true);
            }
            
            else{
            int employeeid = Integer.parseInt(empid.getText());
            System.out.println("77");
                System.out.println("88");
                try{
            pst = con.prepareStatement(sql);
            pst.setInt(1,employeeid);
            rs = pst.executeQuery();
            while(rs.next()){
                employeegrid.setVisible(true);
                update.setVisible(true);
                nec.setVisible(true);
                String fname = rs.getString(1); 
                firstname.setText(fname);
                String lname = rs.getString(2); 
                lastname.setText(lname);
                Double salary = rs.getDouble(3); 
                salarym.setText(Double.toString(salary));
                String contactno = rs.getString(4); 
                contact.setText(contactno);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate hiredate = LocalDate.parse(rs.getString(5), formatter);
                hire.setValue(hiredate);
                String paddress = rs.getString(6); 
                address.setText(paddress);
                LocalDate birthdate = LocalDate.parse(rs.getString(7), formatter);
                birth.setValue(birthdate);
                String gender = rs.getString(8);
                if(Male.getText().equals(gender))
                    t_gender.selectToggle(Male);
                else if (Female.getText().equals(gender))
                    t_gender.selectToggle(Female);
                String shift = rs.getString(9);
                if(Morning.getText().equals(shift))
                    t_shift.selectToggle(Morning);
                else if (Evening.getText().equals(shift))
                    t_shift.selectToggle(Evening);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private boolean checkEmployee() throws SQLException{
        System.out.println("3");
        try{
        pst = con.prepareStatement("select employeeid from employee where employeeid = ?");
        int employeeid = Integer.parseInt(empid.getText());
        pst.setInt(1,employeeid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UpdateEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @FXML
    public void toPreviousScreen(){
        System.out.println("4");
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/employee.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(UpdateEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("1");
    }
}
