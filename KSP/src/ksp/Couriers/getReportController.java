/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.Couriers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import ksp.LoginManager;
import ksp.MainViewManager;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class getReportController implements Initializable {
    dbConnection dbc = new dbConnection();
    ObservableList<OrderedProduct> filledProducts;
    String carID = "";
    char courierID = '0';
    @FXML private Button logoutButton;
    @FXML private Label  sessionLabel;
    @FXML private Button backButton;
    @FXML private Button generateReport;
    @FXML private Button accept;
    @FXML private Label state;
    @FXML private Button main;
    @FXML private DatePicker date;
    @FXML private Label label;
    @FXML public TableView<OrderedProduct> table;
    @FXML private TableColumn id = new TableColumn("ID");
    @FXML private TableColumn productID = new TableColumn("Prekės kodas");
    @FXML private TableColumn userID = new TableColumn("Užsakovas");
    @FXML private TableColumn address = new TableColumn("Adresas");
    @FXML private TableColumn delivered = new TableColumn("Pristatyta");
    
     public void acceptButton(ActionEvent e) {
         if(!table.getItems().isEmpty()){
            boolean allProductsUpdated = false;
            for (int i = 0; i < filledProducts.size(); i++) {       
                String filledProductQuery = "UPDATE uzsakytos_prekes SET pristatyta='" + 1 + "' WHERE id='" + filledProducts.get(i).getId() + "';";           
                boolean filledProductStatus = dbc.executeUpdate(filledProductQuery);
                if (filledProductStatus) {
                    System.out.println("Pavyko atnaujinti " + filledProducts.get(i).getId() + " irasa.");
                    allProductsUpdated = true;
                } else {
                    System.out.println("Nepavyko atnaujinti " + filledProducts.get(i).getId() + " iraso.");
                    allProductsUpdated = false;
                }
            }
            if(allProductsUpdated){
                label.setText("Ataskaita patvirtinta.");
                LocalDate dateVal = date.getValue();
                String dateStr = dateVal.toString();
                filledProducts = readOrderedProductsWithDate(carID,dateStr);
                table.setItems(filledProducts);  
            }
         } else {
             label.setText("Prašome pasirinkti ataskaitą.");
         }
     }
     
      public void generateReportButton(ActionEvent e) {
          label.setText("");
          if(date.getValue() != null){
              LocalDate dateVal = date.getValue();
              String dateStr = dateVal.toString();
              filledProducts = readOrderedProductsWithDate(carID,dateStr);
              if(filledProducts.isEmpty()){
                  label.setText("Ataskaita nerasta.");
              } else {
                  table.setItems(filledProducts);           
              }           
          }
          else {
              label.setText("Pasirinkite norimą datą.");
          }     
      }
    ObservableList<OrderedProduct> readOrderedProductsWithDate(String carID, String date) {
        final ObservableList<OrderedProduct> data = FXCollections.observableArrayList();
        ResultSet products = dbc.getOrderedProducts();
        int stateTrue = 0;
        try {
            while (products.next()) {
                if(products.getString("priskyrimo_data").equals(date)){
                    String delivered = products.getString("pristatyta");
                    if(delivered.equals("0")){
                        delivered = "NE";
                    }else {
                        delivered = "TAIP";
                        stateTrue++;
                    }
                    data.add(new OrderedProduct(products.getString("id"), products.getString("svoris"), products.getString("kiekis"), products.getString("pristatymo_adresas"),products.getString("priskyrimo_data"),delivered, products.getString("fk_preke"), products.getString("fk_vartotojas"), products.getString("fk_marsrutas"), carID));
                }   
            }
            if(stateTrue == data.size()){
                state.setTextFill(Color.GREEN);
                state.setText("Patvirtinta");
                
            }else {
                state.setText("Nepatvirtinta");
                state.setTextFill(Color.RED);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return data;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    } 
    public void initView(final MainViewManager mainManager, final LoginManager loginManager, String sessionID) {
        state.setTextFill(Color.DARKORANGE);
        courierID = sessionID.charAt(sessionID.length() - 1);
        carID = getCourierCarID(courierID);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setMinWidth(30);
        id.setMaxWidth(30);
        id.setText("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        productID.setMinWidth(80);
        productID.setMaxWidth(80);
        productID.setCellValueFactory(new PropertyValueFactory<>("fk_product"));
        userID.setMinWidth(80);
        userID.setMaxWidth(80);
        userID.setCellValueFactory(new PropertyValueFactory<>("fk_user"));
        address.setMinWidth(190);
        address.setMaxWidth(190);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        delivered.setMinWidth(70);
        delivered.setMaxWidth(70);
        delivered.setCellValueFactory(new PropertyValueFactory<>("delivered"));
        
        table.getColumns().addAll(id,productID,userID,address,delivered);
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((ActionEvent event) -> {
            loginManager.logout();
        });
        main.setOnAction((ActionEvent event) -> {
            loginManager.authenticated(sessionID);
        });
        CourierManager couriersManager = new CourierManager(mainManager.getScene(), mainManager.getStage());
        backButton.setOnAction((ActionEvent event) -> {
            mainManager.navigateCouriers(loginManager, sessionID);
        });
    }
    
}
