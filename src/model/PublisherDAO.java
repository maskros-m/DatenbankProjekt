/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnector;

/**
 *
 * @author flygm
 */
public class PublisherDAO {
    public static ObservableList<Publisher> getPublishers () {
        ObservableList<Publisher> pubs = FXCollections.observableArrayList();
        pubs.add(new Publisher(0, "-- All", ""));
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT id, name, location from publisher"
                    + " ORDER BY name";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                pubs.add(new Publisher(
                     rs.getInt("id"),
                     rs.getString("name"),
                     rs.getString("location")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return pubs;
    }
    
    public static Publisher findPublisherByBook (int bookId) {
        Publisher pub = new Publisher();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT * from publisher p"
                    + " LEFT JOIN books b ON b.publisher_id = p.id"
                    + " WHERE b.id=" + bookId;
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            rs.next();
            pub.setId(rs.getInt("id"));
            pub.setName(rs.getString("name"));
            pub.setLocation(rs.getString("location"));

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return pub;
    }
}
