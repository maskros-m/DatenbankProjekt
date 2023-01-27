package util;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mido
 */
public class DAO {

    public static void executeDML(String sql) {
        Connection con = null;
        try {
            con = DBConnector.connect();
            con.createStatement().executeUpdate(sql);
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
            return;
        }
//        finally {
//            if (con!=null) {
//                con.close();
//            }
//        }
    }
    
    public static int getDuration() {
        Connection con = null;
        int duration = 0;
        try {
            con = DBConnector.connect();
            String sql = "SELECT duration from duration WHERE id=1";
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            duration = rs.getInt("duration");
        } catch (SQLException e) {
            // System.out.println(e);
            e.printStackTrace();
        }
//        } finally {
//            if (con!=null) con.close();
//        }
        
        return duration;
    }
    
}