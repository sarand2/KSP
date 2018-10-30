package ksp.users_management;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ksp.LoginManager;
import ksp.MainViewManager;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button main;
    @FXML
    private Button backButton;
    @FXML
    private TextField personalID;
    @FXML
    private TextField surname;
    @FXML
    private TextField password;
    @FXML
    private TextField address;
    @FXML
    private Button register;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void initView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        backButton.setOnAction((ActionEvent event) -> {
            mainManager.navigateControls(loginManager, sessionID);
        });
    }
}
