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
import javafx.scene.control.Label;
import ksp.LoginManager;
import ksp.MainViewManager;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class showMapController implements Initializable {
    @FXML private Button logoutButton;
    @FXML private Label  sessionLabel;
    @FXML private Button backButton;
    @FXML private Button main;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    } 
    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        backButton.setOnAction((ActionEvent event) -> {
             if(sessionID.contains("admin")){
            AdminManager adminManager = new AdminManager(mainManager.getScene(), mainManager.getStage());
            adminManager.navigateSearch(mainManager, loginManager, sessionID);
        } else {
                 CourierManager couriersManager = new CourierManager(mainManager.getScene(), mainManager.getStage());
                 couriersManager.navigatePlanRoute(mainManager, loginManager, sessionID);
             }
        });
    }
    
}
