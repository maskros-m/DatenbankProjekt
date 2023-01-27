/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mido
 */
public class Kunden {
    private int patronId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Kunden() {
    }

    public Kunden(int patronId, String firstName, String lastName, String email, String phone) {
        this.patronId = patronId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    
    public int getPatronId() {
        return patronId;
    }
    
    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return firstName + " " + lastName + " <'" + email + "'> [ " + phone + " ]";
    }
    
}
