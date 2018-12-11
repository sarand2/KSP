/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.sandeliavimas;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ksp.Couriers.dbConnection;
import ksp.LoginManager;
import ksp.MainViewManager;

/**
 * FXML Controller class
 *
 * @author Martynas
 */
public class PridetiPrekeController implements Initializable {

    dbConnection dbc = new dbConnection();
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button main;
    @FXML
    private Button backButton;
    @FXML
    private Button addToWarehouse;
    @FXML
    private TextField name;
    @FXML
    private TextField code;
    @FXML
    private TextField brand;
    @FXML
    private TextField country;
    @FXML
    private ComboBox category;
    @FXML
    private TextField length;
    @FXML
    private TextField width;
    @FXML
    private TextField heigth;
    @FXML
    private TextField weigth;
    @FXML
    private Label status;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initView(SandeliavimasManager sandeliavimas, MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);

        ResultSet categories = dbc.getCategories();
        try {
            while (categories.next()) {
                category.getItems().add(categories.getString("pavadinimas"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }

        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        backButton.setOnAction((ActionEvent event) -> {
            mainManager.navigateStorage(loginManager, sessionID);
        });
        addToWarehouse.setOnAction((ActionEvent event) -> {
            sandeliavimas.navigateAddToWareHouse(mainManager, loginManager, sessionID);
        });
    }

    @FXML
    public void addButton(ActionEvent e) {
        if (name.getText() != null && code.getText() != null
                && brand.getText() != null && category.getValue() != null
                && country.getText() != null && weigth.getText() != null
                && length.getText() != null && width.getText() != null
                && heigth.getText() != null) {
            String name = "\"" + this.name.getText() + "\"";
            String code = "\"" + this.code.getText() + "\"";
            String brand = "\"" + this.brand.getText() + "\"";
            String country = "\"" + this.country.getText() + "\"";
            String category = this.category.getValue().toString();
            String weigth = "\"" + this.weigth.getText() + "\"";
            String length = "\"" + this.length.getText() + "\"";
            String width = "\"" + this.width.getText() + "\"";
            String heigth = "\"" + this.heigth.getText() + "\"";

            int c = 0;

            ResultSet categories = dbc.getCategories();
            try {
                while (categories.next()) {
                    if (categories.getString("pavadinimas").equals(category)) {
                        c = categories.getInt("id");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }
            
            Locale locale = new Locale("en", "US");
            Locale.setDefault(locale);
            String query = "INSERT INTO prekes (kodas,pavadinimas,kategorija,prekes_zenklas,kilmes_salis,svoris,ilgis,plotis,aukstis) values(" + code + "," + name + "," + c + "," + brand + "," + country + "," + weigth + "," + length + "," + width + "," + heigth + ")";
            System.out.println("Sending query: " + query);
            boolean ok = dbc.executeUpdate(query);
            if (ok) {
                status.setText("Naujas prekės įrašas sukurtas.");
            } else {
                status.setText("Nepavyko sukurti prekės įrašo.");
            }
        } else {
            status.setText("Prašome užpildyti visus laukelius.");
        }
    }

}
