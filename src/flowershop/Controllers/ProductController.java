/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import flowershop.ProductList;
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
public class ProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<ProductList> producttbl;
    @FXML
    private TableColumn<?,?> column_pid;
    @FXML
    private TableColumn<?,?> column_name;
    @FXML
    private TableColumn<?,?> column_description;
    @FXML
    private TableColumn<?,?> column_quantity;
    @FXML
    private TableColumn<?,?> column_price;
    @FXML
    private TableColumn<?,?> column_baseprice;
    @FXML
    private TableColumn<?,?> column_charges;
    @FXML
    private ImageView delete;
    @FXML
    private ImageView update;
    @FXML
    private ImageView add;
    @FXML
    private ImageView back;
    @FXML
    private ImageView additem;
    @FXML
    private ImageView updateprice;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        if(getLoginid() == 1){
            updateprice.setVisible(true);
            additem.setVisible(false);
            add.setVisible(false);
            update.setVisible(false);
            delete.setVisible(false);
        }
        else if(getLoginid() > 1){
            updateprice.setVisible(false);
            additem.setVisible(true);
            add.setVisible(true);
            update.setVisible(true);
            delete.setVisible(true);
        }
    }
    
    private ObservableList<ProductList> data;
    private TableView tableview;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ResultSet rs1 = null;
    
    @FXML
    public void toAddProductScreen(){
        add.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/EmployeePath/AddProduct.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddProductController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @FXML
    public void toDeleteProductScreen(){
        delete.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/DeleteProduct.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
      DeleteProductController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toUpdateProductScreen(){
        update.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/UpdateProduct.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
       UpdateProductController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toAddProductChargesScreen(){
        updateprice.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/AdminPath/AddProductCharges.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
       AddProductChargesController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toProductDetailScreen(){
        additem.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/ProductDetail.fxml"));
        try{
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProductDetailController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
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
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("prod prev "+getLoginid());
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
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("prod prev2 "+getLoginid());
       AdminDashboardController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
     
    private void setCellTable(){
        column_pid.setCellValueFactory(new PropertyValueFactory<>("productid"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("productname"));
        column_description.setCellValueFactory(new PropertyValueFactory<>("productdescription"));
        column_quantity.setCellValueFactory(new PropertyValueFactory<>("productquantity"));
        column_baseprice.setCellValueFactory(new PropertyValueFactory<>("baseprice"));
        column_charges.setCellValueFactory(new PropertyValueFactory<>("charges"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select * from product");
            rs = pst.executeQuery();
            while(rs.next()){
                Double x = loadTotalBasePrice(rs.getString(1));
                data.add(new ProductList(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),
                    x,rs.getDouble(5),x+rs.getDouble(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        producttbl.setItems(data);
    }
    
    private Double loadTotalBasePrice(String productid){
        try {
            System.out.println(productid);
            pst = con.prepareStatement("select dbo.f_baseprice(?)");
            pst.setString(1,productid);
            rs1 = pst.executeQuery();
            while(rs1.next()){
                return rs1.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDatabase();
    }    
    
}
