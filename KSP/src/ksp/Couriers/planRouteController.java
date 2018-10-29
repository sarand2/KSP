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
public class planRouteController implements Initializable {
    @FXML private Button logoutButton;
    @FXML private Label  sessionLabel;
    @FXML private Button backButton;
    @FXML private Button showOnMap;
    @FXML private Button planRoute;
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
        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        CourierManager couriersManager = new CourierManager(mainManager.getScene(), mainManager.getStage());
//        getList.setOnAction((ActionEvent event) -> {
//            couriersManager.navig(mainManager, loginManager, sessionID);
//        });
//        accept.setOnAction((ActionEvent event) -> {
//            couriersManager.navigateDelete(mainManager, loginManager, sessionID);
//        });
    }
    
}