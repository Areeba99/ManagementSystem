/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop.Controllers;

import Dialog.AlertDialog;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class AddProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField description;
    @FXML
    private TextField unitprice;
    @FXML
    private Button add;
    @FXML
    private Button additems;
    @FXML
    private ImageView back;
    @FXML
    private Label chkfields;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
    }
    
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        chkfields.setVisible(false);
        String sql = "insert into product(productid,productname,productdescription) values (?,?,?)";
        String productid = id.getText();
        String productname = name.getText();
        String productdescription;
        if(description.getText().isEmpty() == true)
            productdescription = null;
        else
            productdescription = description.getText();
        if(id.getText().isEmpty() == true  || name.getText().isEmpty() == true)
            chkfields.setVisible(true);
        else if(loadDataFromDatabase() == true){
             AlertDialog.display("Redundancy Check", "This product already exists.");
        }
        else{
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,productid);
            pst.setString(2,productname);
            pst.setString(3,productdescription);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Product Added successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
        
    private boolean loadDataFromDatabase() throws SQLException{
        try {
            pst = con.prepareStatement("Select productid from product where productid = ?");
            String productid = id.getText();
            pst.setString(1,productid);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void clearData(){
        id.clear();
        name.clear();
        description.clear();
        chkfields.setVisible(false);
    }
    
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Product.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
       ProductController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @FXML
    public void toProductDetailScreen(){
        additems.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/ProductDetail.fxml"));
        System.out.println("1");
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("2");
       ProductDetailController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
        System.out.println("3");
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
    }    
    
}
