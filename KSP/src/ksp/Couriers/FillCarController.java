/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.Couriers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ksp.LoginManager;
import ksp.MainViewManager;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FillCarController implements Initializable {

    dbConnection dbc = new dbConnection();
    ObservableList<OrderedProduct> filledProducts;
    int choosedCarID = -1;
    char courierID = '0';
    boolean alreadyFilled = false;
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button getList;
    @FXML
    private Button accept;
    @FXML
    private Button main;
    @FXML
    private Label label;
    @FXML
    private TableView<OrderedProduct> table;
    @FXML
    private TableColumn productID = new TableColumn("Prekės ID");
    @FXML
    private TableColumn weight = new TableColumn("Svoris");
    @FXML
    private TableColumn count = new TableColumn("Kiekis");
    @FXML
    private TableColumn carID = new TableColumn("Automobilio ID");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void getListButton(ActionEvent e) {
        if (table.getItems().isEmpty()) {
            table.setItems(filledProducts);
        }
    }

    public void acceptButton(ActionEvent e) {
        if (!table.getItems().isEmpty() && !alreadyFilled) {
            System.out.println(courierID + " " + choosedCarID);
            String couriersUpdateQuery = "UPDATE kurjeriai SET fk_automobilis='" + choosedCarID + "' WHERE id='" + courierID + "';";
            boolean couriersUpdateStatus = dbc.executeUpdate(couriersUpdateQuery);
            if (couriersUpdateStatus) {
                System.out.println("Pavyko atnaujinti kurjerio duomenis.");
            } else {
                System.out.println("Nepavyko atnaujinti kurjerio duomenis.");
            }
            String carsUpdateQuery = "UPDATE automobiliai SET fk_kurjeris='" + courierID + "' WHERE id='" + choosedCarID + "';";
            boolean carsUpdateStatus = dbc.executeUpdate(carsUpdateQuery);
            if (carsUpdateStatus) {
                System.out.println("Pavyko atnaujinti automobilio duomenis.");
            } else {
                System.out.println("Nepavyko atnaujinti automobilio duomenis.");
            }
            boolean allProductsUpdated = false;
            for (int i = 0; i < filledProducts.size(); i++) {
                String filledProductQuery = "UPDATE uzsakytos_prekes SET fk_automobilis='" + choosedCarID + "' WHERE id='" + filledProducts.get(i).getId() + "';";
                boolean filledProductStatus = dbc.executeUpdate(filledProductQuery);
                if (filledProductStatus) {
                    System.out.println("Pavyko atnaujinti " + filledProducts.get(i).getId() + " irasa.");
                    allProductsUpdated = true;
                } else {
                    System.out.println("Nepavyko atnaujinti " + filledProducts.get(i).getId() + " iraso.");
                    allProductsUpdated = false;
                }
            }
            if (couriersUpdateStatus && carsUpdateStatus && allProductsUpdated) {
                label.setText("Patvirtinta.");
            } else {
                label.setText("Neįmanoma patvirtinti.");
            }
        } else if(alreadyFilled){
            label.setText("Kurjeris jau priskirtas automobiliui.");
        } else  {
            label.setText("Automobilis nepakrautas.");
        }
    }

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        courierID = sessionID.charAt(sessionID.length() - 1);
        filledProducts = fillCar(courierID);
        if(filledProducts == null){
            label.setText("Kurjeris jau priskirtas automobiliui.");
            filledProducts = readOrderedProductsWithCarID(getCourierCarID(courierID));   
            alreadyFilled=true;
        }
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productID.setMinWidth(3);
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        weight.setMinWidth(4);
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        count.setMinWidth(3);
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        carID.setMinWidth(3);
        carID.setCellValueFactory(new PropertyValueFactory<>("fk_automobilis"));
        table.getColumns().addAll(productID, weight, count, carID);
       // table.setItems(filledProducts);
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        backButton.setOnAction((ActionEvent event) -> {
            table.getItems().clear();
            mainManager.navigateCouriers(loginManager, sessionID);
        });
        main.setOnAction((ActionEvent event) -> {
            table.getItems().clear();
            loginManager.authenticated(sessionID);
        });

    }
      String getCourierCarID(char id) {
        ResultSet couriers = dbc.getCouriersList();
        try {
            while (couriers.next()) {
                if (couriers.getString("id").equals(Character.toString(id))) {
                    return couriers.getString("fk_automobilis");
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return null;
    }
     ObservableList<OrderedProduct> readOrderedProductsWithCarID(String carID) {
        final ObservableList<OrderedProduct> data = FXCollections.observableArrayList();
        ResultSet products = dbc.getOrderedProductsByCarID(carID);
        try {
            while (products.next()) {
                data.add(new OrderedProduct(products.getString("id"), products.getString("svoris"), products.getString("kiekis"), products.getString("pristatymo_adresas"), products.getString("fk_preke"), products.getString("fk_vartotojas"), products.getString("fk_marsrutas"), products.getString("fk_automobilis")));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return data;
    }
    public ObservableList<OrderedProduct> fillCar(char courierID) {
        if(checkCarAssigned(courierID)){        
        String category = getCourierCategory(courierID);
        choosedCarID = getAvailableCar(category);
        double maxLoad = getAvailableCarMaxLoad(choosedCarID);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(choosedCarID);
        String choosedCarStr = sb.toString();
        ObservableList<OrderedProduct> allProducts = readOrderedProducts(choosedCarStr);
        ObservableList<String> pickedProductsID = FXCollections.observableArrayList();
        ObservableList<OrderedProduct> choosedProducts = FXCollections.observableArrayList();
        double load = 0;
        for (int i = 0; i < allProducts.size(); i++) {
            String productID = allProducts.get(i).getId();
            if (!pickedProductsID.contains(productID)) {
                if (load <= maxLoad) {
                    Double additionalLoad = Double.parseDouble(allProducts.get(i).getWeight()) * Integer.parseInt(allProducts.get(i).getCount());
                    if (load + additionalLoad <= maxLoad) {
                        pickedProductsID.add(productID);
                        load += additionalLoad;
                        choosedProducts.add(allProducts.get(i));
                    }
                }
            }
        }     
        return choosedProducts;
        }
        return null;
    }

    String getCourierCategory(char id) {
        ResultSet couriers = dbc.getCouriersList();
        try {
            while (couriers.next()) {
                if (couriers.getString("id").equals(Character.toString(id))) {
                    return couriers.getString("auto_kategorija");
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return null;
    }
    boolean checkCarAssigned(char id) {
        ResultSet couriers = dbc.getCouriersList();
        try {
            while (couriers.next()) {
                if (couriers.getString("id").equals(Character.toString(id))) {
                    if(couriers.getString("fk_automobilis").equals("-1")){
                       return true ;
                    }             
               }
            }
            
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return false;
    }
    Double getAvailableCarMaxLoad(int id) {
        ResultSet cars = dbc.getCarsList();
        try {
            while (cars.next()) {
                if (cars.getInt("id") == id) {
                    return cars.getDouble("maks_apkrova");
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return null;
    }

    int getAvailableCar(String category) {
        ResultSet cars = dbc.getCarsList();
        try {
            while (cars.next()) {
                if (cars.getString("kategorija").equals(category) && cars.getInt("fk_marsrutas") == -1 && cars.getInt("fk_kurjeris") == -1) {
                    return cars.getInt("id");
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return -1;
    }

    ObservableList<OrderedProduct> readOrderedProducts(String carID) {
        final ObservableList<OrderedProduct> data = FXCollections.observableArrayList();
        ResultSet products = dbc.getOrderedProducts();
        try {
            while (products.next()) {
                data.add(new OrderedProduct(products.getString("id"), products.getString("svoris"), products.getString("kiekis"), products.getString("pristatymo_adresas"), products.getString("fk_preke"), products.getString("fk_vartotojas"), products.getString("fk_marsrutas"), carID));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return data;
    }
}
