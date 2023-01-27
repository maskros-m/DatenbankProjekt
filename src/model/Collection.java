/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author mido
 */
public class Collection {
    private int titleId;
    private String title;
    private String authors;
    private int copies;
//    private boolean availability;

    public Collection() {
    }

    public Collection(int id, String title, String authors, int copies) {
        this.titleId = id;
        this.title = title;
        this.authors = authors;
        this.copies = copies;
//        this.availability = availability;
    }
    
    public int getBookId() {
        return titleId;
    }
    
    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getCopies() {
        return copies;
    }
}
