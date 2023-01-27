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
public class GenreDAO {
    public static ObservableList<Genre> getGenres () {
        ObservableList<Genre> genres = FXCollections.observableArrayList();
        genres.add(new Genre(0, "-- All"));
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT id, genre from genre ORDER BY genre";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                genres.add(new Genre(
                     rs.getInt("id"),
                     rs.getString("genre")                     
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return genres;
    }
}
