/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.sandeliavimas;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
public class PridetiISandeliController implements Initializable {

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
    private Button addNew;
    @FXML
    private TextField price;
    @FXML
    private TextField count;
    @FXML
    private Button add;
    @FXML
    private Label message;
    @FXML
    private ComboBox product;
    @FXML
    private ComboBox warehouse;

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
        addNew.setOnAction((ActionEvent event) -> {
            sandeliavimas.navigateAdd(mainManager, loginManager, sessionID);
        });

        product.getItems().add("");
        ResultSet products = dbc.getProducts();
        try {
            while (products.next()) {
                product.getItems().add(products.getString("kodas") + " " + products.getString("pavadinimas"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }

        warehouse.getItems().add("");
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
                && price.getText() != null && count.getText() != null) {
            double price = Double.parseDouble(this.price.getText());
            int count = Integer.parseInt(this.count.getText());
            String product = this.product.getValue().toString();
            String warehouse = this.warehouse.getValue().toString();

            ResultSet warehouses = dbc.getWarehouses();
            int occupied = 0;
            int capacity = 0;
            int warehouseId = 0;
            try {
                while (warehouses.next()) {
                    String w = warehouses.getString("adresas") + ", " + warehouses.getString("miestas");
                    if (w.equals(warehouse)) {
                        occupied = warehouses.getInt("uzimta");
                        capacity = warehouses.getInt("talpa");
                        warehouseId = warehouses.getInt("id");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }

            if (occupied + count > capacity) {
                message.setText("Neužtenka laisvos vietos sandėlyje.");
            } else {
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
                occupied += count;
                String query = "UPDATE sandeliai SET uzimta=" + String.valueOf(occupied) + " WHERE id=" + String.valueOf(warehouseId);
                System.out.println("Sending query: " + query);
                boolean ok = dbc.executeUpdate(query);
                if (ok) {
                    Locale locale = new Locale("en", "US");
                    Locale.setDefault(locale);
                    message.setText("Atnaujintas sandėlio įrašas.");
                    query = String.format("INSERT INTO sandelio_prekes (sandelio_id,prekes_kodas,kaina,kiekis) values(%d,%s,%.2f,%d)", warehouseId, productCode, price, count);
                    System.out.println("Sending query: " + query);
                    ok = dbc.executeUpdate(query);
                    if (ok) {
                        message.setText("Sukurtas sandėlio prekės įrašas.");
                    } else {
                        message.setText("Nepavyko sukurti sandėlio prekės įrašo.");
                    }
                } else {
                    message.setText("Nepavyko atnaujinti sandėlio įrašo.");
                }
            }
        } else {
            message.setText("Prašome užpildyti visus laukelius.");
        }
    }

    @FXML
    public void selectProduct(ActionEvent event) {
        if ((warehouse.getValue() == null || warehouse.getValue().toString().equals("")) && !product.getValue().toString().equals("")) {
            warehouse.getItems().clear();
            warehouse.getItems().add("");
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
        if ((product.getValue() == null || product.getValue().toString().equals("")) && !warehouse.getValue().toString().equals("")) {
            product.getItems().clear();
            product.getItems().add("");
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
