/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import util.DAO;

/**
 *
 * @author mido
 */
public class Transaction {
    private int id;
    private int copyId;
    private int bookId;
    private int patronId;
    private String lendDate;
    private String returnDate;
    private String new_lendDate;
    private int duration;
    private int pastRenewal;
    private int availability;
    
    
    private String status;
    private String copyCode;
    private int currRenewal;
    private LocalDate retDate;
    private String patronLName;
    private String bookTitle;
    private float fine;
    private boolean isDirty = false;
    
    private float penaltyFee = 0.2f;
    
    LibraryDateTime ldt = new LibraryDateTime();

    public Transaction() {
    }

    public Transaction(int id, int copyId, int bookId, int patronId, String lendDate, 
            String returnDate, int renewal, int availability) throws ParseException {
        this.id = id;
        this.copyId = copyId;
        this.bookId = bookId;
        this.patronId = patronId;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.pastRenewal = renewal;
        this.availability = availability;
        if (lendDate != null) { 
            setRenewal(lendDate, renewal);
            setDuration(lendDate, returnDate);
            setFine2(returnDate);
            // System.out.println("From Transaction.Constructor: fine=" + fine);
        }
        if (returnDate != null) { 
            setRetDate(returnDate); 
        }
        setCopyCode(bookId, copyId);
        setStatus(availability);
        setPatronLName(patronId);
        setBookTitle(bookId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getNew_lendDate() {
        return new_lendDate;
    }

    public void setNew_lendDate(String new_lendDate) {
        this.new_lendDate = new_lendDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(String lendDate, String returnDate) {       
        this.duration = (int) ldt.calculateDuration(lendDate, returnDate);
    }
    
    public int getRenewalValue() {
        return currRenewal;
    }

    public String getCurrRenewal() {
        return currRenewal + "/3";
    }

    private void setRenewal(String borrowDate, int pastRenewal) throws ParseException {
        long days = ldt.findLendingDuration(borrowDate);
        int dur = DAO.getDuration();
        int renew = (int) (days / dur);
        this.currRenewal = renew + pastRenewal;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
        setStatus(availability);
    }
    
    private void setStatus(int availability) {
        switch (availability) {
            case 0: this.status = "Available"; break;
            case 1: this.status = "On Loan"; break;
            case 2: this.status = "Reserved"; break; // noch nicht implementiert
        }
    }
    
    public String getStatus() {
        return status;
    }
    
    private void setCopyCode(int bookId, int copyId) {
        this.copyCode = String.format("%03d", bookId) + String.format("%04d", copyId);
    }
    
    public String getCopyCode() {
        return copyCode;
    }
    
    private void setPatronLName(int patronId) {
        this.patronLName = KundenDAO.getPatronLastName(patronId);
    }
    
    public String getPatronLName () {
        return patronLName;
    }
    
    private void setBookTitle(int bookId) {
        this.bookTitle = BookDAO.fetchTitleById(bookId);
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public void setFine(String startDate) throws ParseException {
        long days = ldt.findLendingDuration(startDate) - 1;
        this.fine = ((int)days - duration) > 0 ? ((int)days - duration) * penaltyFee : 0.0f;
    }
    
    public void setFine2(String returnDate) throws ParseException {
        int daysOverdue = (int) ldt.calculateDuration(returnDate, LocalDate.now().toString());
        this.fine = daysOverdue > 0? daysOverdue * penaltyFee : 0.0f;
    }
    
    public String getFine() {
        DecimalFormat df = new DecimalFormat("0.00");
        return fine > 0.0 ? df.format(fine) + " €" : "Keine";
    }
    
    public float getFineValue() {
        return fine;
    }

    public LocalDate getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) throws ParseException {
        LibraryDateTime ldt = new LibraryDateTime();
        this.retDate = ldt.dateStringToLocalDate(retDate);
    }
    
    public void setDirty(boolean value) {
        this.isDirty = value;
    }
    
    public boolean isDirty() {
        return this.isDirty;
    }
    
}
