package ksp;

import ksp.sandeliavimas.SandeliavimasController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Martynas
 */
public class MainViewManager {
  private final Scene scene;
  private final Stage stage;

  public MainViewManager(Scene scene,Stage stage) {
    this.scene = scene;
    this.stage = stage;
  }
  
  public Scene getScene() {
      return this.scene;
  }
  
  public Stage getStage() {
      return this.stage;
  }
  public void navigateCouriers(final LoginManager loginManager, String sessionID) {
      if(sessionID.contains("admin")){
          showCourierViewAdmin(loginManager, sessionID);
      }
      if(sessionID.contains("courier")){
          showCourierViewCourier(loginManager, sessionID);
      }
  }
  public void showCourierViewAdmin(final LoginManager loginManager, String sessionID){
      try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Couriers/adminView.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            ksp.Couriers.AdminController controller = 
                loader.<ksp.Couriers.AdminController>getController();
            controller.initView(this, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  public void showCourierViewCourier(final LoginManager loginManager, String sessionID){
      try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Couriers/courierView.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            ksp.Couriers.CourierController controller = 
                loader.<ksp.Couriers.CourierController>getController();
            controller.initView(this, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    public void navigateStorage(final LoginManager loginManager, String sessionID) {
        showStorageView(loginManager, sessionID);
    }

    public void navigateControls(final LoginManager loginManager, String sessionID) {
        if(sessionID.contains("admin")){
            showControlsView(loginManager, sessionID);
        }
    }

    private void showStorageView(final LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("sandeliavimas/sandeliavimas.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            SandeliavimasController controller = 
                loader.<SandeliavimasController>getController();
            controller.initView(this, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showControlsView(final LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("usersManagement.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            UsersManagementController controller =
                    loader.<UsersManagementController>getController();
            controller.initView(this, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void navigateAcounting(final LoginManager loginManager, String sessionID) {
      if(sessionID.contains("admin")){
          showAccountingViewAdmin(loginManager, sessionID);
      } else if(sessionID.contains("courier")){
          showAcountingViewCourier(loginManager, sessionID);
      } else if (sessionID.contains("user")){
          showAcountingViewUser(loginManager, sessionID);
      }
  }
    
  private void showAccountingViewAdmin(final LoginManager loginManager, String sessionID){
      try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("apskaita/adminView.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            ksp.apskaita.AdminController controller = 
                loader.<ksp.apskaita.AdminController>getController();
            controller.initView(this, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  private void showAcountingViewCourier(final LoginManager loginManager, String sessionID){
      try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("apskaita/CourierView.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            ksp.apskaita.CourierController controller = 
                loader.<ksp.apskaita.CourierController>getController();
            controller.initView(this, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  private void showAcountingViewUser(final LoginManager loginManager, String sessionID){
      try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("apskaita/UserView.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            ksp.apskaita.UserController controller = 
                loader.<ksp.apskaita.UserController>getController();
            controller.initView(this, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
}
