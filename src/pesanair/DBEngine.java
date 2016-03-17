/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pesanair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JTextField;

/**
*
* @author cgg
*/
public class DBEngine
{
    /**
    * Connect to database
    * @return Connection to database
    * @throws java.lang.Exception
    */
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    DBEngine(){
        try{
        //loading driver MySQL/J
            Class.forName("com.mysql.jdbc.Driver");

            //membuat koneksi database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pjl?zeroDateTimeBehavior=convertToNull", "root", "");

            //statemen sql statik
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
        }
        catch(ClassNotFoundException e){
            System.err.println("Error loading driver: " + e.getMessage());
        }
        catch(SQLException e){
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    /**
    * This method will load vector of vector of string and load all the data in
    * the vector
    * @return vector of vector of string
    * @throws java.lang.Exception
    */
    
    public List<Customer> getCustomer() throws SQLException {
        DBEngine db = new DBEngine();
        List<Customer> customers = new ArrayList<>();
        
        rs = stmt.executeQuery("SELECT * FROM customer");
        
        while(rs.next()) {
            customers.add(new Customer(rs.getString("nama"), rs.getString("email"), rs.getString("password"), rs.getString("alamat")));
        }
        rs.close();
        
        return customers;
    }
    
    public void tambahData(Customer customer) {
        try{
            //eksekusi SQL-statemen untuk tabel penerbit
            String sql = "INSERT INTO customer (nama, email, password, alamat) values ('"+customer.getUsername()+"','"+customer.getEmail()+"','"+customer.getPassword()+"','"+customer.getAlamat()+"');";
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
            System.err.println("Error SQL: " + e.getMessage());
        }
    }
    
    public int check_login(String nama, String password) throws SQLException {
        DBEngine db = new DBEngine();
        String sql = "SELECT COUNT(*) FROM customer WHERE nama='"+nama+"' AND password='"+password+"'";
        rs = stmt.executeQuery(sql);
        rs.next();
        int cek = rs.getInt(1);
        return cek;
    }
    
}
