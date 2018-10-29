package ksp;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/** Controls the main application screen */
public class SandeliavimasController {
    @FXML private Button logoutButton;
    @FXML private Label  sessionLabel;
    @FXML private Button backButton;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button distribute;
    @FXML
    private Button search;
  
  
    public void initialize() {}

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        SandeliavimasManager sandeliavimas = new SandeliavimasManager(mainManager.getScene(), mainManager.getStage());
        add.setOnAction((ActionEvent event) -> {
            sandeliavimas.navigateAdd(mainManager, loginManager, sessionID);
        });
        delete.setOnAction((ActionEvent event) -> {
            sandeliavimas.navigateDelete(mainManager, loginManager, sessionID);
        });
        distribute.setOnAction((ActionEvent event) -> {
            sandeliavimas.navigateDistribute(mainManager, loginManager, sessionID);
        });
        search.setOnAction((ActionEvent event) -> {
            sandeliavimas.navigateSearch(mainManager, loginManager, sessionID);
        });
    }
}