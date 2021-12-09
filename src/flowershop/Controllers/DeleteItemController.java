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
public class DeleteItemController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField iid;
    @FXML
    private Button delete;
    @FXML
    private ImageView back;
    @FXML
    private Label enter;
    @FXML
    private Label exist;
    private int loginid;

    public int getLoginid() {
        return loginid;
    }

    public void setLoginid(int loginid) {
        this.loginid = loginid;
        System.out.println("del "+getLoginid());
    }
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        enter.setVisible(false);
        exist.setVisible(false);
        String sql = "delete from item where itemid = ?";
        if(iid.getText().isEmpty() == true){
            enter.setVisible(true);}
        else if (checkItem() == false){
            exist.setVisible(true);
            iid.clear();
        }
        else{
        String itemid = iid.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,itemid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item Deleted successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DeleteItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        iid.clear();
        enter.setVisible(false);
        exist.setVisible(false);
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
            Logger.getLogger(DeleteItemController.class.getName()).log(Level.SEVERE, null, ex);   
        }
        return false;
    }
    
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Item.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(DeleteItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("del prev "+getLoginid());
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
