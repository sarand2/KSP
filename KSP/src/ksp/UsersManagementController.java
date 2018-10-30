package ksp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ksp.users_management.UsersManager;

public class UsersManagementController {
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button removeUser;
    @FXML
    private Button blockUser;
    @FXML
    private Button retrieveOrders;
    @FXML
    private Button createOrder;


    public void initialize() {}

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });

        backButton.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });

        UsersManager usersManager = new UsersManager(mainManager.getScene(), mainManager.getStage());
        removeUser.setOnAction((ActionEvent event) -> {
            usersManager.navigateBlockUser(mainManager, loginManager, sessionID);
        });
        blockUser.setOnAction((ActionEvent event) -> {
            usersManager.navigateDeleteUser(mainManager, loginManager, sessionID);
        });
        createOrder.setOnAction((ActionEvent event) -> {
            usersManager.navigateCreateOrder(mainManager, loginManager, sessionID);
        });
        retrieveOrders.setOnAction((ActionEvent event) -> {
            usersManager.navigateRetrieveOrders(mainManager, loginManager, sessionID);
        });
    }
}
