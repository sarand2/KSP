/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.Couriers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ksp.LoginManager;
import ksp.MainViewManager;

public class addCourierController implements Initializable {
    dbConnection dbc = new dbConnection();
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button main;
    @FXML
    private Button backButton;
    @FXML
    private TextField personalID;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private Label status;
    @FXML
    public ComboBox category;
    
    @FXML
    private Button add;
    
   public void addButton(ActionEvent e){
       if(personalID.getText() != null && name.getText() != null && surname.getText() != null && category.getValue() != null){
           String personalID = "\"" + this.personalID.getText() + "\"";
           String name = "\"" + this.name.getText() + "\"";
           String surname = "\"" + this.surname.getText() + "\"";
           String category = "\"" + this.category.getValue().toString() + "\"";
           String query = "INSERT INTO kurjeriai (asmens_kodas,vardas,pavarde,auto_kategorija) values("+ personalID+","+name+","+surname+","+category+")";
           System.out.println("Sending query: " + query);
           boolean ok = dbc.executeUpdate(query);
           if(ok){
              status.setText("Naujas kurjerio įrašas sukurtas.");
           }
           else {
               status.setText("Nepavyko sukurti kurjerio įrašo.");
           }
       } else {
           status.setText("Prašome užpildyti visus laukelius.");
       }
   }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    void initView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        
        sessionLabel.setText(sessionID);
        category.getItems().addAll("B","BE","C1","C1E");
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        backButton.setOnAction((ActionEvent event) -> {
           mainManager.navigateCouriers(loginManager, sessionID);
        });
    }
    
}
