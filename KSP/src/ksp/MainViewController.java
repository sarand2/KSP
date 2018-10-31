package ksp;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** UsersManagementController the main application screen */
public class MainViewController {
  @FXML private Button logoutButton;
  @FXML private Label  sessionLabel;
  @FXML private Button sandeliavimasButton;
  @FXML private Button controlsButton;
  @FXML private Button couriersButton;
  @FXML private Button acountingButton;
  
  public void initialize() {}
  
  public void initSessionID(final LoginManager loginManager, String sessionID) {
    sessionLabel.setText(sessionID);
    logoutButton.setOnAction((ActionEvent event) -> {
        loginManager.logout();
    });
    couriersButton.setOnAction((ActionEvent event) -> {
        MainViewManager mainManager = new MainViewManager(loginManager.getScene(), loginManager.getStage());
        mainManager.navigateCouriers(loginManager, sessionID);
    });
    
    sandeliavimasButton.setOnAction((ActionEvent event) -> {
        MainViewManager mainManager = new MainViewManager(loginManager.getScene(), loginManager.getStage());
        mainManager.navigateStorage(loginManager, sessionID);
    });

      controlsButton.setOnAction((ActionEvent event) -> {
      MainViewManager mainManager = new MainViewManager(loginManager.getScene(), loginManager.getStage());
      mainManager.navigateControls(loginManager, sessionID);
    });
      
      acountingButton.setOnAction((ActionEvent event) -> {
      MainViewManager mainManager = new MainViewManager(loginManager.getScene(), loginManager.getStage());
      mainManager.navigateAcounting(loginManager, sessionID);
    });
  }
}