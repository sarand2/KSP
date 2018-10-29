package ksp;

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

    public void navigateStorage(final LoginManager loginManager, String sessionID) {
        showStorageView(loginManager, sessionID);
    }

    private void showStorageView(final LoginManager loginManager, String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("sandeliavimas.fxml")
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
}
