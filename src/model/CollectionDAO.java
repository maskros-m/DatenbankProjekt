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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.DBConnector;

/**
 *
 * @author mido
 */
public class CollectionDAO {
        
    public static ObservableList<Collection> fetchSearchResult (String searchStr,
            int genreId, int authorId, int publisherId) {
        String bookWHEREclause = "";
        String genreWHEREclause = "";
        String authorWHEREclause = "";
        String pubWHEREclause = "";
        bookWHEREclause = searchStr == "" ? "AND b.title LIKE '%'" : 
                "AND b.title LIKE '%" + searchStr + "%'";
        authorWHEREclause = authorId == 0? "" : " WHERE a.id=" + authorId;
        genreWHEREclause = genreId == 0? "" : " AND b.genre_id=" + genreId;
        pubWHEREclause = publisherId == 0? "" : " AND b.publisher_id=" + publisherId;
                
        Connection con;
        ObservableList<Collection> collection = FXCollections.observableArrayList();
        try {
            con = DBConnector.connect();
            String sql = "SELECT b.id, b.title, "
                    + "GROUP_CONCAT(CONCAT(' ', a.lastName, ' ', a.firstName)) as author"
                    + ", b.copies FROM `books` b " 
                    + "LEFT JOIN author_book ab ON ab.book_id = b.id " 
                    + "LEFT JOIN author a ON a.id = ab.author_id "
                    + "WHERE b.id IN (SELECT b.id FROM books b\n"
                            + "LEFT JOIN author_book ab ON b.id = ab.book_id\n"
                            + "LEFT JOIN author a ON ab.author_id = a.id"
                            + authorWHEREclause + ")\n"
                    + bookWHEREclause + genreWHEREclause
                    + pubWHEREclause
                    + " GROUP BY b.id";
            System.out.println(sql);
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                collection.add(new Collection (
                                rs.getInt("id"),
                                rs.getString("title"), 
                                rs.getString("author"), 
                                rs.getInt("copies") 
                ));
            }
            if (collection.size() == 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setHeaderText(null);
                alert.setContentText("There is no such book in collection.");

                alert.showAndWait();
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return collection;
    }
}
