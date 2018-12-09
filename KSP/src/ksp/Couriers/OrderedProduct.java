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
public class OrderedProduct {
    private SimpleStringProperty id;
    private SimpleStringProperty weight;
    private SimpleStringProperty count;
    private SimpleStringProperty address;
    private SimpleStringProperty fk_product;
    private SimpleStringProperty fk_user;
    private SimpleStringProperty fk_route;
    private SimpleStringProperty fk_automobilis;

    public OrderedProduct(String id, String weight, String count, String address, String fk_product, String fk_user) {
        this.id = new SimpleStringProperty(id);
        this.weight = new SimpleStringProperty(weight);
        this.count = new SimpleStringProperty(count);
        this.address = new SimpleStringProperty(address);
        this.fk_product = new SimpleStringProperty(fk_product);
        this.fk_user = new SimpleStringProperty(fk_user);
    }

    public OrderedProduct(String id, String weight, String count, String address, String fk_product, String fk_user, String fk_route) {
         this.id = new SimpleStringProperty(id);
        this.weight = new SimpleStringProperty(weight);
        this.count = new SimpleStringProperty(count);
        this.address = new SimpleStringProperty(address);
        this.fk_product = new SimpleStringProperty(fk_product);
        this.fk_user = new SimpleStringProperty(fk_user);
        this.fk_route = new SimpleStringProperty(fk_route);
    }

    public OrderedProduct(String id, String weight, String count, String address, String fk_product, String fk_user, String fk_route, String fk_automobilis) {
           this.id = new SimpleStringProperty(id);
        this.weight = new SimpleStringProperty(weight);
        this.count = new SimpleStringProperty(count);
        this.address = new SimpleStringProperty(address);
        this.fk_product = new SimpleStringProperty(fk_product);
        this.fk_user = new SimpleStringProperty(fk_user);
        this.fk_route = new SimpleStringProperty(fk_route);
        this.fk_automobilis = new SimpleStringProperty(fk_automobilis);
    }

    public String getFk_automobilis() {
        return fk_automobilis.getValue();
    }

    public void setFk_automobilis(SimpleStringProperty fk_automobilis) {
        this.fk_automobilis = fk_automobilis;
    }
    
    
    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getWeight() {
        return weight.getValue();
    }

    public void setWeight(String weight) {
        this.weight = new SimpleStringProperty(weight);
    }

    public String getCount() {
        return count.getValue();
    }

    public void setCount(String count) {
        this.count = new SimpleStringProperty(count);
    }

    public String getAddress() {
        return address.getValue();
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }

    public String getFk_product() {
        return fk_product.getValue();
    }

    public void setFk_product(String fk_product) {
        this.fk_product = new SimpleStringProperty(fk_product);
    }

    public String getFk_user() {
        return fk_user.getValue();
    }

    public void setFk_user(String fk_user) {
        this.fk_user = new SimpleStringProperty(fk_user);
    }

    public String getFk_route() {
        return fk_route.getValue();
    }

    public void setFk_route(String fk_route) {
        this.fk_route = new SimpleStringProperty(fk_route);
    }

    @Override
    public String toString() {
        return "OrderedProduct{" + "id=" + id + ", weight=" + weight + ", count=" + count + ", address=" + address + ", fk_product=" + fk_product + ", fk_user=" + fk_user + ", fk_route=" + fk_route + '}';
    }
    
    
    
}

