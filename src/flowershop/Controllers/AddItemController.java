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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class AddItemController implements Initializable {
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
    private Button add;
    @FXML
    private Label chkfields;
    @FXML
    private ImageView back;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("add "+getLoginid());
    }
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        chkfields.setVisible(false);
        String sql = "insert into item(itemid,itemname,itemdescription,category) values (?,?,?,?)";
        String itemid = id.getText();
        String itemname = name.getText();
        String itemdescription;
        if(description.getText().isEmpty() == true)
            itemdescription = null;
        else
            itemdescription = description.getText();
        RadioButton chk = (RadioButton)t_category.getSelectedToggle(); // Cast object to radio button
        String category = chk.getText();
        if(id.getText().isEmpty() == true  || name.getText().isEmpty() == true)
            chkfields.setVisible(true);
        else if(loadItemIDFromDatabase() == true){
                AlertDialog.display("Redundancy Check", "This Item ID already exists.");
        }
        else if(loadItemNameFromDatabase() == true){
             AlertDialog.display("Redundancy Check", "An item with this name already exists.");
        }
        else{
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,itemid);
            pst.setString(2,itemname);
            pst.setString(3,itemdescription);
            pst.setString(4,category);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item Added successfully.");
                System.out.println("55");
                clearData();
                System.out.println("66");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        id.clear();
        name.clear();
        t_category.selectToggle(Flower);
        description.clear();
        chkfields.setVisible(false);
    }
    
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        //Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Item.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("add prev "+getLoginid());
       ItemController e = loader.getController();
       e.setLoginid(getLoginid());
       Parent root = loader.getRoot();
       Stage st = new Stage();
       st.setScene(new Scene(root));
       st.show();
    }
    
    private boolean loadItemIDFromDatabase() throws SQLException{
        try {
            pst = con.prepareStatement("Select itemid from item where itemid = ?");
            String itemid = id.getText();
            pst.setString(1,itemid);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private boolean loadItemNameFromDatabase() throws SQLException{
        try {
            pst = con.prepareStatement("Select itemname from item where itemname = ?");
            String itemname = name.getText();
            pst.setString(1,itemname);
            rs = pst.executeQuery();
            while(rs.next()){
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = dba.DbConnection.flowerConnection();
    }    
    
}
