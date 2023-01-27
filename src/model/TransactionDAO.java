/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DAO;
import util.DBConnector;

/**
 *
 * @author mido
 */
public class TransactionDAO {
    public static ObservableList<Transaction> getTransactionsByTitle (int bookId) throws ParseException {
        ObservableList<Transaction> trans = FXCollections.observableArrayList();
        Connection con;
        try {
            con = DBConnector.connect();
            String sql = "SELECT t.id, c.copy_id, c.book_id, t.patron_id, "
                    + "t.lend_date, t.return_date, t.renewal_history, "
                    + "t.availability FROM copy c"
                    + " LEFT JOIN books b ON b.id = c.book_id"
                    + " LEFT JOIN transactions t ON c.copy_id = t.copy_id"
                    + " WHERE b.id=" + bookId;
            ResultSet rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                trans.add(new Transaction(
                     rs.getInt("id"),
                     rs.getInt("copy_id"),
                     rs.getInt("book_id"),
                     rs.getInt("patron_id"),
                     rs.getString("lend_date"),
                     rs.getString("return_date"),
                     rs.getInt("renewal_history"),
                     rs.getInt("availability")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        } 
        return trans;
    }
    
    public ObservableList<Transaction> getBorrowingHistory(String searchStr, Kunden k) throws ParseException {
        ObservableList<Transaction> trans = FXCollections.observableArrayList();
        Connection con;
        
        String patronWHEREclause = k == null ? "" : " AND t.patron_id=" + k.getPatronId();
        try {
            con = DBConnector.connect();
            String sql = "SELECT t.id, c.copy_id, c.book_id, t.patron_id, "
                    + "t.lend_date, t.return_date, t.duration, "
                    + "t.renewal_history, t.availability FROM transactions t"
                    + " LEFT JOIN copy c ON c.copy_id = t.copy_id"
                    + " LEFT JOIN books b ON b.id = c.book_id"
                    + " WHERE b.title LIKE '%" + searchStr + "%'"
                    + patronWHEREclause;
            ResultSet rs = con.createStatement().executeQuery(sql);
            System.out.println(sql);
            while (rs.next()) {
                trans.add(new Transaction(
                     rs.getInt("id"),
                     rs.getInt("copy_id"),
                     rs.getInt("book_id"),
                     rs.getInt("patron_id"),
                     rs.getString("lend_date"),
                     rs.getString("return_date"),
                     rs.getInt("renewal_history"),
                     rs.getInt("availability")
                ));
            }
        } catch (SQLException ex) {
            // System.err.println(ex.getMessage());
            ex.printStackTrace();
        } 
        return trans;
    }
    
    public static void createNewLendingTransaction(int copyId, Kunden patron, 
                                String lendDate, String returnDate) {
        LibraryDateTime libDT = new LibraryDateTime();
        long days = libDT.calculateDuration(lendDate, returnDate) + 1;
        
        String sql = "INSERT INTO transactions (copy_id, patron_id, lend_date,"
                + " return_date, renewal_history, availability) VALUES("
                + copyId + ", " + patron.getPatronId() + ", '"
                + lendDate + "', '" + returnDate + "', 0, 1)";
        DAO.executeDML(sql);
    }
    
    public void deleteTransaction(int transId) {
        String sql = "DELETE FROM transactions WHERE id=" + transId;
        DAO.executeDML(sql);
    }
    
    public void updateNewLendDate(String newLendDate, int transId) {
        String sql = "UPDATE transactions set new_lendDate='" + newLendDate
                + "' WHERE id=" + transId;
        DAO.executeDML(sql);
    }
    
    public void updateNewReturnDate (String newReturnDate, long newDuration, int transId) {
        String sql = "UPDATE transactions set return_date='" + newReturnDate
                + "', duration=" + newDuration + " WHERE id=" + transId;
        DAO.executeDML(sql);
    }
    
    public void renewTransaction(String newLendDate, String newRetDate, int renewal, int transId) {
        String sql = "UPDATE transactions set lend_date='" + newLendDate
                + "', return_date='" + newRetDate + "', renewal_history=" + renewal
                + " WHERE id=" + transId;
        DAO.executeDML(sql);
    }

}
