/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksp.Couriers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class dbConnection {

    private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private String MYSQL_URL = "jdbc:mysql://localhost:3306/ksp";
    private String MYSQL_USER = "root";
    private String MYSQL_PASSWD = "";
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public dbConnection() {
        try {
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            con = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWD);
            System.out.println("Connected to the database....");
            st = con.createStatement();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException:\n" + ex.toString());
            ex.printStackTrace();

        } catch (SQLException ex) {
            System.out.println("SQLException:\n" + ex.toString());
            ex.printStackTrace();
        }
    }

    public ResultSet getCouriersList() {
        try {
            rs = st.executeQuery("SELECT * FROM kurjeriai");
        } catch (SQLException ex) {
            System.out.println("SQLException:\n" + ex.toString());
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet getRoutes() {
        try {
            rs = st.executeQuery("SELECT * FROM marsrutai");
        } catch (SQLException ex) {
            System.out.println("SQLException:\n" + ex.toString());
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet getOrderedProducts() {
        try {
            rs = st.executeQuery("SELECT * FROM uzsakytos_prekes");
        } catch (SQLException ex) {
            System.out.println("SQLException:\n" + ex.toString());
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet getOrderedProductsByCarID(String carID) {
        try {
            rs = st.executeQuery("SELECT * FROM uzsakytos_prekes WHERE fk_automobilis='" + carID + "';");
        } catch (SQLException ex) {
            System.out.println("SQLException:\n" + ex.toString());
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet getCarsList() {
        try {
            rs = st.executeQuery("SELECT * FROM automobiliai");
        } catch (SQLException ex) {
            System.out.println("SQLException:\n" + ex.toString());
            ex.printStackTrace();
        }
        return rs;
    }

    public boolean executeUpdate(String query) {
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("SQLException:\n" + ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public ResultSet getCategories() {
        try {
            rs = st.executeQuery("SELECT * FROM kategorijos");
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getWarehouses() {
        try {
            rs = st.executeQuery("SELECT * FROM sandeliai");
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getWarehouses(String product) {
        try {
            rs = st.executeQuery(String.format("SELECT * FROM sandeliai, (SELECT * FROM sandelio_prekes WHERE sandelio_prekes.prekes_kodas=\'%s\') AS x WHERE x.sandelio_id!=sandeliai.id", product));
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getWarehouses(String product, boolean b) {
        try {
            rs = st.executeQuery(String.format("SELECT * FROM sandeliai INNER JOIN sandelio_prekes ON sandelio_prekes.sandelio_id=sandeliai.id WHERE sandelio_prekes.prekes_kodas=\'%s\'", product));
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getWarehouses(int warehouse) {
        try {
            rs = st.executeQuery(String.format("SELECT * FROM sandeliai WHERE id!=%d", warehouse));
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getProducts() {
        try {
            rs = st.executeQuery("SELECT * FROM prekes");
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getProducts(int warehouse) {
        try {
            rs = st.executeQuery(String.format("SELECT * FROM prekes, (SELECT * FROM sandelio_prekes WHERE sandelio_prekes.sandelio_id=%d) AS x WHERE x.prekes_kodas!=prekes.kodas", warehouse));
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getProducts(int warehouse, boolean b) {
        try {
            rs = st.executeQuery(String.format("SELECT * FROM prekes INNER JOIN sandelio_prekes ON sandelio_prekes.prekes_kodas=prekes.kodas WHERE sandelio_prekes.sandelio_id=%d", warehouse));
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getProductInWarehouse(int warehouse, String product) {
        try {
            rs = st.executeQuery(String.format("SELECT * FROM sandelio_prekes WHERE sandelio_id=%d AND prekes_kodas=\'%s\'", warehouse, product));
            System.out.println(String.format("SELECT * FROM sandelio_prekes WHERE sandelio_id=%d AND prekes_kodas=\'%s\'", warehouse, product));
        } catch (SQLException e) {
            System.out.println("'SQLException:\n" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

}
