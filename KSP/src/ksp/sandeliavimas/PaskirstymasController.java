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
public class PaskirstymasController implements Initializable {

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
    private Button distribute;
    @FXML
    private TextField count;
    @FXML
    private ComboBox from;
    @FXML
    private ComboBox product;
    @FXML
    private ComboBox to;
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

        from.getItems().add("");
        ResultSet warehouses = dbc.getWarehouses();
        try {
            while (warehouses.next()) {
                from.getItems().add(warehouses.getString("adresas") + ", " + warehouses.getString("miestas"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }

        to.getItems().add("");
        warehouses = dbc.getWarehouses();
        try {
            while (warehouses.next()) {
                to.getItems().add(warehouses.getString("adresas") + ", " + warehouses.getString("miestas"));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }

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
    }

    @FXML
    public void distribudeButton(ActionEvent event) {
        if (count.getText() != null && from.getValue() != null && product.getValue() != null && to.getValue() != null) {
            ResultSet warehouses = dbc.getWarehouses();
            String product = this.product.getValue().toString();
            int count = Integer.parseInt(this.count.getText());
            int warehouseId = 0;
            int occupied = 0;
            int capacity = 0;
            try {
                while (warehouses.next()) {
                    String w = warehouses.getString("adresas") + ", " + warehouses.getString("miestas");
                    if (w.equals(from.getValue().toString())) {
                        warehouseId = warehouses.getInt("id");
                        occupied = warehouses.getInt("uzimta");
                        capacity = warehouses.getInt("talpa");
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
            double price = 0;
            try {
                while (pw.next()) {
                    id = pw.getInt("id");
                    overallCount = pw.getInt("kiekis");
                    price = pw.getDouble("kaina");
                    break;
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }

            if (id != -1) {
                warehouses = dbc.getWarehouses();
                int warehouseId2 = 0;
                int occupied2 = 0;
                int capacity2 = 0;
                try {
                    while (warehouses.next()) {
                        String w = warehouses.getString("adresas") + ", " + warehouses.getString("miestas");
                        if (w.equals(to.getValue().toString())) {
                            warehouseId2 = warehouses.getInt("id");
                            occupied2 = warehouses.getInt("uzimta");
                            capacity2 = warehouses.getInt("talpa");
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("ERROR: " + ex);
                    ex.printStackTrace();
                }

                if (occupied2 + count <= capacity2) {
                    String query = String.format("UPDATE sandeliai SET uzimta=%d WHERE id=%d", occupied2 + count, warehouseId2);
                    System.out.println("Sending query: " + query);
                    boolean ok = dbc.executeUpdate(query);
                    if (ok) {
                        message.setText("Pavyko atnaujinti tikslo sandėlį.");
                    } else {
                        message.setText("Nepavyko atnaujinti tikslo sandėlio.");
                    }
                    
                    query = String.format("UPDATE sandeliai SET uzimta=%d WHERE id=%d", occupied - count, warehouseId);
                    System.out.println("Sending query: " + query);
                    ok = dbc.executeUpdate(query);
                    if (ok) {
                        message.setText("Pavyko atnaujinti pardžios sandėlį.");
                    } else {
                        message.setText("Nepavyko atnaujinti pradžios sandėlio.");
                    }
                    
                    if (overallCount <= count) {
                        query = String.format("UPDATE sandelio_prekes SET sandelio_id=%d WHERE id=%d", warehouseId2, id);
                        System.out.println("Sending query: " + query);
                        ok = dbc.executeUpdate(query);
                        if (ok) {
                            message.setText("Sumažintas sandėlio prekės kiekis.");
                        } else {
                            message.setText("Nepavyko sumažinti sandėlio prekės kiekio.");
                        }
                    } else {
                        query = String.format("UPDATE sandelio_prekes SET kiekis=%d WHERE id=%d", overallCount - count, id);
                        System.out.println("Sending query: " + query);
                        ok = dbc.executeUpdate(query);
                        if (ok) {
                            message.setText("Sumažintas sandėlio prekės kiekis.");
                        } else {
                            message.setText("Nepavyko sumažinti sandėlio prekės kiekio.");
                        }

                        Locale locale = new Locale("en", "US");
                        Locale.setDefault(locale);
                        query = String.format("INSERT INTO sandelio_prekes (sandelio_id,prekes_kodas,kaina,kiekis) VALUES(%d,%s,%.2f,%d)", warehouseId2, productCode, price, count);
                        System.out.println("Sending query: " + query);
                        ok = dbc.executeUpdate(query);
                        if (ok) {
                            message.setText("Pervažta sandėlio prekė.");
                        } else {
                            message.setText("Nepavyko pervežti sandėlio prekės.");
                        }
                    }
                } else {
                    message.setText("Netelpa į pasirinktą sandėlį.");
                }
            } else {
                message.setText("Nėra pasirinktos prekės.");
            }
        } else {
            message.setText("Prašome užpildyti visus laukelius.");
        }
    }

    @FXML
    public void fromSelected(ActionEvent event) {
        String warehouse = this.from.getValue().toString();
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

        if (product.getValue() == null || product.getValue().toString().equals("")) {
            product.getItems().clear();
            product.getItems().add("");

            ResultSet products = dbc.getProducts(warehouseId, false);
            try {
                while (products.next()) {
                    product.getItems().add(products.getString("kodas") + " " + products.getString("pavadinimas"));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }
        }

        if (to.getValue() == null || to.getValue().toString().equals("") || to.getValue().toString().equals(warehouse)) {
            to.getItems().clear();
            to.getItems().add("");
            warehouses = dbc.getWarehouses(warehouseId);
            try {
                while (warehouses.next()) {
                    to.getItems().add(warehouses.getString("adresas") + ", " + warehouses.getString("miestas"));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void productSelected(ActionEvent event) {
        if (from.getValue() == null || from.getValue().toString().equals("")) {
            from.getItems().clear();
            from.getItems().add("");
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

            ResultSet warehouses = dbc.getWarehouses(productCode, false);
            try {
                while (warehouses.next()) {
                    from.getItems().add(warehouses.getString("adresas") + ", " + warehouses.getString("miestas"));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void toSelected(ActionEvent event) {
        String warehouse = this.to.getValue().toString();
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

        if (from.getValue() == null || from.getValue().toString().equals("") || from.getValue().toString().equals(warehouse)) {
            from.getItems().clear();
            from.getItems().add("");
            warehouses = dbc.getWarehouses(warehouseId);
            try {
                while (warehouses.next()) {
                    from.getItems().add(warehouses.getString("adresas") + ", " + warehouses.getString("miestas"));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex);
                ex.printStackTrace();
            }
        }
    }

}
