/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.sandeliavimas;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class PrekiuSalinimasController implements Initializable {

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
    private TextField count;
    @FXML
    private Button delete;
    @FXML
    private ComboBox product;
    @FXML
    private ComboBox warehouse;
    @FXML
    private Label message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initView(SandeliavimasManager sandeliavimas, MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        backButton.setOnAction((ActionEvent event) -> {
            mainManager.navigateStorage(loginManager, sessionID);
        });

        ResultSet products = dbc.getProducts();
        try {
            while (products.next()) {
                product.getItems().add(products.getString("kodas") + " " + products.getString("pavadinimas"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }

        ResultSet warehouses = dbc.getWarehouses();
        try {
            while (warehouses.next()) {
                warehouse.getItems().add(warehouses.getString("adresas") + ", " + warehouses.getString("miestas"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    public void addButton(ActionEvent event) {
        if (product.getValue() != null && warehouse.getValue() != null
                && count.getText() != null) {
            int count = Integer.parseInt(this.count.getText());
            String product = this.product.getValue().toString();
            String warehouse = this.warehouse.getValue().toString();

            ResultSet warehouses = dbc.getWarehouses();
            int warehouseId = 0;
            try {
                while (warehouses.next()) {
                    String w = warehouses.getString("adresas") + ", " + warehouses.getString("miestas");
                    if (w.equals(warehouse)) {
                        warehouseId = warehouses.getInt("id");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }

            String productCode = "";
            ResultSet products = dbc.getProducts();
            try {
                while (products.next()) {
                    String p = products.getString("kodas") + " " + products.getString("pavadinimas");
                    if (p.equals(product)) {
                        productCode = products.getString("kodas");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }

            ResultSet pw = dbc.getProductInWarehouse(warehouseId, productCode);
            int id = -1;
            int overallCount = -1;
            try {
                while (pw.next()) {
                    id = pw.getInt("id");
                    overallCount = pw.getInt("kiekis");
                    break;
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }

            String query;
            boolean ok;
            if (overallCount - count > 0) {
                query = String.format("UPDATE sandelio_prekes SET kiekis=%d WHERE id=%d", overallCount - count, id);
                System.out.println("Sending query: " + query);
                ok = dbc.executeUpdate(query);
                if (ok) {
                    message.setText("Sumažintas sandėlio prekės kiekis.");
                } else {
                    message.setText("Nepavyko sumažinti sandėlio prekės kiekio.");
                }
            } else {
                query = String.format("DELETE FROM sandelio_prekes WHERE id=%d", id);
                System.out.println("Sending query: " + query);
                ok = dbc.executeUpdate(query);
                if (ok) {
                    message.setText("Ištrintas sandėlio prekės įrašas.");
                } else {
                    message.setText("Nepavyko ištrinti sandėlio prekės įrašo.");
                }
            }
        } else {
            message.setText("Prašome užpildyti visus laukelius.");
        }
    }

    @FXML
    public void selectProduct(ActionEvent event) {
        if (warehouse.getValue() == null) {
            warehouse.getItems().clear();
            String product = this.product.getValue().toString();
            String productCode = "";
            ResultSet products = dbc.getProducts();
            try {
                while (products.next()) {
                    String p = products.getString("kodas") + " " + products.getString("pavadinimas");
                    if (p.equals(product)) {
                        productCode = products.getString("kodas");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }

            ResultSet warehouses = dbc.getWarehouses(productCode);
            try {
                while (warehouses.next()) {
                    warehouse.getItems().add(warehouses.getString("adresas") + ", " + warehouses.getString("miestas"));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void selectWarehouse(ActionEvent event) {
        if (product.getValue() == null) {
            product.getItems().clear();
            String warehouse = this.warehouse.getValue().toString();
            ResultSet warehouses = dbc.getWarehouses();
            int warehouseId = 0;
            try {
                while (warehouses.next()) {
                    String w = warehouses.getString("adresas") + ", " + warehouses.getString("miestas");
                    if (w.equals(warehouse)) {
                        warehouseId = warehouses.getInt("id");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }

            ResultSet products = dbc.getProducts(warehouseId);
            try {
                while (products.next()) {
                    product.getItems().add(products.getString("kodas") + " " + products.getString("pavadinimas"));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }
        }
    }

}
