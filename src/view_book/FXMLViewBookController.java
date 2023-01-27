/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_book;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.FXMLBibliothekController;
import model.Author;
import model.AuthorDAO;
import model.Book;
import model.BookDAO;
import model.Publisher;
import model.PublisherDAO;
import model.Transaction;
import model.TransactionDAO;

/**
 * FXML Controller class
 *
 * @author mido
 */
public class FXMLViewBookController implements Initializable {

    @FXML
    private ImageView imgBookCover;
    @FXML
    private Label lblBookTitle;
    @FXML
    private Label lblAuthors;
    @FXML
    private Label lblYear;
    @FXML
    private Label lblEdition;
    @FXML
    private Label lblPublisher;
    @FXML
    private TableView<Transaction> tbvLendingDetails;
    @FXML
    private TableColumn<Transaction, String> colCopyCode;
    @FXML
    private TableColumn<Transaction, String> colLendDate;
    @FXML
    private TableColumn<Transaction, String> colReturnDate;
    @FXML
    private TableColumn<Transaction, String> colRenewal;
    @FXML
    private TableColumn<Transaction, String> colAvailability;
    @FXML
    private Button btnBorrow;
    @FXML
    private Button btnReserve;
    
    private int selectedBookId = 0;
    private int selectedCopyId = 0;

    /**
     * Initialises the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblBookTitle.setMaxWidth(250);
        lblBookTitle.setWrapText(true);
    }    

    @FXML
    private void borrowBook(ActionEvent event) throws ParseException {
        FXMLBibliothekController openFunction = new FXMLBibliothekController();
        openFunction.openScene("/lendingDetails/FXMLenterLendingDetails.fxml", selectedCopyId, this.tbvLendingDetails);
        // openFunction.openScene("/lendingDetails/FXMLenterLendingDetails.fxml", selectedCopyId, null);
    }

    @FXML
    private void reserveBook(ActionEvent event) {
    }
    
    public void loadBookDetails(int bookId) throws ParseException {
        this.selectedBookId = bookId;
        Book thisBook = BookDAO.fetchSpecificBook(bookId);
        ArrayList<Author> allAuthors = AuthorDAO.findAuthorsByBook(bookId);
        String authorList = allAuthors.stream().map(Object::toString)
                        .collect(Collectors.joining("; "));
        Publisher pub = PublisherDAO.findPublisherByBook(bookId);
        if (thisBook.getCover_img() != null) {
            Image coverImg = new Image("imgs/" + thisBook.getCover_img());

            imgBookCover.setImage(coverImg);
            imgBookCover.setFitWidth(129);
            imgBookCover.setPreserveRatio(true);
            imgBookCover.setSmooth(true);
            imgBookCover.setCache(true);
        }

        lblBookTitle.setText(thisBook.getTitle());
        lblYear.setText(Integer.toString(thisBook.getYear()));
        lblEdition.setText(Integer.toString(thisBook.getEdition()));
        lblAuthors.setText(authorList);
        lblPublisher.setText(pub.getName() + ", " + pub.getLocation());
        
        tbvLendingDetails.setItems(TransactionDAO.getTransactionsByTitle(bookId));
        // System.out.println("hey!");
        colCopyCode.setCellValueFactory(new PropertyValueFactory<>("copyCode"));
        colLendDate.setCellValueFactory(new PropertyValueFactory<>("lendDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colRenewal.setCellValueFactory(new PropertyValueFactory<>("renewal"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        tbvLendingDetails.getSelectionModel().selectedItemProperty().addListener(
                (tbvLendingDetails, prevTransaction, nextTransaction) -> openLendingCard(nextTransaction)
        );
        
    }
    
    private void openLendingCard(Transaction trans) {
        if (trans != null) {
            this.selectedCopyId = trans.getCopyId();
            if (trans.getAvailability() == 0) {
                btnBorrow.setDisable(false);
            } else if (trans.getAvailability() == 2) {
                btnReserve.setDisable(false);
            } else {
                btnReserve.setDisable(true);
                btnBorrow.setDisable(true);
            }
        } else {
            btnReserve.setDisable(true);
            btnBorrow.setDisable(true);
        }
    }
    
    public void updateLendingTransTable() throws ParseException {
        // tbvLendingDetails.setItems(TransactionDAO.getTransactionsByTitle(this.selectedBookId));
        tbvLendingDetails.refresh();
    }
    
    public TableView getTableView() {
        return tbvLendingDetails;
    }
    
}
