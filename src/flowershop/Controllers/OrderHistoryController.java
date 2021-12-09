/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import flowershop.SaleList;

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
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class OrderHistoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ImageView add;
    @FXML
    private ImageView back;
    @FXML
    private ImageView customer;
    @FXML
    private Button profit;
    @FXML
    private TableColumn<?, ?> column_oid;
    @FXML
    private TableView<SaleList> saletbl;
    @FXML
    private TableColumn<?, ?> column_cid;
    @FXML
    private TableColumn<?, ?> column_eid;
    @FXML
    private TableColumn<?, ?> column_date;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        if(getLoginid() == 1){
            add.setVisible(false);
        }
        else if(getLoginid() > 1){
            add.setVisible(true);
        }
    }
    
    private ObservableList<SaleList> data;
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
        /*
        saletbl.setOnMousePressed(new EventHandler<MouseEvent>(){
        @Override
     public void handle(MouseEvent event){
        saletbl.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/OrderDetail.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
       OrderDetailController e = loader.getController();
       e.setData(""+saletbl.getSelectionModel().getSelectedItem().getOrderid());
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    });*/
    }    
    
    private void setCellTable(){
        column_oid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        column_date.setCellValueFactory(new PropertyValueFactory<>("orderdate"));
        column_eid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        column_cid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
    }
    
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select * from orders");
            rs = pst.executeQuery();
            
            while(rs.next()){
                data.add(new SaleList(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        saletbl.setItems(data);
    }
    
    
    
    public void toInvoiceScreen(){
        add.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/Invoice.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
       InvoiceController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toCustomerScreen(){
        customer.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Customer.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
       CustomerController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
   /* @FXML
    public void toProfitScreen(){
        profit.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Profit.fxml"));
        System.out.println("66");
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("77");
       ProfitController e = loader.getController();
       e.setLoginid(getLoginid());
        System.out.println("99");
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }*/
    
    @FXML
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
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
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
