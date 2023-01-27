/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnector;

/**
 *
 * @author mido
 */
public class AuthorDAO {
    public static ObservableList<Author> getAuthors () {
        ObservableList<Author> authors = FXCollections.observableArrayList();
        authors.add(new Author(0, "", "-- All"));
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT id, lastName, firstName from author"
                    + " ORDER BY lastName";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                authors.add(new Author(
                     rs.getInt("id"),
                     rs.getString("firstName"),
                     rs.getString("lastName")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return authors;
    }
    
    public static ArrayList<Author> findAuthorsByBook(int bookId) {
        ArrayList<Author> allAuthors = new ArrayList<Author>();
        Connection con;
        try {
            con = DBConnector.connect();
            String sql = "SELECT * FROM author a "
                    + "LEFT JOIN author_book ab ON ab.author_id = a.id " 
                    + "LEFT JOIN books b ON b.id = ab.book_id " 
                    + "WHERE b.id =" + bookId;
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                allAuthors.add(new Author(
                     rs.getInt("id"),
                     rs.getString("firstName"),
                     rs.getString("lastName")                        
                ));
            }
        } catch (SQLException exc) {
            System.out.println(exc);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return allAuthors;
    }
}
