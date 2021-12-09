/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import flowershop.ProfitList;
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
public class ProfitController implements Initializable {
    /*
    
    @FXML
    private TableColumn<?, ?> column_productid;
    @FXML
    private TableColumn<?, ?> column_purchaseprice;
    @FXML
    private TableColumn<?, ?> column_sellingprice;
    @FXML
    private TableColumn<?, ?> column_profit;
    @FXML
    private ImageView back;
    @FXML
    private TableView<ProfitList> profittbl;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("7");
    }
    

    ObservableList<ProfitList> data;
    private TableView tableview;
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private PreparedStatement pst1 = null;
    private PreparedStatement pst2 = null;
    private ResultSet rs = null;
    private ResultSet rs1 = null;
    private ResultSet rs2 = null;
    
    /**
     * Initializes the controller class.
     */
    /*
    private void setCellTable(){
        column_productid.setCellValueFactory(new PropertyValueFactory<>("productid"));
        column_purchaseprice.setCellValueFactory(new PropertyValueFactory<>("purchaseprice"));
        column_sellingprice.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));
        column_profit.setCellValueFactory(new PropertyValueFactory<>("profit"));
    }
    /*
    private void loadDataFromDatabase(){
        System.out.println("88");
        try {
            pst = con.prepareStatement("Select productid,charges from product");
            rs = pst.executeQuery();
            System.out.println("4");
            while(rs.next()){
                Double y = loadTotalPurchasePrice(rs.getString(1));
                Double x = loadTotalBasePrice(rs.getString(1))+rs.getDouble(2);
                data.add(new ProfitList(rs.getString(1),y,x,x-y));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        profittbl.setItems(data);
    }
    
    private Double loadTotalBasePrice(String productid){
        try {
            System.out.println(productid);
            pst1 = con.prepareStatement("select dbo.f_baseprice(?)");
            System.out.println("5");
            pst1.setString(1,productid);
            rs1 = pst1.executeQuery();
            while(rs1.next()){
                return rs1.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }
    
    private Double loadTotalPurchasePrice(String productid){
        try{
            pst2 = con.prepareStatement("exec sp_purchaseprice ?");
            pst2.setString(1,productid);
            System.out.println("6");
            rs2 = pst2.executeQuery();
            while(rs2.next()){
                return rs2.getDouble(1);
            }
        }
         catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //System.out.println("1");
   //     con = dba.DbConnection.flowerConnection();
     //   System.out.println("2");
       // data = FXCollections.observableArrayList();
       // setCellTable();
       // System.out.println("3");
        //loadDataFromDatabase();
    }    
/*
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/OrderHistory.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(ProfitController.class.getName()).log(Level.SEVERE, null, ex);
        }
       OrderHistoryController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
 */   
}
