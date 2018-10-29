package ksp.Couriers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksp.LoginManager;
import ksp.MainViewManager;

public class AdminManager {
  private final Scene scene;
  private final Stage stage;

  public AdminManager(Scene scene,Stage stage) {
    this.scene = scene;
    this.stage = stage;
  }

    public void navigateAdd(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showAddView(mainManager, loginManager, sessionID);
    }
    public void navigateDelete(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showDeleteView(mainManager, loginManager, sessionID);
    }

    public void navigateSearch(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showSearchView(mainManager, loginManager, sessionID);
    }
    private void showAddView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("addCourier.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            addCourierController controller = 
                loader.<addCourierController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showDeleteView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("deleteCourier.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            deleteCourierController controller = 
                loader.<deleteCourierController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSearchView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("findCourierLocation.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(720);
            stage.setMinHeight(500);
            stage.setMaxWidth(720);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(720);
            findCourierLocationController controller = 
                loader.<findCourierLocationController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
