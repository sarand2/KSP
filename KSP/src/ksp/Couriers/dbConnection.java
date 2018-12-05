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
public  dbConnection() {
    try {
      Class.forName(MYSQL_DRIVER);
      System.out.println("Class Loaded....");
      con = DriverManager.getConnection(MYSQL_URL,MYSQL_USER,MYSQL_PASSWD);
      System.out.println("Connected to the database....");
      st = con.createStatement();
    } catch(ClassNotFoundException ex) {
       System.out.println("ClassNotFoundException:\n"+ex.toString());
       ex.printStackTrace();

    } catch(SQLException ex) {
        System.out.println("SQLException:\n"+ex.toString());
        ex.printStackTrace();
    }
  }  
public ResultSet getCouriersList()
{
    try{
         rs = st.executeQuery("SELECT * FROM kurjeriai");    
    } catch(SQLException ex){
        System.out.println("SQLException:\n"+ex.toString());
        ex.printStackTrace();
    }
   return rs;
}
public boolean executeQuery(String query){
    try{
    st.executeUpdate(query);
    }catch(SQLException ex){
        System.out.println("SQLException:\n"+ex.toString());
        ex.printStackTrace();
        return false;
    }
    return true;
}




}
