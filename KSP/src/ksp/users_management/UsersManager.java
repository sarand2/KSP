package ksp.users_management;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksp.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersManager {

    private final Scene scene;
    private final Stage stage;

    public UsersManager(Scene scene,Stage stage) {
        this.scene = scene;
        this.stage = stage;
    }

    public void navigateDeleteUser(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showDeleteView(mainManager, loginManager, sessionID);
    }

    public void navigateCreateOrder(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        showCreateOrderView(mainManager, loginManager, sessionID);
    }

    public void navigateBlockUser(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showBlockView(mainManager, loginManager, sessionID);
    }

    public void navigateRetrieveOrders(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        showOrdersList(mainManager, loginManager, sessionID);
    }

    private void showOrdersList(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("retrieveOrders.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            RetrieveOrders controller =
                    loader.<RetrieveOrders>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showBlockView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("blockUser.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            BlockUser controller =
                    loader.<BlockUser>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showDeleteView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("deleteUser.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            DeleteUser controller =
                    loader.<DeleteUser>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showCreateOrderView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("createOrder.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            CreateOrder controller =
                    loader.<CreateOrder>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
