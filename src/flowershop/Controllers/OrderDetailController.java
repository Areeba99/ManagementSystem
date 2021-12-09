/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;
/*
import flowershop.OrderList;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author areeb
 *//*
public class OrderDetailController implements Initializable {

    /**
     * Initializes the controller class.
     *//*
    @FXML
    private TableColumn<?, ?> column_id;

    @FXML
    private TableView<OrderList> ordertbl;

    @FXML
    private TableColumn<?, ?> column_total;

    @FXML
    private TableColumn<?, ?> column_name;

    @FXML
    private TableColumn<?, ?> column_price;

    @FXML
    private ImageView back;
    @FXML
    private TextField oid;
    @FXML
    private Button ok;

    @FXML
    private TableColumn<?, ?> column_qty;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }
    

    ObservableList<OrderList> data;
    private TableView tableview;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ResultSet rs1 = null;
    
    private void setCellTable(){
        column_id.setCellValueFactory(new PropertyValueFactory<>("productid"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("productname"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        column_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    
   /* private void loadDataFromDatabase(){
        Double x= 0.0;
       // loadDataFromDatabase();
        try {
            pst = con.prepareStatement("Select [order detail].productid,productname,charges,quantity from [order detail]\n" +
"inner join product\n" +
"on product.productid = [order detail].productid\n" +
"where orderid = ?");
        //    int orderid = Integer.parseInt(invoiceid.getText());
            pst.setInt(1,orderid);
            rs = pst.executeQuery();
            while(rs.next()){            
                Double price = loadTotalBasePrice(rs.getString(1)) + rs.getDouble(3);
                data.add(new OrderList(rs.getString(1),rs.getString(2),price,
                        rs.getInt(4),price*rs.getInt(4)));
                x=x+(price*rs.getInt(4));
              //  subtotal.setText(Double.toString(x));
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
    }*//*
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
       // loadDataFromDatabase();
    }    
    
    @FXML
    private void ok(ActionEvent ae){
        ok.getScene().getWindow().hide();
    }
    
    public void setData(String orderid) {
        oid.setText(""+orderid);
        System.out.println(orderid);
    }
    
}
*/