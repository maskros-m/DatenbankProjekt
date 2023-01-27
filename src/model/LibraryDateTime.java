/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import util.DAO;

/**
 *
 * @author mido
 */
public class LibraryDateTime {

    public LibraryDateTime() {
    }
    
    public LocalDate incrementReturnDate (String lendDate, int renewal) throws ParseException {
        int duration = DAO.getDuration();
        LocalDate currentDate = LocalDate.now();
        int lendingDuration = (int) findLendingDuration(lendDate);
        
        // Formula: nextReturnDate = currentDate + (duration * (renewal + 1) - lendingDuration - 1);
        int increment = duration * (renewal + 1) - lendingDuration - 1;
        
        long nowToMillis = localDateToMillis(currentDate.toString());
        long incrementMillis = TimeUnit.DAYS.toMillis((long) increment);
        
        LocalDate nextReturnDate = Instant.ofEpochMilli(incrementMillis + nowToMillis)
                .atZone(ZoneId.systemDefault()).toLocalDate();
        
        System.out.println("LibraryDateTime.incrementReturnDate: " + nextReturnDate);
        return nextReturnDate;
    }
    
    public LocalDate datePlusDays (LocalDate startDate, int incrementDays) {
        ZoneId zone = ZoneId.systemDefault();
        long millis = startDate.atStartOfDay(zone).toInstant().toEpochMilli();
        long returnDateMillis = millis + TimeUnit.DAYS.toMillis((long) (incrementDays-1));
        LocalDate newDate = Instant.ofEpochMilli(returnDateMillis)
                .atZone(ZoneId.systemDefault()).toLocalDate();
        
        return newDate;
    }
    
    public LocalDate dateMinusDays (LocalDate date, int decrementDays) {
        ZoneId zone = ZoneId.systemDefault();
        long millis = date.atStartOfDay(zone).toInstant().toEpochMilli();
        long difference = millis - TimeUnit.DAYS.toMillis((long) (decrementDays)) + 1;
        LocalDate newDate = Instant.ofEpochMilli(difference)
                .atZone(ZoneId.systemDefault()).toLocalDate();
        
        return newDate;
    }
    
    public LocalDate dateStringToLocalDate (String dateString) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(dateString, dtf);
        return ld;
    }
    
    public long localDateToMillis (String localDate) throws ParseException {
        LocalDate d = dateStringToLocalDate(localDate);
        
        ZoneId zone = ZoneId.systemDefault();
        long millis = d.atStartOfDay(zone).toInstant().toEpochMilli();
        
        return millis;
    }
    
    public long findLendingDuration(String borrowDate) throws ParseException {
        long days = 0;
        long millis = localDateToMillis(borrowDate);
        long currentMillis = System.currentTimeMillis();
        days = TimeUnit.MILLISECONDS.toDays(currentMillis - millis) + 1;
        
        // System.out.println("LibraryDateTime.findLendingDuration: Duration= " + days);
        return days;
    }
    
    public long calculateDuration (String startDate, String endDate) {
        long days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date d1 = sdf.parse(startDate);
            Date d2 = sdf.parse(endDate);
 
            long millis_diff = d2.getTime() - d1.getTime(); 
            days = TimeUnit.MILLISECONDS.toDays(millis_diff);
 
            // System.out.println("LibraryDateTime.calculateDuration: Difference " + days);
        } catch (ParseException e) {
            System.out.println("LibraryDateTime.caculateDuration: " + e.getMessage());
            // e.printStackTrace();
        }
        return days;
    }
    
    public LocalDate dateToLocalDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
}
