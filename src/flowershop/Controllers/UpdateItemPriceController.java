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

public class UpdateItemPriceController implements Initializable {
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
        String sql = "update item set price = ? where itemid = ?";
        Double price;
        if(unitprice.getText().isEmpty() == true)
            price = null;
        else
            price = Double.parseDouble(unitprice.getText());
        String itemid = id.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setDouble(1,price);
            pst.setString(2,itemid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item price updated successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateItemPriceController.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "Select price from item where itemid = ?";
            if (id.getText().isEmpty()){
                enter.setVisible(true);
                id.setEditable(true);
            }
            
            else if(checkItem() == false){
                exist.setVisible(true);
                id.clear();
                id.setEditable(true);
            }
            else{
            update.setDisable(false);
            unitprice.setEditable(true);
            String itemid = id.getText();
            try{
            pst = con.prepareStatement(sql);
            pst.setString(1,itemid);
            rs = pst.executeQuery();
            while(rs.next()){
                Double price = rs.getDouble(1); 
                unitprice.setText(Double.toString(price));
            }            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateItemPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
     
    private boolean checkItem() throws SQLException{
        System.out.println("3");
        try{
        pst = con.prepareStatement("select itemid from item where itemid = ?");
        String itemid = id.getText();
        pst.setString(1,itemid);
        rs = pst.executeQuery();
        while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UpdateItemPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Item.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(UpdateItemPriceController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("update prev "+getLoginid());
       ItemController e = loader.getController();
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
