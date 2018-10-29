package ksp.Couriers;

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
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button search;
  
  
    public void initialize() {}

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        AdminManager couriersManager = new AdminManager(mainManager.getScene(), mainManager.getStage());
        add.setOnAction((ActionEvent event) -> {
            couriersManager.navigateAdd(mainManager, loginManager, sessionID);
        });
        delete.setOnAction((ActionEvent event) -> {
            couriersManager.navigateDelete(mainManager, loginManager, sessionID);
        });
        search.setOnAction((ActionEvent event) -> {
            couriersManager.navigateSearch(mainManager, loginManager, sessionID);
        });
    }
}