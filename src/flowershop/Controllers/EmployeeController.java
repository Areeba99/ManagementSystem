/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import flowershop.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class EmployeeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<EmployeeList> employeetbl;
    @FXML
    private TableColumn<?,?> column_eid;
    @FXML
    private TableColumn<?,?> column_fname;
    @FXML
    private TableColumn<?,?> column_lname;
    @FXML
    private TableColumn<?,?> column_salary;
    @FXML
    private TableColumn<?,?> column_contactno;
    @FXML
    private TableColumn<?,?> column_hiredate;
    @FXML
    private TableColumn<?,?> column_gender;
    @FXML
    private TableColumn<?,?> column_paddress;
    @FXML
    private TableColumn<?,?> column_tshift;
    @FXML
    private TableColumn<?,?> column_birthdate;
    @FXML
    private ImageView delete;
    @FXML
    private ImageView update;
    @FXML
    private ImageView add;
    @FXML
    private ImageView back;
    private int loginid;
    
    private ObservableList<EmployeeList> data;
    private TableView tableview;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDatabase();
        //System.out.println("initializaaa: "+getLoginid());
        
    }
    
    public void toAddEmployeeScreen(){
        add.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/AddEmployee.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddEmployeeController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    public void toUpdateEmployeeScreen(){
        update.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/UpdateEmployee.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        UpdateEmployeeController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    public void toDeleteEmployeeScreen(){
        delete.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/DeleteEmployee.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DeleteEmployeeController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/AdminPath/AdminDashboard.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AdminDashboardController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    private void setCellTable(){
        column_eid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        column_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        column_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        column_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        column_paddress.setCellValueFactory(new PropertyValueFactory<>("paddress"));
        column_birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        column_hiredate.setCellValueFactory(new PropertyValueFactory<>("hiredate"));
        column_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        column_contactno.setCellValueFactory(new PropertyValueFactory<>("contactno"));
        column_tshift.setCellValueFactory(new PropertyValueFactory<>("tshift"));
        
    }
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select * from employee");
            rs = pst.executeQuery();
            
            while(rs.next()){
                data.add(new EmployeeList(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getDouble(8),rs.getString(9),rs.getString(10)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        employeetbl.setItems(data);
    }

   
    public int getLoginid() {
        return loginid;
    }

    
    public void setLoginid(int loginid) {
        
        this.loginid = loginid;
    }
            
    
}
