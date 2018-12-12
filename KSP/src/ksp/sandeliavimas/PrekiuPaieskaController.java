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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ksp.Couriers.dbConnection;
import ksp.LoginManager;
import ksp.MainViewManager;

/**
 * FXML Controller class
 *
 * @author Martynas
 */
public class PrekiuPaieskaController implements Initializable {

    public class Product {

        private final SimpleStringProperty code;
        private final SimpleStringProperty name;
        private final SimpleStringProperty price;
        private final SimpleStringProperty warehouse;
        private final SimpleStringProperty category;
        private final SimpleStringProperty brand;
        private final SimpleStringProperty country;
        private final SimpleStringProperty count;

        private Product(String code, String name, String price, String warehouse, String category, String brand, String country, String count) {
            this.code = new SimpleStringProperty(code);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.warehouse = new SimpleStringProperty(warehouse);
            this.category = new SimpleStringProperty(category);
            this.brand = new SimpleStringProperty(brand);
            this.country = new SimpleStringProperty(country);
            this.count = new SimpleStringProperty(count);
        }

        public String getCode() {
            return code.get();
        }

        public void setCode(String value) {
            code.set(value);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String value) {
            name.set(value);
        }

        public String getPrice() {
            return price.get();
        }

        public void setPrice(String value) {
            price.set(value);
        }

        public String getWarehouse() {
            return warehouse.get();
        }

        public void setWarehouse(String value) {
            warehouse.set(value);
        }

        public String getCategory() {
            return category.get();
        }

        public void setCategory(String value) {
            category.set(value);
        }

        public String getBrand() {
            return brand.get();
        }

        public void setBrand(String value) {
            brand.set(value);
        }

        public String getCountry() {
            return country.get();
        }

        public void setCountry(String value) {
            country.set(value);
        }

        public String getCount() {
            return count.get();
        }

        public void setCount(String value) {
            count.set(value);
        }

        @Override
        public String toString() {
            return String.format("%s, %s, %s, %s, %s, %s, %s, %s", code.get(), name.get(), price.get(), warehouse.get(), category.get(), brand.get(), country.get(), count.get());
        }
    }

    dbConnection dbc = new dbConnection();

    private TableColumn codeCol;
    private TableColumn nameCol;
    private TableColumn priceCol;
    private TableColumn warehouseCol;
    private TableColumn categoryCol;
    private TableColumn brandCol;
    private TableColumn countryCol;
    private TableColumn countCol;
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button main;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox category;
    @FXML
    private TextField name;
    @FXML
    private TextField code;
    @FXML
    private ComboBox warehouse;
    @FXML
    private Button filter;
    @FXML
    private TableView<Product> table;
    ObservableList<Product> list = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        filterButton(null);
    }

    void initView(SandeliavimasManager sandeliavimas, MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);

        category.getItems().add("");
        ResultSet categories = dbc.getCategories();
        try {
            while (categories.next()) {
                category.getItems().add(categories.getString("pavadinimas"));
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

        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        backButton.setOnAction((ActionEvent event) -> {
            mainManager.navigateStorage(loginManager, sessionID);
        });
        
        table.getColumns().clear();
        
        table.setEditable(true);

        table.setItems(list);

        codeCol = new TableColumn("Kodas");
        nameCol = new TableColumn("Pavadinimas");
        priceCol = new TableColumn("Kaina");
        warehouseCol = new TableColumn("Sandėlys");
        categoryCol = new TableColumn("Kategorija");
        brandCol = new TableColumn("Prekės ženklas");
        countryCol = new TableColumn("Kilmės šalis");
        countCol = new TableColumn("Svoris");

        table.getColumns().addAll(codeCol, nameCol, priceCol, warehouseCol, categoryCol, brandCol, countryCol, countCol);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        warehouseCol.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        list.removeAll(list);

        ResultSet products = dbc.getProductSearchResults(null, null, -1, -1);
        try {
            while (products.next()) {
                Product prod = new Product(products.getString("kodas"), products.getString("pavadinimas"), String.format("%.2f", products.getDouble("kaina")), String.format("%s, %s", products.getString("adresas"), products.getString("miestas")), products.getString("kategorija"), products.getString("prekes_zenklas"), products.getString("kilmes_salis"), products.getString("kiekis"));
                System.out.println(prod.toString());
                list.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    public void filterButton(ActionEvent event) {
        table.setEditable(true);
        
        String code = null;
        String name = null;
        int category = -1;
        int warehouse = -1;

        if (this.code.getText() != null || !this.code.getText().equals("")) {
            code = this.code.getText();
        }

        if (this.name.getText() != null || !this.name.getText().equals("")) {
            name = this.name.getText();
        }

        if (this.category.getValue() != null) {
            if (!this.category.getValue().toString().equals("")) {
                ResultSet categories = dbc.getCategories();
                try {
                    while (categories.next()) {
                        if (categories.getString("pavadinimas").equals(this.category.getValue().toString())) {
                            category = categories.getInt("id");
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("ERROR: " + ex);
                    ex.printStackTrace();
                }
            }
        }

        if (this.warehouse.getValue() != null) {
            if (!this.warehouse.getValue().toString().equals("")) {
                ResultSet warehouses = dbc.getWarehouses();
                try {
                    while (warehouses.next()) {
                        if (String.format("%s, %s", warehouses.getString("adresas"), warehouses.getString("miestas")).equals(this.warehouse.getValue().toString())) {
                            warehouse = warehouses.getInt("id");
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("ERROR: " + ex);
                    ex.printStackTrace();
                }
            }
        }

        codeCol = new TableColumn("Kodas");
        nameCol = new TableColumn("Pavadinimas");
        priceCol = new TableColumn("Kaina");
        warehouseCol = new TableColumn("Sandėlys");
        categoryCol = new TableColumn("Kategorija");
        brandCol = new TableColumn("Prekės ženklas");
        countryCol = new TableColumn("Kilmės šalis");
        countCol = new TableColumn("Kiekis");
        
        table.getColumns().clear();

        table.getColumns().addAll(codeCol, nameCol, priceCol, warehouseCol, categoryCol, brandCol, countryCol, countCol);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        warehouseCol.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        list.removeAll(list);

        ResultSet products = dbc.getProductSearchResults(name, code, category, warehouse);
        try {
            while (products.next()) {
                Product prod = new Product(products.getString("kodas"), products.getString("pavadinimas"), String.format("%.2f", products.getDouble("kaina")), String.format("%s, %s", products.getString("adresas"), products.getString("miestas")), products.getString("kategorija"), products.getString("prekes_zenklas"), products.getString("kilmes_salis"), products.getString("kiekis"));
                System.out.println(prod.toString());
                list.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }

        table.setItems(list);
    }

}
