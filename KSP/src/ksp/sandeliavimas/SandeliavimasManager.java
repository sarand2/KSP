package ksp.sandeliavimas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksp.LoginManager;
import ksp.MainViewManager;

/**
 *
 * @author Martynas
 */
public class SandeliavimasManager {
  private final Scene scene;
  private final Stage stage;

  public SandeliavimasManager(Scene scene,Stage stage) {
    this.scene = scene;
    this.stage = stage;
  }

    public void navigateAdd(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showAddView(mainManager, loginManager, sessionID);
    }

    public void navigateDelete(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showDeleteView(mainManager, loginManager, sessionID);
    }

    public void navigateDistribute(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showDistributeView(mainManager, loginManager, sessionID);
    }

    public void navigateSearch(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        showSearchView(mainManager, loginManager, sessionID);
    }

    void navigateAddToWareHouse(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        showAddWarehouse(mainManager, loginManager, sessionID);
    }

    private void showAddView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("pridetiPreke.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            PridetiPrekeController controller = 
                loader.<PridetiPrekeController>getController();
            controller.initView(this, mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showDeleteView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("prekiuSalinimas.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            PrekiuSalinimasController controller = 
                loader.<PrekiuSalinimasController>getController();
            controller.initView(this, mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showDistributeView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("paskirstymas.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            PaskirstymasController controller = 
                loader.<PaskirstymasController>getController();
            controller.initView(this, mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showSearchView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("prekiuPaieska.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(720);
            stage.setMinHeight(500);
            stage.setMaxWidth(720);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(720);
            PrekiuPaieskaController controller = 
                loader.<PrekiuPaieskaController>getController();
            controller.initView(this, mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAddWarehouse(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("pridetiISandeli.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setMinWidth(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setHeight(500);
            stage.setWidth(500);
            PridetiISandeliController controller = 
                loader.<PridetiISandeliController>getController();
            controller.initView(this, mainManager, loginManager, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(MainViewManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
