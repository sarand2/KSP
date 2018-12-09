/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.Couriers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class planRouteController implements Initializable {

    dbConnection dbc = new dbConnection();
    ObservableList<OrderedProduct> choosedProducts;
    MainViewManager mvm;
    LoginManager lg;
    String sID;
    String carID;
    char courierID;
    LinkedHashMap<Location, Location> locations;
    Location startLocation;
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button main;
    @FXML
    private Label label;
    @FXML
    private TableView<OrderedProduct> table;
    @FXML
    private TableColumn id = new TableColumn("ID");
    @FXML
    private TableColumn fk_product = new TableColumn("Prekė");
    @FXML
    private TableColumn count = new TableColumn("Kiekis");
    @FXML
    private TableColumn fk_user = new TableColumn("Užsakovas");
    @FXML
    private TableColumn address = new TableColumn("Pristatymo adresas");
    @FXML
    private Button showOnMap;
    @FXML
    private Button planRoute;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void planRouteButton(ActionEvent e) {
        if (table.getItems().isEmpty()) {
            table.setItems(choosedProducts);
            if (checkCourierRouteStatus(courierID)) {
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                String routeQuery = "INSERT INTO marsrutai(data,pradine_vieta,bendras_svoris,fk_uzsakytos_prekes,fk_kurjeris) values('" + date + "','" + startLocation.getAddress() + "'," + getLoadSize() + ",'" + getLoadedProductsString() + "'," + courierID + ");";
                boolean insertRouteStatus = dbc.executeUpdate(routeQuery);
                if (insertRouteStatus) {
                    System.out.println("Sukurtas maršrutas.");
                } else {
                    System.out.println("Kurjeris jau turi priskirtą maršrutą, arba nepavyksta priskirti.");
                }
                String couriersUpdateQuery = "UPDATE kurjeriai SET fk_marsrutas='" + getRouteID(courierID) + "' WHERE id='" + courierID + "';";
                boolean couriersUpdateStatus = dbc.executeUpdate(couriersUpdateQuery);
                if (couriersUpdateStatus) {
                    System.out.println("Pavyko atnaujinti kurjerio duomenis.");
                } else {
                    System.out.println("Nepavyko atnaujinti kurjerio duomenis.");
                }
                String carsUpdateQuery = "UPDATE automobiliai SET fk_marsrutas='" + getRouteID(courierID) + "' WHERE id='" + getCourierCarID(courierID) + "';";
                boolean carsUpdateStatus = dbc.executeUpdate(carsUpdateQuery);
                if (carsUpdateStatus) {
                System.out.println("Pavyko atnaujinti automobilio duomenis.");
                } else {
                System.out.println("Nepavyko atnaujinti automobilio duomenis.");
                }              
                boolean allProductsUpdated = false;
                for (int i = 0; i < choosedProducts.size(); i++) {
                    String filledProductQuery = "UPDATE uzsakytos_prekes SET fk_marsrutas='" + getRouteID(courierID) + "' WHERE id='" + choosedProducts.get(i).getId() + "';";
                    boolean filledProductStatus = dbc.executeUpdate(filledProductQuery);
                    if (filledProductStatus) {
                        System.out.println("Pavyko atnaujinti " + choosedProducts.get(i).getId() + " irasa.");
                        allProductsUpdated = true;
                    } else {
                        System.out.println("Nepavyko atnaujinti " + choosedProducts.get(i).getId() + " iraso.");
                        allProductsUpdated = false;
                    }
                }
                if (insertRouteStatus && couriersUpdateStatus && allProductsUpdated) {
                    label.setText("Maršrutas sukurtas.");
                } else {
                    label.setText("Neįmanoma sukurti maršruto.");
                }
            } else {
                label.setText("Kurjeris jau turi priskirtą maršrutą.");
            }
        }
    }

    public void showMapButton(ActionEvent e) {
        StringBuilder url = new StringBuilder();
        url.append("https://www.google.com/maps/dir/");
        locations.forEach((k, v) -> System.out.println("From: " + k.getLat() + "," + k.getLon() + " To: " + v.getLat() + "," + v.getLon()));
        Map.Entry<Location, Location> entry = locations.entrySet().iterator().next();
        Location startLocation = entry.getKey();
        url.append(startLocation.getLat() + "," + startLocation.getLon() + "/");
        locations.forEach((k, v) -> url.append(v.getLat() + "," + v.getLon() + "/"));
        url.append(startLocation.getLat() + "," + startLocation.getLon() + "/");
        System.out.println(url.toString());
        AdminManager adminManager = new AdminManager(mvm.getScene(), mvm.getStage());
        adminManager.navigateMap(mvm, lg, sID, url.toString());

    }

    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        mvm = mainManager;
        lg = loginManager;
        sID = sessionID;
        courierID = sessionID.charAt(sessionID.length() - 1);
        locations = getRoute(courierID);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setMinWidth(30);
        id.setMaxWidth(30);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fk_product.setMinWidth(50);
        fk_product.setMaxWidth(50);
        fk_product.setCellValueFactory(new PropertyValueFactory<>("fk_product"));
        count.setMinWidth(40);
        count.setMaxWidth(40);
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        fk_user.setMinWidth(80);
        fk_user.setMaxWidth(80);
        fk_user.setCellValueFactory(new PropertyValueFactory<>("fk_user"));
        address.setMinWidth(60);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        table.getColumns().addAll(id, fk_product, count, fk_user, address);

        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });

        backButton.setOnAction((ActionEvent event) -> {
            mainManager.navigateCouriers(loginManager, sessionID);
        });
    }

    public LinkedHashMap<Location, Location> getRoute(char id) {
        carID = getCourierCarID(id);
        choosedProducts = readOrderedProducts(carID);
        startLocation = new Location(getCourierLocation(id));
        ObservableList<Location> locations = FXCollections.observableArrayList();
        locations.add(startLocation);
        for (int i = 0; i < choosedProducts.size(); i++) {
            locations.add(new Location(choosedProducts.get(i).getAddress()));
        }
        LinkedHashMap<Location, Location> routeMap = new LinkedHashMap<Location, Location>();
        while (locations.size() - 1 > 0) {
            Location fromLoc = locations.get(0);
            double minDistance = 10000;
            int toLocationID = -1;
            for (int i = 1; i < locations.size(); i++) {
                double distance = getDistance(fromLoc.getLat(), fromLoc.getLon(), locations.get(i).getLat(), locations.get(i).getLon(), "K");
                if (distance <= minDistance) {
                    minDistance = distance;
                    toLocationID = i;
                }
            }
            Location toLoc = locations.get(toLocationID);
            Location temp = toLoc;
            locations.remove(toLoc);
            routeMap.put(fromLoc, temp);
            locations.remove(fromLoc);
            locations.add(0, temp);
        }

        return routeMap;
    }

    boolean checkCourierRouteStatus(char id) {
        ResultSet couriers = dbc.getCouriersList();
        try {
            while (couriers.next()) {
                if (couriers.getString("id").equals(Character.toString(id))) {
                    if (couriers.getInt("fk_marsrutas") == -1) {
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return false;
    }

    int getRouteID(char id) {
        ResultSet routes = dbc.getRoutes();
        try {
            while (routes.next()) {
                if (routes.getString("fk_kurjeris").equals(Character.toString(id))) {
                    return routes.getInt("id");
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return -1;
    }

    String getLoadedProductsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < choosedProducts.size(); i++) {
            sb.append(choosedProducts.get(i).getId() + "-");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    double getLoadSize() {
        double result = 0;
        for (int i = 0; i < choosedProducts.size(); i++) {
            result += Double.parseDouble(choosedProducts.get(i).getWeight()) * Double.parseDouble(choosedProducts.get(i).getCount());
        }
        return result;
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

    String getCourierLocation(char id) {
        ResultSet couriers = dbc.getCouriersList();
        try {
            while (couriers.next()) {
                if (couriers.getString("id").equals(Character.toString(id))) {
                    return couriers.getString("buvimo_vieta");
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return null;
    }

    ObservableList<OrderedProduct> readOrderedProducts(String carID) {
        final ObservableList<OrderedProduct> data = FXCollections.observableArrayList();
        ResultSet products = dbc.getOrderedProductsByCarID(carID);
        try {
            while (products.next()) {
                data.add(new OrderedProduct(products.getString("id"), products.getString("svoris"), products.getString("kiekis"), products.getString("pristatymo_adresas"), products.getString("fk_preke"), products.getString("fk_vartotojas"), products.getString("fk_marsrutas")));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return data;
    }

    private static double getDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

}
