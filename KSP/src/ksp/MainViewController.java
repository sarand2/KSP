package ksp;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controls the main application screen */
public class MainViewController {
  @FXML private Button logoutButton;
  @FXML private Label  sessionLabel;
  @FXML private Button sandeliavimasButton;
  
  public void initialize() {}
  
  public void initSessionID(final LoginManager loginManager, String sessionID) {
    sessionLabel.setText(sessionID);
    logoutButton.setOnAction((ActionEvent event) -> {
        loginManager.logout();
    });
    
    sandeliavimasButton.setOnAction((ActionEvent event) -> {
        MainViewManager mainManager = new MainViewManager(loginManager.getScene(), loginManager.getStage());
        mainManager.navigateStorage(loginManager, sessionID);
    });
  }
}