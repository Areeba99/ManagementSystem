/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import Dialog.AlertDialog;
import flowershop.OrderList;
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
public class InvoiceController implements Initializable {

    /**
     * Initializes the controller class.
     */
  
    @FXML
    private DatePicker date;
    @FXML
    private Button add;
    @FXML
    private Button refresh;
    @FXML
    private TextField empid;
    @FXML
    private TextField firstname;
    @FXML
    private TableView<OrderList> ordertbl;
    @FXML
    private TableColumn<?, ?> column_total;
    @FXML
    private TableColumn<?, ?> column_name;
    @FXML
    private TableColumn<?, ?> column_price;
    @FXML
    private TableColumn<?, ?> column_qty;
    @FXML
    private TableColumn<?, ?> column_id;
    @FXML
    private Button edit;
    @FXML
    private TextField additional;
    @FXML
    private Button increment;
    @FXML
    private TextField discount;
    @FXML
    private TextField prodid;
    @FXML
    private Button remove;
    @FXML
    private TextField lastname;
    @FXML
    private TextField subtotal;
    @FXML
    private TextField contact;
    @FXML
    private TextField qty;
    @FXML
    private TextField custid;
    @FXML
    private TextField invoiceid;
    @FXML
    private TextField netamount;
    @FXML
    private Button close;
    @FXML
    private Button calculate;
    @FXML
    private Label enter;
    @FXML
    private Label exist;
    @FXML
    private Label existprod;
    @FXML
    private Label chkfields;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        empid.setText(String.valueOf(getLoginid()));
    }
    
  //  private ObservableList<OrderList> data;
    ObservableList<OrderList> data;
    private TableView tableview;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ResultSet rs1 = null;
    
    @FXML
    private void loadCustomerData() throws SQLException{
        enter.setVisible(false);
        exist.setVisible(false);
            String sql = "Select fname,lname,contactno from customer where customerid = ?";
            if (custid.getText().isEmpty()){
                enter.setVisible(true);
            }
            else if(checkCustomer() == false){
                exist.setVisible(true);
                custid.clear();
            }
            else{
            try{
            int customerid = Integer.parseInt(custid.getText());
            pst = con.prepareStatement(sql);
            pst.setInt(1,customerid);
            rs = pst.executeQuery();
            while(rs.next()){
                String fname = rs.getString(1); 
                firstname.setText(fname);  
                String lname = rs.getString(2); 
                lastname.setText(lname); 
                String contactno = rs.getString(3); 
                contact.setText(contactno); 
               // setCellTable();
               // loadDataFromDatabase();
                prodid.setEditable(true);
                qty.setEditable(true);
                custid.setEditable(false);
             //   insertDataIntoDatabase();
             createOrder();
             invoiceid.setText(String.valueOf(fetchInvoiceID()));
            }            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
    }
    
    private boolean checkCustomer() throws SQLException{
        try{
        pst = con.prepareStatement("select customerid from customer where customerid = ?");
        String customerid = custid.getText();
        pst.setString(1,customerid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private int fetchInvoiceID(){
        try {
            pst = con.prepareStatement("select top 1 orderid from orders order by orderid desc");
            rs = pst.executeQuery();
            while(rs.next()){
                return (Integer.parseInt(rs.getString(1)));
            }
        } catch (SQLException ex) {
            invoiceid.setText(String.valueOf(1));
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }
    
    @FXML
    private void handleAddButtonAction() throws SQLException{
    //    if(invoiceid.getText().isEmpty() == true || checkOrder() == false)
      //      createOrder();
        
        insertDataIntoDatabase();
      //  setCellTable();
        ordertbl.getItems().clear();
        setCellTable();
        loadDataFromDatabase();
        
    }
    
    @FXML
    private void handleUpdateButtonAction() throws SQLException{
    //    if(invoiceid.getText().isEmpty() == true || checkOrder() == false)
      //      AlertDialog.display("Alert", "Add products to the order first.");
        if(checkOrderContents() == false)
            AlertDialog.display("Alert", "This order does not contain any product.");
        else{
        updateDataOfDatabase();
        ordertbl.getItems().clear();
        setCellTable();
        loadDataFromDatabase();
        }
    }
    
    @FXML
    private void handleDeleteButtonAction() throws SQLException{
     //   if(invoiceid.getText().isEmpty() == true || checkOrder() == false)
       //     AlertDialog.display("Alert", "Add products to the order first.");
        if(checkOrderContents() == false)
            AlertDialog.display("Alert", "This order does not contain any product.");
        else{
        deleteDataFromDatabase();
        ordertbl.getItems().clear();
        setCellTable();
        loadDataFromDatabase();
        if(checkOrderContents() == false)
            deleteOrder();
        }
    }
    
    private void createOrder(){
        String sql = "insert into orders(orderdate,employeeid,customerid) values (?,?,?)";
        String orderdate = String.valueOf(date.getValue());
        int employeeid = Integer.parseInt(empid.getText());
        int customerid = Integer.parseInt(custid.getText());
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,orderdate);
            pst.setInt(2,employeeid);
            pst.setInt(3,customerid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deleteOrder(){
        String sql = "delete from orders where orderid = ?";
         int orderid = Integer.parseInt(invoiceid.getText());
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,orderid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkOrder(){
        String sql = "select orderid from orders where orderid = ?";
        int orderid = Integer.parseInt(invoiceid.getText());
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,orderid);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private boolean checkOrderContents(){
        String sql = "select * from [order detail] where orderid = ?";
        int orderid = Integer.parseInt(invoiceid.getText());
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,orderid);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void setCellTable(){
        column_id.setCellValueFactory(new PropertyValueFactory<>("productid"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("productname"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        column_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    
    private void loadDataFromDatabase(){
        Double x= 0.0;
       // loadDataFromDatabase();
        try {
            pst = con.prepareStatement("Select [order detail].productid,productname,charges,quantity from [order detail]\n" +
"inner join product\n" +
"on product.productid = [order detail].productid\n" +
"where orderid = ?");
            int orderid = Integer.parseInt(invoiceid.getText());
            pst.setInt(1,orderid);
            rs = pst.executeQuery();
            while(rs.next()){            
                Double price = loadTotalBasePrice(rs.getString(1)) + rs.getDouble(3);
                data.add(new OrderList(rs.getString(1),rs.getString(2),price,
                        rs.getInt(4),price*rs.getInt(4)));
                x=x+(price*rs.getInt(4));
                subtotal.setText(Double.toString(x));
              //  calculateBill();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ordertbl.setItems(data);
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
    
    private int quantity(){
        String sql = "select productquantity from product where productid = ?";
        String productid = prodid.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,productid);
            rs = pst.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    private void insertDataIntoDatabase() throws SQLException{
        chkfields.setVisible(false);
        existprod.setVisible(false);
        String sql = "insert into [order detail] values (?,?,?)";
        int orderid = Integer.parseInt(invoiceid.getText());
        int quantity;
        if(qty.getText().isEmpty() == true){
            quantity = 1;
        }
        else{
            quantity = Integer.parseInt(qty.getText());}
        if(prodid.getText().isEmpty() == true){
            chkfields.setVisible(true);
        }
        else if(checkProduct() == false){
            existprod.setVisible(true);
        }
        else if(checkOrderProduct() == true){
            AlertDialog.display("Redundancy Check", "This product already exists in details of this order.");
        }
        else if(quantity() < quantity){
            AlertDialog.display("Alert", "Please enter a lower quantity.");}
        else{
            String productid = prodid.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,orderid);
            pst.setString(2,productid);
            pst.setInt(3,quantity);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Product added to the order successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private boolean checkOrderProduct(){
        try{
        pst = con.prepareStatement("select orderid,productid from [order detail] where orderid = ? and productid = ?");
        String productid = prodid.getText();
        int orderid = Integer.parseInt(invoiceid.getText());
        pst.setInt(1,orderid);
        pst.setString(2,productid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void updateDataOfDatabase() throws SQLException{
        chkfields.setVisible(false);
        existprod.setVisible(false);
        String sql = "update [order detail] set quantity = ? where orderid = ? and productid = ?";
        int orderid = Integer.parseInt(invoiceid.getText());
        int quantity;
        if(qty.getText().isEmpty() == true){
            quantity = 1;
        }
        else{
            quantity = Integer.parseInt(qty.getText());}
        if(prodid.getText().isEmpty() == true){
            chkfields.setVisible(true);
        }
        else if(checkProduct() == false){
            existprod.setVisible(true);
        }
        else if(checkOrderProduct() == false){
            AlertDialog.display("Alert", "This product does not exist in details of this order.");
        }
        else if(quantity() < quantity){
            AlertDialog.display("Alert", "Please enter a lower quantity.");}
        else{
        String productid = prodid.getText();             
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,quantity);
            pst.setInt(2,orderid);
            pst.setString(3,productid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Product updated from the order successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void deleteDataFromDatabase() throws SQLException{
        chkfields.setVisible(false);
        existprod.setVisible(false);
        String sql = "delete from [Order detail] where orderid = ? and productid = ?";
        int orderid = Integer.parseInt(invoiceid.getText());
        if(prodid.getText().isEmpty() == true){
            chkfields.setVisible(true);
        }
        else if(checkProduct() == false){
            existprod.setVisible(true);
        }
        else if(checkOrderProduct() == false){
            AlertDialog.display("Alert", "This product does not exist in details of this order.");
        }
        else{
        String productid = prodid.getText();
                
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1,orderid);
            pst.setString(2,productid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Product deleted from the order successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        prodid.clear();
        qty.clear();
    }
    
    public void toPreviousScreen(){
        if(checkOrderContents() == false){
            deleteOrder();
        }
        close.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/OrderHistory.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("inv prev "+getLoginid());
       OrderHistoryController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    private void calculateBill(){
        Double dis = Double.parseDouble(discount.getText());
        Double addi = Double.parseDouble(additional.getText());
        Double net = (dis*Double.parseDouble(subtotal.getText()))+addi;
        netamount.setText(Double.toString(net));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
        data = FXCollections.observableArrayList();
        date.setValue(LocalDate.now());
       // add.setOnAction(event -> handleAddButtonAction());
        //edit.setOnAction(event -> handleUpdateButtonAction());
        //remove.setOnAction(event -> handleDeleteButtonAction());
       // refresh.setOnAction(event -> loadDataFromDatabase());
    }    
    
}
