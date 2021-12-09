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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class UpdateItemController implements Initializable {

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
    private ToggleGroup t_category;
    @FXML
    private RadioButton Flower;
    @FXML
    private RadioButton Leaf;
    @FXML
    private RadioButton Basket;
    @FXML
    private RadioButton Wrap;
    @FXML
    private RadioButton Other;
    @FXML
    private GridPane updategrid;
    @FXML
    private ImageView back;
    @FXML
    private Label chkfields;
    @FXML
    private Label enter;
    @FXML
    private Label exist;
    @FXML
    private Label nec;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("upd "+getLoginid());
    }
    
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        chkfields.setVisible(false);
        String sql = "update item set itemname = ?,itemdescription = ?,category = ? where itemid = ?";
        String itemname = name.getText();
        RadioButton chk = (RadioButton)t_category.getSelectedToggle(); // Cast object to radio button
        String category = chk.getText();
        String itemdescription;
        if(description.getText().isEmpty() == true){
            itemdescription = null;}
        else{
            itemdescription = description.getText();}
        String itemid = id.getText();
        
        if(id.getText().isEmpty() == true  || name.getText().isEmpty() == true)
            chkfields.setVisible(true);
        else{
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,itemname);
            pst.setString(2,itemdescription);
            pst.setString(3,category);
            pst.setString(4,itemid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item Updated successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    }
    
    @FXML
    private void loadDataFromDatabase() throws SQLException{
        enter.setVisible(false);
        exist.setVisible(false);
        id.setEditable(false);
        updategrid.setVisible(false);
            String sql = "Select itemname,itemdescription,category from item where itemid = ?";
            if (id.getText().isEmpty()){
              //  System.out.println("in if");
                enter.setVisible(true);
                id.setEditable(true);
            }
            
            else if(checkItem() == false){
            //    System.out.println("99");
                exist.setVisible(true);
                id.clear();
                id.setEditable(true);
            }
            else{
            String itemid = id.getText();
            try{
            pst = con.prepareStatement(sql);
            pst.setString(1,itemid);
            rs = pst.executeQuery();
            while(rs.next()){
                updategrid.setVisible(true);    
                nec.setVisible(true);
                String itemname = rs.getString(1); 
                name.setText(itemname);
                String itemdescription = rs.getString(2); 
                description.setText(itemdescription);
                String category = rs.getString(3);
                if(Flower.getText().equals(category))
                    t_category.selectToggle(Flower);
                else if (Leaf.getText().equals(category))
                    t_category.selectToggle(Leaf);
                else if (Basket.getText().equals(category))
                    t_category.selectToggle(Basket);
                else if (Wrap.getText().equals(category))
                    t_category.selectToggle(Wrap);
                else if (Other.getText().equals(category))
                    t_category.selectToggle(Other);
                
            }            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateItemController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void clearData(){
        id.clear();
        name.clear();
        t_category.selectToggle(Flower);
        description.clear();
        updategrid.setVisible(false);
        id.setEditable(true);
        nec.setVisible(false);
        enter.setVisible(false);
        exist.setVisible(false);
        chkfields.setVisible(false);
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
            Logger.getLogger(UpdateItemController.class.getName()).log(Level.SEVERE, null, ex);
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
