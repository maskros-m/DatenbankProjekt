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
 * @author mido
 */
public class BookDAO {
    public static ObservableList<Book> getBooks () {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        bookList.add(new Book(0, "-- All", "", 0, 0, 0, 0, 0, "", 0));
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT id, title, description, year, " 
                    + " edition, copies, publisher_id, genre_id, "
                    + "cover_img, price "
                    + "from books ORDER BY title";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                bookList.add(new Book(
                     rs.getInt("id"),
                     rs.getString("title"),
                     rs.getString("description"),
                     rs.getInt("year"),
                     rs.getInt("edition"),
                     rs.getInt("copies"),
                     rs.getInt("publisher_id"),
                     rs.getInt("genre_id"),
                     rs.getString("cover_img"),
                     rs.getFloat("price")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return bookList;
    } // getBooks()
    
    public static Book fetchSpecificBook (int bookId) {
        Book lookupTitle = new Book();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT * FROM books " 
                    + "WHERE id=" + bookId;
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            lookupTitle.setId(rs.getInt("id"));
            lookupTitle.setTitle(rs.getString("title"));
            lookupTitle.setDescription(rs.getString("description"));
            lookupTitle.setYear(rs.getInt("year"));
            lookupTitle.setEdition(rs.getInt("edition"));
            lookupTitle.setCopies(rs.getInt("copies"));
            lookupTitle.setPublisher_id(rs.getInt("publisher_id"));
            lookupTitle.setGenre_id(rs.getInt("genre_id"));
            lookupTitle.setCover_img(rs.getString("cover_img"));
            lookupTitle.setPrice(rs.getFloat("price"));

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return lookupTitle;
    }
    
    public static Book fetchBookByCopy(int copyId) {
        Book lookupTitle = new Book();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT * FROM books b" 
                    + " LEFT JOIN copy c ON b.id = c.book_id"
                    + " WHERE c.copy_id=" + copyId;
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            lookupTitle.setId(rs.getInt("id"));
            lookupTitle.setTitle(rs.getString("title"));
            lookupTitle.setDescription(rs.getString("description"));
            lookupTitle.setYear(rs.getInt("year"));
            lookupTitle.setEdition(rs.getInt("edition"));
            lookupTitle.setCopies(rs.getInt("copies"));
            lookupTitle.setPublisher_id(rs.getInt("publisher_id"));
            lookupTitle.setGenre_id(rs.getInt("genre_id"));
            lookupTitle.setCover_img(rs.getString("cover_img"));
            lookupTitle.setPrice(rs.getFloat("price"));

        } catch (SQLException ex) {
            System.err.println("BookDAO.fetchBookByCopy: " + ex.getMessage());
        }
        return lookupTitle;
    }
    
    public static String fetchTitleById(int bookId) {
        String bookTitle = "";
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT title from books WHERE id=" + bookId;
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                bookTitle = rs.getString("title");
            }
        } catch (SQLException ex) {
            System.err.println("BookDAO.fetchTitleById: " + ex.getMessage());
        }
        return bookTitle;
    }
}
