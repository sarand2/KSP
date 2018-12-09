/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.Couriers;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Admin
 */
public class Courier {

   
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty personalID;
    private SimpleStringProperty category;
    private SimpleStringProperty location;
    private SimpleStringProperty routeID;
    private SimpleStringProperty carID;
    
    public Courier(String id, String name, String surname, String personalID, String category) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.personalID = new SimpleStringProperty(personalID);
        this.category = new SimpleStringProperty(category);
    }
     public Courier(String id, String name, String surname, String personalID, String category, String location) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.personalID = new SimpleStringProperty(personalID);
        this.category = new SimpleStringProperty(category);
        this.location = new SimpleStringProperty(location);
    }
     public Courier(SimpleStringProperty id, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty personalID, SimpleStringProperty category, SimpleStringProperty location, SimpleStringProperty routeID, SimpleStringProperty carID) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personalID = personalID;
        this.category = category;
        this.location = location;
        this.routeID = routeID;
        this.carID = carID;
    }
    public String getLocation(){
        return location.get();
    }
    public void setLocation(String location){
        this.location = new SimpleStringProperty(location);
    }
     public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname = new SimpleStringProperty(surname);
    }

    public String getPersonalID() {
        return personalID.get();
    }

    public void setPersonalID(String personalID) {
        this.personalID = new SimpleStringProperty(personalID);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category = new SimpleStringProperty(category);
    }

    public SimpleStringProperty getRouteID() {
        return routeID;
    }

    public void setRouteID(SimpleStringProperty routeID) {
        this.routeID = routeID;
    }

    public SimpleStringProperty getCarID() {
        return carID;
    }

    public void setCarID(SimpleStringProperty carID) {
        this.carID = carID;
    }
    
}
