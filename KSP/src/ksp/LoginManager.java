package ksp;

import java.io.IOException;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

/** Manages control flow for logins */
public class LoginManager {
  private Scene scene;
  private Stage stage;

  public LoginManager(Scene scene,Stage stage) {
    this.scene = scene;
    this.stage = stage;
  }

  /**
   * Callback method invoked to notify that a user has been authenticated.
   * Will show the main application screen.
   */ 
  public void authenticated(String sessionID) {
    showMainView(sessionID);
  }

  /**
   * Callback method invoked to notify that a user has logged out of the main application.
   * Will show the login application screen.
   */ 
  public void logout() {
    showLoginScreen();
  }
  
  public void showLoginScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("login.fxml")
      );
      
      scene.setRoot((Parent) loader.load());
      stage.setMinHeight(320);
      stage.setMinWidth(350);
      stage.setMaxHeight(320);
      stage.setMaxWidth(350);
      stage.setHeight(320);
      stage.setWidth(350);
      LoginController controller = 
        loader.<LoginController>getController();
      controller.initManager(this);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void showMainView(String sessionID) {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("mainview.fxml")
      );
      scene.setRoot((Parent) loader.load());
      stage.setMinWidth(500);
      stage.setMinHeight(500);
      stage.setMaxWidth(500);
      stage.setMaxHeight(500);
      stage.setHeight(500);
      stage.setWidth(500);
      MainViewController controller = 
        loader.<MainViewController>getController();
      controller.initSessionID(this, sessionID);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
