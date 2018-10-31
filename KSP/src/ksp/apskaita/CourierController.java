package ksp.apskaita;

import ksp.Couriers.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ksp.LoginManager;
import ksp.MainViewManager;

public class CourierController {
    @FXML private Button logoutButton;
    @FXML private Label  sessionLabel;
    @FXML private Button backButton;
    @FXML
    private Button routes;
    @FXML
    private Button checkCouriers;
  
  
    public void initialize() {}

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        CourierManager courierManager = new CourierManager(mainManager.getScene(), mainManager.getStage());
        routes.setOnAction((ActionEvent event) -> {
            courierManager.navigateRoutes(mainManager, loginManager, sessionID);
        });
        checkCouriers.setOnAction((ActionEvent event) -> {
            courierManager.navigateCheckCouriers(mainManager, loginManager, sessionID);
        });
    }
}