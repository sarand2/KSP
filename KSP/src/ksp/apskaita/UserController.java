package ksp.apskaita;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ksp.LoginManager;
import ksp.MainViewManager;

public class UserController {
    @FXML private Button logoutButton;
    @FXML private Label  sessionLabel;
    @FXML private Button backButton;
    @FXML
    private Button products;
    @FXML
    private Button checkClients;
  
  
    public void initialize() {}

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        }); 
        UserManager userManager = new UserManager(mainManager.getScene(), mainManager.getStage());
        products.setOnAction((ActionEvent event) -> {
            userManager.navigateProducts(mainManager, loginManager, sessionID);
        });
        checkClients.setOnAction((ActionEvent event) -> {
            userManager.navigateClients(mainManager, loginManager, sessionID);
        });
    }
}