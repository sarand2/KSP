package ksp;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** UsersManagementController the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML private TextField password;
  @FXML private Button loginButton;
  @FXML private Button registrationButton;
  
  public void initialize() {
      
  }
  
  public void initManager(final LoginManager loginManager) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        String sessionID = authorize();
        if (sessionID != null) {
          loginManager.authenticated(sessionID);
        }
      }
    });
    registrationButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        String sessionID = authorize();
        if (sessionID != null) {
          loginManager.authenticated(sessionID);
        } 
      }
    });
  }

  /**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
   */   
  private String authorize() {
      
    return
            user.getText().equals("admin") && password.getText().equals("admin")|| user.getText().equals("user") && password.getText().equals("user") || user.getText().contains("courier") && password.getText().equals("courier")
            ? generateSessionID(user.getText()) 
            : null;
  }
  
  //private static int sessionID = 0;
  private String generateSessionID(String name) {
    //sessionID++;\
    //return name + " - session " + sessionID;
    return name;
  }
}
