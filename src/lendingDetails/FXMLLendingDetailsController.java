/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingDetails;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import model.Book;
import model.BookDAO;
import model.Kunden;
import model.KundenDAO;
import model.LibraryDateTime;
import model.Transaction;
import model.TransactionDAO;
import util.DAO;
import view_book.FXMLViewBookController;

/**
 * FXML Controller class
 *
 * @author mido
 */
public class FXMLLendingDetailsController implements Initializable {

    @FXML
    private Label lblBookName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private DatePicker dateReturnDate;
    @FXML
    private Button btnRequestLend;
    @FXML
    private Button btnCancelLend;
    @FXML
    private DatePicker dateLendDate;
    @FXML
    private ComboBox<String> comboLastname;
    @FXML
    private ComboBox<Kunden> comboPatronInfo;
    @FXML
    private Button btnRequestLend1;
    
    // private KundenDAO kdao = new KundenDAO();
    
    private int selectedCopyId;
    private int selectedBookId;
    private int lendingDuration;
    
    private TableView<Transaction> tbv;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set default value in both datepickers to today and next return date
        dateLendDate.setValue(LocalDate.now());

        lendingDuration = DAO.getDuration();
        LibraryDateTime ldt = new LibraryDateTime();
        dateReturnDate.setValue(ldt.datePlusDays(LocalDate.now(), lendingDuration));
        
        // init comboLastname with a list of patrons' last names
        comboLastname.setItems(KundenDAO.getPatronsByLastName());
        comboLastname.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // If the condition is not met and the new value is not null: "rollback"
                if(newValue != null){
                    togglePatronInfoList();
                    String searchString = newValue;
                    comboPatronInfo.setItems(KundenDAO.getPatrons(newValue));
                }
            }
        });
        
        
    }
    
    @FXML
    private void createLendingTransaction(ActionEvent event) throws ParseException {
        try {
            Kunden selectedPatron = comboPatronInfo.getSelectionModel().getSelectedItem();
            String borrowDate = dateLendDate.getValue().toString();
            String returnDate = dateReturnDate.getValue().toString();

            TransactionDAO.createNewLendingTransaction(selectedCopyId, selectedPatron, borrowDate, returnDate);
            Book b = BookDAO.fetchBookByCopy(selectedCopyId);
            selectedBookId = b.getId();
            // System.out.println("selectedBookId=" + selectedBookId);
            tbv.setItems(TransactionDAO.getTransactionsByTitle(selectedBookId));
            closeCurrentWindow(btnRequestLend1);

        } catch (NullPointerException nullE) {
            nullE.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select at least a patron.");

            alert.showAndWait();
        }
    }

    @FXML
    private void closeLendingCard(ActionEvent event) {   
        closeCurrentWindow(btnCancelLend);
    }
    
    @FXML
    private void registerAndBorrow(ActionEvent event) throws ParseException {
        // register new patron
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        
        String borrowDate = dateLendDate.getValue().toString();
        String returnDate = dateReturnDate.getValue().toString();

        KundenDAO kdao = new KundenDAO();
        try {
            kdao.registerNewPatron(firstName, lastName, email, phone);
            
            // register new lending transaction
            Kunden newPatron = kdao.findPatron(firstName, lastName, email, phone);
            System.out.println(newPatron);
            TransactionDAO.createNewLendingTransaction(selectedCopyId, newPatron, borrowDate, returnDate);
            Book b = BookDAO.fetchBookByCopy(selectedCopyId);
            selectedBookId = b.getId();
            tbv.setItems(TransactionDAO.getTransactionsByTitle(selectedBookId));
            closeCurrentWindow(btnRequestLend);
        } catch (NullPointerException nullE) {
            System.out.println("FXMLLendingDetailsCtrl: NullPointerError");
            nullE.printStackTrace();
        }
        
    }
    
    public void loadBookTitle(int copyId) {
        this.selectedCopyId = copyId;
        Book thisBook = BookDAO.fetchBookByCopy(copyId);
        
        lblBookName.setText(thisBook.getTitle());
    }

    private void togglePatronInfoList() {
        if (comboPatronInfo.isDisabled()) {
            comboPatronInfo.setDisable(false);
        }
    }
    
    private void closeCurrentWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    
    public void loadTableView(TableView t) {
        this.tbv = t;
        System.out.println("LendingDetailsCtrl.loadTableView: " + t==null);
    }

}
