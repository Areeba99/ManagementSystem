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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author areeb
 */
public class UpdateProductController implements Initializable {

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
    private Button update;
    @FXML
    private GridPane productgrid;
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
    }
    
    
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
     @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        chkfields.setVisible(false);
        String sql = "update product set productname = ?,productdescription = ? where productid = ?";
        String productname = name.getText();
        String productdescription;
        if(description.getText().isEmpty() == true){
            productdescription = null;}
        else{
            productdescription = description.getText();}
        String productid = id.getText();
        if(name.getText().isEmpty() == true)
            chkfields.setVisible(true);
        else{
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1,productname);
            pst.setString(2,productdescription);
            pst.setString(3,productid);
            
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Connection successs!!");
                AlertDialog.display("Info", "Item Updated successfully.");
                clearData();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private void clearData(){
        id.clear();
        name.clear();
        description.clear();
        productgrid.setVisible(false);
        update.setVisible(false);
        nec.setVisible(false);
        id.setEditable(true);
    }
    
    @FXML
    private void loadDataFromDatabase() throws SQLException{
        enter.setVisible(false);
        exist.setVisible(false);
        id.setEditable(false);
        productgrid.setVisible(false);
            String sql = "Select productname,productdescription from product where productid = ?";
            if (id.getText().isEmpty()){
                System.out.println("in if");
                enter.setVisible(true);
                id.setEditable(true);
            }
            
            else if(checkProduct() == false){
                System.out.println("99");
                exist.setVisible(true);
                id.clear();
                id.setEditable(true);
            }
            try{
            String productid = id.getText();
            pst = con.prepareStatement(sql);
            pst.setString(1,productid);
            rs = pst.executeQuery();
            while(rs.next()){
                productgrid.setVisible(true);    
                nec.setVisible(true);
                String productname = rs.getString(1); 
                name.setText(productname);
                String productdescription = rs.getString(2); 
                description.setText(productdescription);                
            }            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkProduct() throws SQLException{
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
            Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @FXML
    public void toPreviousScreen(){
        back.getScene().getWindow().hide();
        //Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/flowershop/Views/Product.fxml"));
       try{
           loader.setRoot(loader.getRoot());
           loader.load();
       } catch (IOException ex) {
            Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("add prev "+getLoginid());
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
