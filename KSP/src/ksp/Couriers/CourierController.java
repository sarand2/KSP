package ksp.Couriers;

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
    private Button planRoute;
    @FXML
    private Button fillCar;
    @FXML
    private Button getReport;
  
  
    public void initialize() {}

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        CourierManager couriersManager = new CourierManager(mainManager.getScene(), mainManager.getStage());
        planRoute.setOnAction((ActionEvent event) -> {
            couriersManager.navigatePlanRoute(mainManager, loginManager, sessionID);
        });
        fillCar.setOnAction((ActionEvent event) -> {
            couriersManager.navigateFillCar(mainManager, loginManager, sessionID);
        });
        getReport.setOnAction((ActionEvent event) -> {
            couriersManager.navigateGetReport(mainManager, loginManager, sessionID);
        });
    }
}