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
import util.DAO;
import util.DBConnector;

/**
 *
 * @author mido
 */
public class KundenDAO {
    public static ObservableList<String> getPatronsByLastName() {
        ObservableList<String> patrons = FXCollections.observableArrayList();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT * FROM patron GROUP BY lastName ORDER BY lastName ASC";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                patrons.add(rs.getString("lastName"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
        
        return patrons;
    }
    
    public static ObservableList<Kunden> getPatrons(String searchString) {
        ObservableList<Kunden> patrons = FXCollections.observableArrayList();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT * FROM patron"
                    + " WHERE lastName='" + searchString + "' "
                    + "ORDER BY firstName ASC";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                patrons.add(new Kunden(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
        
        return patrons;
    }
    
    public static ObservableList<Kunden> getPatronsByName(String searchString) {
        ObservableList<Kunden> patrons = FXCollections.observableArrayList();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT * FROM patron"
                    + " WHERE lastName LIKE '" + searchString + "%' "
                    + "OR firstName LIKE '" + searchString + "%' "
                    + "ORDER BY firstName ASC";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                patrons.add(new Kunden(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
        
        return patrons;
    }
    
    public void registerNewPatron(String firstName, String lastName, String email, String phone) {
        String emailSQL = email.equals("")? null : "'" + email + "'";
        String phoneSQL = phone.equals("")? null : "'" + phone + "'";
        String sql = "INSERT INTO patron (firstName, lastName, email, phone)"
                + " VALUES ('" + firstName + "', '" + lastName + "', "
                + emailSQL + ", " + phoneSQL + ")";
        System.out.println(sql);
        DAO.executeDML(sql);
    }
    
    public Kunden findPatron(String firstName, String lastName, String email, String phone) {
        Kunden patron = new Kunden();
        Connection con;
        
        String emailWHEREclause = email.equals("") ? " IS NULL " : "='" + email + "' ";
        String phoneWHEREclause = phone.equals("") ? " IS NULL" : "='" + phone + "'";
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT * FROM patron"
                    + " WHERE firstName='" + firstName + "' "
                    + "AND lastName='" + lastName + "' "
                    + "AND email" + emailWHEREclause + "AND phone" + phoneWHEREclause;
            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                patron.setPatronId(rs.getInt("id"));
                patron.setFirstName(rs.getString("firstName"));
                patron.setLastName(rs.getString("lastName"));
                patron.setEmail(rs.getString("email"));
                patron.setPhone(rs.getString("phone"));
            };         

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
        return patron;
    }
    
    public static String getPatronLastName(int patronId) {
        String lastName = "";
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT lastName FROM patron"
                    + " WHERE id=" + patronId;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                lastName = rs.getString("lastName");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            // ex.printStackTrace();
        } 
        
        return lastName;
    }
}
