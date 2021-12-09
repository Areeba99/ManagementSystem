/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import Dialog.AlertDialog;
import flowershop.AddPurchaseList;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
public class PurchaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker date;
    @FXML
    private Button add;
    @FXML
    private Button edit;
    @FXML
    private TextField empid;
    @FXML
    private TextField supid;
    @FXML
    private TextField price;
    @FXML
    private TableView<AddPurchaseList> addpurchasetbl;
    @FXML
    private TableColumn<?, ?> column_name;
    @FXML
    private TableColumn<?, ?> column_price;
    @FXML
    private TableColumn<?, ?> column_qty;
    @FXML
    private Button remove;
    @FXML
    private TableColumn<?, ?> column_id;
    @FXML
    private TextField iid;
    @FXML
    private TableColumn<?, ?> column_description;
    @FXML
    private TextField qty;
    @FXML
    private Button close;
    @FXML
    private Label chkfields;
    @FXML
    private Label exist;
    @FXML
    private Label existitem;
    @FXML
    private Label enter;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        empid.setText(String.valueOf(getLoginid()));
    }
    
    ObservableList<AddPurchaseList> data;
    private TableView tableview;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
        data = FXCollections.observableArrayList();
        date.setValue(LocalDate.now());
     //   add.setOnAction(event -> handleAddButtonAction());
       // edit.setOnAction(event -> handleUpdateButtonAction());
       // remove.setOnAction(event -> handleDeleteButtonAction());
       // close.setOnAction(event -> toPreviousScreen());
    }    
    
    @FXML
    private void handleAddButtonAction() throws SQLException{
        insertDataIntoDatabase();
      //  setCellTable();
        addpurchasetbl.getItems().clear();
        setCellTable();
        loadDataFromDatabase();
        System.out.println("done");
    }
    
    @FXML
    private void handleUpdateButtonAction() throws SQLException{
        updateDataOfDatabase();
        addpurchasetbl.getItems().clear();
        setCellTable();
        loadDataFromDatabase();
    }
    
    @FXML
    private void handleDeleteButtonAction() throws SQLException{
        deleteDataFromDatabase();
        addpurchasetbl.getItems().clear();
        setCellTable();
        loadDataFromDatabase();
    }
    
    @FXML
    private void handleGoButton() throws SQLException{
        enter.setVisible(false);
        exist.setVisible(false);
        if(supid.getText().isEmpty() == true)
            enter.setVisible(true);
        else if(checkSupplier() == false)
            exist.setVisible(true);
        else{
            iid.setEditable(true);
            qty.setEditable(true);
            price.setEditable(true);
            supid.setEditable(false);
            setCellTable();
            loadDataFromDatabase();
        }
    }
    
    private boolean checkSupplier() throws SQLException{
        try{
        pst = con.prepareStatement("select supplierid from supplier where supplierid = ?");
        String supplierid = supid.getText();
        pst.setString(1,supplierid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
   /* private boolean checkSupplyItems() throws SQLException{
        System.out.println("1");
        
        String sql = "select * from [supply details] where itemid = ? and supplierid = ?"
                + "and employeeid = ? and supplydate = ?";
            System.out.println("2");
        String itemid = iid.getText();
        int supplierid = Integer.parseInt(supid.getText());
        int employeeid = Integer.parseInt(empid.getText());
        String supplydate = String.valueOf(date.getValue());
            System.out.println("3");
        try{
            pst = con.prepareStatement(sql);
        pst.setString(1,itemid);
        pst.setInt(2,supplierid);
        pst.setInt(3,employeeid);
        pst.setString(4,supplydate);
            System.out.println("4");
        rs = pst.executeQuery();
        while(rs.next()){
            System.out.println("5");
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("6");
        return false;
    }*/
    
    private boolean checkSupplyItem(){
        String sql = "exec sp_checkSupply ?,?,?,?";
        String itemid = iid.getText();
        int supplierid = Integer.parseInt(supid.getText());
        int employeeid = Integer.parseInt(empid.getText());
        String supplydate = String.valueOf(date.getValue());
        try{
            pst = con.prepareStatement(sql);
        pst.setString(1,itemid);
        pst.setInt(2,supplierid);
        pst.setInt(3,employeeid);
        pst.setString(4,supplydate);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void setCellTable(){
        column_id.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        column_description.setCellValueFactory(new PropertyValueFactory<>("itemdescription"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
    
    private void insertDataIntoDatabase() throws SQLException{
        chkfields.setVisible(false);
        existitem.setVisible(false);
        String sql = "insert into [supply details] values (?,?,?,?,?,?)";
        int supplierid = Integer.parseInt(supid.getText());
        int employeeid = Integer.parseInt(empid.getText());
        int quantity;
        if(qty.getText().isEmpty() == true){
            quantity = 1;
        }
        else{
            quantity = Integer.parseInt(qty.getText());}
        Double unitprice;
        if(price.getText().isEmpty() == true){
            unitprice = 0.0;
        }
        else{
            unitprice = Double.parseDouble(price.getText());}
        String supplydate = String.valueOf(date.getValue());
        if(iid.getText().isEmpty() == true || supid.getText().isEmpty() == true){
            chkfields.setVisible(true);}
        else if(checkItem() == false){
            existitem.setVisible(true);}
        else if(checkSupplyItem() == true){
            AlertDialog.display("Redundancy Check", "This item already exists in details of this supply.");}
        else{
            String itemid = iid.getText();
        try {
            
            pst = con.prepareStatement(sql);
            
            pst.setInt(1,supplierid);
            pst.setInt(2,employeeid);
            pst.setString(3,itemid);
            pst.setString(4,supplydate);
            pst.setInt(5,quantity);
            pst.setDouble(6,unitprice);
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item added to the supply successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void updateDataOfDatabase() throws SQLException{
        chkfields.setVisible(false);
        existitem.setVisible(false);
        String sql = "update [supply details] set quantity = ?,unitprice = ? where employeeid = ? and "
                + "supplierid = ? and supplydate = ? and itemid = ?";
        
        int supplierid = Integer.parseInt(supid.getText());
        int employeeid = Integer.parseInt(empid.getText());
        int quantity;
        if(qty.getText().isEmpty() == true){
            quantity = 1;
        }
        else{
            quantity = Integer.parseInt(qty.getText());}
        Double unitprice;
        if(price.getText().isEmpty() == true){
            unitprice = 0.0;
        }
        else{
            unitprice = Double.parseDouble(price.getText());}
        String supplydate = String.valueOf(date.getValue());
        if(iid.getText().isEmpty() == true || supid.getText().isEmpty() == true){
            chkfields.setVisible(true);}
        else if(checkItem() == false){
            existitem.setVisible(true);}
        else if(checkSupplyItem() == false){
            AlertDialog.display("Alert", "This item does not exist in details of this supply.");}
        else{     
            String itemid = iid.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,quantity);
            pst.setDouble(2,unitprice);
            pst.setInt(3,employeeid);
            pst.setInt(4,supplierid);
            pst.setString(5,supplydate);
            pst.setString(6,itemid);
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item updated from the supply successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void deleteDataFromDatabase() throws SQLException{
        chkfields.setVisible(false);
        existitem.setVisible(false);
        String sql = "delete from [supply details] where employeeid = ? and supplierid = ? and supplydate = ? and itemid = ?";
        String itemid = iid.getText();
        int supplierid = Integer.parseInt(supid.getText());
        int employeeid = Integer.parseInt(empid.getText());
        String supplydate = String.valueOf(date.getValue());
        if(iid.getText().isEmpty() == true || supid.getText().isEmpty() == true){
            chkfields.setVisible(true);}
        else if(checkItem() == false){
            existitem.setVisible(true);}
        else if(checkSupplyItem() == false){
            AlertDialog.display("Alert", "This item does not exist in details of this supply.");}
        else{
                
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,employeeid);
            pst.setInt(2,supplierid);
            pst.setString(3,supplydate);
            pst.setString(4,itemid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item deleted from the supply successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void loadDataFromDatabase(){
        try {
            pst = con.prepareStatement("Select [supply details].itemid,itemname,itemdescription,unitprice,[supply details].quantity from [supply details]\n" +
"inner join item \n" +
"on item.itemid = [supply details].itemid\n" +
"where employeeid = ? and supplierid = ? and supplydate = ?");
            int employeeid = Integer.parseInt(empid.getText());
            int supplierid = Integer.parseInt(supid.getText());
            String supplydate = String.valueOf(date.getValue());
            pst.setInt(1,employeeid);
            pst.setInt(2,supplierid);
            pst.setString(3,supplydate);
            rs = pst.executeQuery();
            while(rs.next()){      
                System.out.println("id and price: "+rs.getString(1)+rs.getDouble(4));
                data.add(new AddPurchaseList(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getDouble(4),rs.getInt(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addpurchasetbl.setItems(data);
    }
    
    private void clearData(){
        iid.clear();
        qty.clear();
        price.clear();
    }
    
    @FXML
    public void toPreviousScreen(){
        close.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/PurchaseHistory.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("inv prev "+getLoginid());
       PurchaseHistoryController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
}
