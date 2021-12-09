/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import flowershop.PurchaseList;
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
public class PurchaseHistoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableColumn<?, ?> column_date;
    @FXML
    private ImageView add;
    @FXML
    private TableView<PurchaseList> purchasetbl;
    @FXML
    private TableColumn<?, ?> column_eid;
    @FXML
    private TableColumn<?, ?> column_sid;
    @FXML
    private TableColumn<?, ?> column_total;
    @FXML
    private TableColumn<?, ?> column_price;
    @FXML
    private ImageView back;
    @FXML
    private TableColumn<?, ?> column_quantity;
    @FXML
    private TableColumn<?, ?> column_iid;
    
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("purchase"+getLoginid());
        if(getLoginid() == 1){
            add.setVisible(false);
        }
        else if(getLoginid() > 1){
            add.setVisible(true);
        }
    }
    
    private ObservableList<PurchaseList> data;
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
    
    private void setCellTable(){
        column_sid.setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        column_eid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        column_iid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        column_date.setCellValueFactory(new PropertyValueFactory<>("supplydate"));
        column_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        column_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select * from [supply details]");
            rs = pst.executeQuery();
            
            while(rs.next()){
                data.add(new PurchaseList(rs.getString(3),rs.getInt(1),rs.getInt(2),rs.getInt(5),rs.getDouble(6),
                rs.getString(4),rs.getInt(5)*rs.getDouble(6)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        purchasetbl.setItems(data);
    }
    
    public void toPurchaseScreen(){
        add.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Purchase.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(PurchaseHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
       PurchaseController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        //Stage stage = new Stage();
        if(getLoginid() == 1){
            prev2();
        }
        if(getLoginid() > 1){
            prev1();
        }
    }
    
    public void prev1(){
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/EmployeeDashboard.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(PurchaseHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("sale prev "+getLoginid());
       EmployeeDashboardController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    public void prev2(){
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/AdminPath/AdminDashboard.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(PurchaseHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("sale prev2 "+getLoginid());
       AdminDashboardController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
}
