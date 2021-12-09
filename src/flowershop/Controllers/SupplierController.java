/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import flowershop.SupplierList;
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
import javafx.scene.control.Button;
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
public class SupplierController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<SupplierList> suppliertbl;
    @FXML
    private TableColumn<?,?> column_sid;
    @FXML
    private TableColumn<?,?> column_fname;
    @FXML
    private TableColumn<?,?> column_lname;
    @FXML
    private TableColumn<?,?> column_gender;
    @FXML
    private TableColumn<?,?> column_contactno;
    @FXML
    private TableColumn<?,?> column_city;
    @FXML
    private TableColumn<?,?> column_company;
    @FXML
    private ImageView delete;
    @FXML
    private ImageView update;
    @FXML
    private ImageView add;
    @FXML
    private ImageView back;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        if(getLoginid() == 1){
            System.out.println("22");
            add.setVisible(false);
            update.setVisible(false);
            delete.setVisible(false);
        }
        else if(getLoginid() > 1){
            System.out.println("11");
            add.setVisible(true);
            update.setVisible(true);
            delete.setVisible(true);
        }
    }
    
    private ObservableList<SupplierList> data;
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
        
    }    
    
    @FXML
    public void toAddSupplierScreen(){
        add.getScene().getWindow().hide();
        System.out.println("clicked");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/EmployeePath/AddSupplier.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddSupplierController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @FXML
    public void toUpdateSupplierScreen(){
        update.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/EmployeePath/UpdateSupplier.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        UpdateSupplierController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @FXML
    public void toDeleteSupplierScreen(){
        delete.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/EmployeePath/DeleteSupplier.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DeleteSupplierController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @FXML
    private void toPreviousScreen(){
        back.getScene().getWindow().hide();
        System.out.println("44");
        if(getLoginid() == 1){
            prev2();
        }
        if(getLoginid() > 1){
            prev1();
        }
    }
    
    public void prev1(){
        System.out.println("previous1");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/EmployeePath/EmployeeDashboard.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("55");
        EmployeeDashboardController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    public void prev2(){
        System.out.println("previous2");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/AdminPath/AdminDashboard.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("66");
        AdminDashboardController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    private void setCellTable(){
        column_sid.setCellValueFactory(new PropertyValueFactory<>("sid"));
        column_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        column_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        column_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        column_contactno.setCellValueFactory(new PropertyValueFactory<>("contactno"));
        column_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        column_company.setCellValueFactory(new PropertyValueFactory<>("company"));
    }
    
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select * from supplier");
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new SupplierList(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        suppliertbl.setItems(data);
    }
}
