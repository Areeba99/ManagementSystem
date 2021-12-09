/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import Dialog.AlertDialog;
import flowershop.ProductDetailList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class ProductDetailController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button add;
    @FXML
    private TableColumn<?, ?> column_total;
    @FXML
    private TextField iid;
 //   @FXML
   // private Button edit;
    @FXML
    private TableColumn<?, ?> column_itemid;
    @FXML
    private TableColumn<?, ?> column_price;
    @FXML
    private TextField prodname;
    @FXML
    private TextField prodid;
    @FXML
    private TableColumn<?, ?> column_quantity;
    @FXML
    private TableColumn<?, ?> column_itemname;
    @FXML
    private Button remove;
    @FXML
    private TableView<ProductDetailList> productdetailtbl;
    @FXML
    private TextField qty;
    @FXML
    private TableColumn<?, ?> column_productid;
    @FXML
    private Button close;
    @FXML
    private TextField base;
    @FXML
    private Label enter;
    @FXML
    private Label exist;
    @FXML
    private Label existitem;
    @FXML
    private Label nec;
    @FXML
    private Label chkfields;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("6");
    }
    
    
    ObservableList<ProductDetailList> data;
    private TableView tableview;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    private void setCellTable(){
        System.out.println("7");
        column_productid.setCellValueFactory(new PropertyValueFactory<>("productid"));
        column_itemid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        column_itemname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        column_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        column_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    
    @FXML
    private void handleAddButtonAction() throws SQLException{
   //     loadProductName();
        System.out.println("8");
        insertDataIntoDatabase();
        productdetailtbl.getItems().clear();
        base.clear();
        setCellTable();
        loadDataFromDatabase();
    }
    
    @FXML
    private void handleUpdateButtonAction() throws SQLException{
    //    loadProductName();
        System.out.println("8");
        updateDataOfDatabase();
        productdetailtbl.getItems().clear();
        base.clear();
        setCellTable();
        loadDataFromDatabase();
    }
    
    @FXML
    private void handleDeleteButtonAction() throws SQLException{
    //    loadProductName();
        System.out.println("8");
        deleteDataFromDatabase();
        productdetailtbl.getItems().clear();
        base.clear();
        setCellTable();
        loadDataFromDatabase();
    }
     
    private void insertDataIntoDatabase() throws SQLException{
        chkfields.setVisible(false);
        existitem.setVisible(false);
        String sql = "insert into [product detail] values (?,?,?)";
        String itemid = iid.getText();    
        String productid = prodid.getText();
        int quantity;
        if(qty.getText().isEmpty() == true){
            quantity = 1;
        }
        else{
            quantity = Integer.parseInt(qty.getText());}
        if(iid.getText().isEmpty() == true){
            chkfields.setVisible(true);
        }
        else if(checkItem() == false){
            existitem.setVisible(true);
        }
        else if(checkItemProduct() == true){
            AlertDialog.display("Redundancy Check", "This item already exists in details of this product.");
        }
        else{
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,productid);
            pst.setString(2,itemid);
            pst.setInt(3,quantity);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item Added to Product successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void updateDataOfDatabase() throws SQLException{
        String sql = "update [product detail] set quantity = ? where productid = ? and itemid = ?";
        String itemid = iid.getText();    
        String productid = prodid.getText();
        int quantity;
        if(qty.getText().isEmpty() == true){
            quantity = 1;
        }
        else{
            quantity = Integer.parseInt(qty.getText());}
        if(iid.getText().isEmpty() == true){
            chkfields.setVisible(true);
        }
        else if(checkItem() == false){
            existitem.setVisible(true);
        }
        else if(checkItemProduct() == false){
            AlertDialog.display("Redundancy Check", "This item does not exist in details of this product.");
        }
        else{               
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,quantity);
            pst.setString(2,productid);
            pst.setString(3,itemid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item updated from Product successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void deleteDataFromDatabase() throws SQLException{
        chkfields.setVisible(false);
        existitem.setVisible(false);
        String sql = "delete from [product detail] where productid = ? and itemid = ?";
        String productid = prodid.getText();
        String itemid = iid.getText();
        if(iid.getText().isEmpty() == true){
            chkfields.setVisible(true);
        }
        else if(checkItem() == false){
            existitem.setVisible(true);
        }
        else if(checkItemProduct() == false){
            AlertDialog.display("Alert", "This item does not exist in details of this product.");
        }
        else{
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,productid);
            pst.setString(2,itemid);
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item Deleted from Product successfully.");
                clearData();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        iid.clear();
        qty.clear();
    }
    
    private boolean checkItemProduct(){
        try{
        pst = con.prepareStatement("select productid,itemid from [product detail] where productid = ? and itemid = ?");
        String productid = prodid.getText();
        String itemid = iid.getText();
        pst.setString(1,productid);
        pst.setString(2,itemid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @FXML
    private void loadProductName() throws SQLException{
        enter.setVisible(false);
        exist.setVisible(false);
            String sql = "Select productname from product where productid = ?";
            if (prodid.getText().isEmpty()){
                enter.setVisible(true);
            }
            
            else if(checkProduct() == false){
                exist.setVisible(true);
                prodid.clear();
            }
            else{
            try{
            String productid = prodid.getText();
            pst = con.prepareStatement(sql);
            pst.setString(1,productid);
            rs = pst.executeQuery();
            while(rs.next()){
                String productname = rs.getString(1); 
                prodname.setText(productname);  
                setCellTable();
                loadDataFromDatabase();
                iid.setEditable(true);
                qty.setEditable(true);
             //   insertDataIntoDatabase();
            }            
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
    }
     
    private boolean checkProduct() throws SQLException{
        try{
        pst = con.prepareStatement("select productid from product where productid = ?");
        String productid = prodid.getText();
        pst.setString(1,productid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private boolean checkItem() throws SQLException{
        try{
        pst = con.prepareStatement("select itemid from item where itemid = ?");
        String itemid = iid.getText();
        pst.setString(1,itemid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void loadDataFromDatabase(){
        Double x= 0.0;
        try {
            pst = con.prepareStatement("Select productid,[product detail].itemid,itemname,price,[product detail].quantity from [product detail]\n" +
"inner join item\n" +
"on item.itemid = [product detail].itemid\n" +
"where productid = ?");
            String productid = prodid.getText();
            pst.setString(1,productid);
            rs = pst.executeQuery();
            while(rs.next()){            
                data.add(new ProductDetailList(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),
                        rs.getInt(5),rs.getDouble(4)*rs.getInt(5)));
                x=x+(rs.getDouble(4)*rs.getInt(5));
                base.setText(Double.toString(x));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        productdetailtbl.setItems(data);
    }
    
    @FXML
    public void toPreviousScreen(){
        close.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/flowershop/Views/Product.fxml"));
        try{
           loader.setRoot(loader.getRoot());
           loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProductController e = loader.getController();
        e.setLoginid(getLoginid());
        Parent root = loader.getRoot();
        Stage st = new Stage();
        st.setScene(new Scene(root));
        st.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("4");
        con = dba.DbConnection.flowerConnection();
        data = FXCollections.observableArrayList();
        System.out.println("5");
        iid.setEditable(false);
        qty.setEditable(false);
        
       // add.setOnAction(event -> handleAddButtonAction());
       // edit.setOnAction(event -> handleUpdateButtonAction());
       // remove.setOnAction(event -> handleDeleteButtonAction());
    }    
    
}
