/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import flowershop.ItemList;
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
public class ItemController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<ItemList> itemtbl;
    @FXML
    private TableColumn<?,?> column_iid;
    @FXML
    private TableColumn<?,?> column_name;
    @FXML
    private TableColumn<?,?> column_description;
    @FXML
    private TableColumn<?,?> column_quantity;
    @FXML
    private TableColumn<?,?> column_price;
    @FXML
    private TableColumn<?,?> column_category;
    @FXML
    private ImageView delete;
    @FXML
    private ImageView update;
    @FXML
    private ImageView add;
    @FXML
    private ImageView back;
    @FXML
    private ImageView updateprice;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("in item "+getLoginid());
        if(getLoginid() == 1){
            updateprice.setVisible(true);
            add.setVisible(false);
            update.setVisible(false);
            delete.setVisible(false);
        }
        else if(getLoginid() > 1){
            updateprice.setVisible(false);
            add.setVisible(true);
            update.setVisible(true);
            delete.setVisible(true);
        }
    }
    
    
    private ObservableList<ItemList> data;
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
    public void toAddItemScreen(){
        add.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/AddItem.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       AddItemController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toUpdateItemScreen(){
        update.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/UpdateItem.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       UpdateItemController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toDeleteItemScreen(){
        delete.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/EmployeePath/DeleteItem.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       DeleteItemController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toUpdateItemPriceScreen(){
        updateprice.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/AdminPath/UpdateItemPrice.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       UpdateItemPriceController e = loader.getController();
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
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("item prev "+getLoginid());
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
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("prev"+getLoginid());
       AdminDashboardController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    private void setCellTable(){
        column_iid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        column_description.setCellValueFactory(new PropertyValueFactory<>("itemdescription"));
        column_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_category.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
    
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select * from item");
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new ItemList(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),
                        rs.getDouble(5),rs.getString(6)));
                
               // System.out.println("em ontroller :"+data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        itemtbl.setItems(data);
    }
    
}
