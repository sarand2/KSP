package ksp.apskaita;

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

    public void navigateProductsDelivery(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showProductsDelivery(mainManager, loginManager, sessionID);
    }
    public void navigateCheckCouriers(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showCheckCouriers(mainManager, loginManager, sessionID);
    }
    public void navigateMembers(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showMembers(mainManager, loginManager, sessionID);
    }
    public void navigateProducts(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showProducts(mainManager, loginManager, sessionID);
    }
    public void navigateRoutes(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showRoutes(mainManager, loginManager, sessionID);
    }
     
    private void showProductsDelivery(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("productsDelivery.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            productsDeliveryController controller = 
                loader.<productsDeliveryController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showCheckCouriers(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("checkCouriers.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            checkCouriersController controller = 
                loader.<checkCouriersController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showClients(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("clients.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            clientsController controller = 
                loader.<clientsController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showMembers(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("members.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            membersController controller = 
                loader.<membersController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showProducts(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("products.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            productsController controller = 
                loader.<productsController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showRoutes(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("routes.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            routesController controller = 
                loader.<routesController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
