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
public class AddProductChargesController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Label exist;

    @FXML
    private ImageView back;

    @FXML
    private Button update;
    @FXML
    private Button go;

    @FXML
    private TextField id;

    @FXML
    private Label enter;

    @FXML
    private TextField unitprice;
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
        String sql = "update product set charges = ? where productid = ?";
        Double charges;
        if(unitprice.getText().isEmpty() == true)
            charges = null;
        else
            charges = Double.parseDouble(unitprice.getText());
        String productid = id.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setDouble(1,charges);
            pst.setString(2,productid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Product charges updated successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddProductChargesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clearData(){
        id.clear();
        unitprice.clear();
        id.setEditable(true);
        unitprice.setEditable(false);
        enter.setVisible(false);
        exist.setVisible(false);
        update.setDisable(true);
    }
    
    @FXML
    private void loadDataFromDatabase() throws SQLException{
        enter.setVisible(false);
        exist.setVisible(false);
        id.setEditable(false);
            String sql = "Select charges from product where productid = ?";
            if (id.getText().isEmpty()){
                enter.setVisible(true);
                id.setEditable(true);
            }
            
            else if(checkProduct() == false){
                exist.setVisible(true);
                id.clear();
                id.setEditable(true);
            }
            else{
            update.setDisable(false);
            unitprice.setEditable(true);
            String productid = id.getText();
            try{
            pst = con.prepareStatement(sql);
            pst.setString(1,productid);
            rs = pst.executeQuery();
            while(rs.next()){
                Double price = rs.getDouble(1); 
                unitprice.setText(Double.toString(price));
            }            
        } catch (SQLException ex) {
            Logger.getLogger(AddProductChargesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
     
    private boolean checkProduct() throws SQLException{
        System.out.println("3");
        try{
        pst = con.prepareStatement("select productid from product where productid = ?");
        String productid = id.getText();
        pst.setString(1,productid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddProductChargesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
            Logger.getLogger(AddProductChargesController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("update prev "+getLoginid());
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
        con = dba.DbConnection.flowerConnection();
    }    
    
}
