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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    @FXML private WebView web;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    } 
    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID, String address) {
        final WebEngine webEngine = web.getEngine();
        web.setZoom(0.8);
        webEngine.load(address);
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
            adminManager.navigateSearch(mainManager, loginManager, sessionID,address);
        } else {
                 CourierManager couriersManager = new CourierManager(mainManager.getScene(), mainManager.getStage());
                 couriersManager.navigatePlanRoute(mainManager, loginManager, sessionID);
             }
        });
    }
}
