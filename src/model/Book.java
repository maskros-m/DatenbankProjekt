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
public class Book {
    private int id;
    private String title;
    private String description;
    private int year;
    private int edition;
    private int copies;
    private int publisher_id;
    private int genre_id;
    private String cover_img;
    private float price;
    
    public Book() {
       // default constructor
    }
    
    public Book (int id, String title, String description, int year, 
                 int edition, int copies, int publisher_id, int genre_id,
                 String cover_img, float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.edition = edition;
        this.copies = copies;
        this.publisher_id = publisher_id;
        this.genre_id = genre_id;
        this.cover_img = cover_img;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    
    
    @Override
    public String toString() {
        return title;
    }
}
