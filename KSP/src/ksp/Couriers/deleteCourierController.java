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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import ksp.LoginManager;
import ksp.MainViewManager;

public class deleteCourierController implements Initializable {
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
    private Button delete;
    @FXML
    public TableView<Courier> table;
    @FXML
    private TableColumn id = new TableColumn("ID");
    @FXML
    private TableColumn name = new TableColumn("Vardas");
    @FXML
    private TableColumn surname = new TableColumn("Pavardė");
    @FXML
    private TableColumn personalID = new TableColumn("Asmens kodas");
    @FXML
    private TableColumn category = new TableColumn("Kategorija");
    @FXML
    private Label label;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void deleteButton(ActionEvent e){
      label.setText("");
      TableViewSelectionModel<Courier> tableS = table.getSelectionModel(); 
      if(!tableS.isEmpty()){
        ObservableList<Courier> selected = tableS.getSelectedItems();
        String query = "DELETE FROM kurjeriai WHERE id='" + selected.get(0).getId() + "';";
        boolean succesful = dbc.executeQuery(query);
        if(succesful){
            label.setText("Įrašas ištrintas.");
        } else {
            label.setText("Neįmanoma ištrinti įrašo.");
        }
        table.setItems(readCouriers());
      }else {
          label.setText("Prašome pasirinkti norimą ištrinti įrašą.");
      }
   }
    ObservableList<Courier> readCouriers(){
        final ObservableList<Courier> data = FXCollections.observableArrayList();
        ResultSet couriers = dbc.getCouriersList();
        try{
            while(couriers.next()){
                data.add(new Courier(couriers.getString("id"),couriers.getString("vardas"),couriers.getString("pavarde"),couriers.getString("asmens_kodas"),couriers.getString("auto_kategorija")));
            }
        }catch(SQLException ex){
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return data;
    }
    void initView(MainViewManager mainManager, LoginManager loginManager, String sessionID) {
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setMinWidth(3);
        id.setText("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setMinWidth(20);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setMinWidth(20);
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        personalID.setMinWidth(11);
        personalID.setCellValueFactory(new PropertyValueFactory<>("personalID"));
        category.setMinWidth(10);
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        
        table.setItems(readCouriers());
        table.getColumns().addAll(id,name,surname,personalID,category);
        
        
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
    
}

