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
import javafx.scene.control.cell.PropertyValueFactory;
import ksp.LoginManager;
import ksp.MainViewManager;

public class findCourierLocationController implements Initializable {
    dbConnection dbc = new dbConnection();
    MainViewManager mvm;
    LoginManager lg;
    String sID;
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;
    @FXML
    private Button main;
    @FXML
    private Button backButton;
    @FXML
    private Button showOnMap;
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
    private TableColumn location = new TableColumn("Buvimo vieta");
    @FXML
    private Label label;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void showMapButton(ActionEvent e){
      label.setText("");
      TableView.TableViewSelectionModel<Courier> tableS = table.getSelectionModel(); 
      if(!tableS.isEmpty()){
        ObservableList<Courier> selected = tableS.getSelectedItems();
        final String addr = selected.get(0).getLocation();
        final String url = "https://nominatim.openstreetmap.org/search.php?q=" + addr + "&polygon_geojson=1&viewbox=";
        AdminManager adminManager = new AdminManager(mvm.getScene(), mvm.getStage());
        adminManager.navigateMap(mvm, lg, sID,url); 
        table.setItems(readCouriers());
      }else {
          label.setText("Prašome pasirinkti norimą kurjerį.");
      }
   }
    void initView(MainViewManager mainManager, LoginManager loginManager, String sessionID,String address) {
        mvm = mainManager;
        lg=loginManager;
        sID=sessionID;
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
        location.setMinWidth(30);
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        table.setItems(readCouriers());
        table.getColumns().addAll(id,name,surname,personalID,location);
            
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
       ObservableList<Courier> readCouriers(){
        final ObservableList<Courier> data = FXCollections.observableArrayList();
        ResultSet couriers = dbc.getCouriersList();
        try{
            while(couriers.next()){
                data.add(new Courier(couriers.getString("id"),couriers.getString("vardas"),couriers.getString("pavarde"),couriers.getString("asmens_kodas"),couriers.getString("auto_kategorija"),couriers.getString("buvimo_vieta")));
            }
        }catch(SQLException ex){
            System.out.println("ERROR: " + ex);
            ex.printStackTrace();
        }
        return data;
    }
    
}
