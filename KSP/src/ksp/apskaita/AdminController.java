package ksp.apskaita;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ksp.LoginManager;
import ksp.MainViewManager;

public class AdminController {
    @FXML private Button logoutButton;
    @FXML private Label  sessionLabel;
    @FXML private Button backButton;
    @FXML
    private Button productsDelivery;
    @FXML
    private Button checkCouriers;
    @FXML
    private Button routes;
    @FXML
    private Button members;
    @FXML
    private Button products;
  
    public void initialize() {}

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });  
        AdminManager adminManager = new AdminManager(mainManager.getScene(), mainManager.getStage());
        productsDelivery.setOnAction((ActionEvent event) -> {
            adminManager.navigateProductsDelivery(mainManager, loginManager, sessionID);
        });
        checkCouriers.setOnAction((ActionEvent event) -> {
            adminManager.navigateCheckCouriers(mainManager, loginManager, sessionID);
        });
        routes.setOnAction((ActionEvent event) -> {
            adminManager.navigateRoutes(mainManager, loginManager, sessionID);
        });
        members.setOnAction((ActionEvent event) -> {
            adminManager.navigateMembers(mainManager, loginManager, sessionID);
        });
        products.setOnAction((ActionEvent event) -> {
            adminManager.navigateProducts(mainManager, loginManager, sessionID);
        });
    }
}