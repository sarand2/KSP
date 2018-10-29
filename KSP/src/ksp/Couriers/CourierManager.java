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

public class CourierManager {
  private final Scene scene;
  private final Stage stage;

  public CourierManager(Scene scene,Stage stage) {
    this.scene = scene;
    this.stage = stage;
  }

    public void navigateGetReport(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showGetReportView(mainManager, loginManager, sessionID);
    }
    public void navigateFillCar(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showFillCar(mainManager, loginManager, sessionID);
    }

    public void navigatePlanRoute(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showPlanRouteView(mainManager, loginManager, sessionID);
    }
    private void showGetReportView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("getReport.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            getReportController controller = 
                loader.<getReportController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showFillCar(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fillCar.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            FillCarController controller = 
                loader.<FillCarController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showPlanRouteView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("planRoute.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            planRouteController controller = 
                loader.<planRouteController>getController();
            controller.initView(mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
