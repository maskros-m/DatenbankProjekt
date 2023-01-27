/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returning;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import model.Book;
import model.BookDAO;
import model.DatePickerCell;
import model.Kunden;
import model.KundenDAO;
import model.LibraryDateTime;
import model.Transaction;
import model.TransactionDAO;
import util.DAO;

/**
 * FXML Controller class
 *
 * @author mido
 */
public class FXMLReturnController implements Initializable {

    @FXML
    private Button renewBtn;
    @FXML
    private Button returnBtn;
    @FXML
    private Button payFineBtn;
    @FXML
    private TextField txtBookTitle;
    @FXML
    private TextField txtLastname;
    @FXML
    private ComboBox<Kunden> comboPatronList;
    @FXML
    private Button lookupBtn;
    @FXML
    private TableView<Transaction> tbvLendingHistory;
    @FXML
    private TableColumn<Transaction, String> colPatron;
    @FXML
    private TableColumn<Transaction, String> colBookTitle;
    @FXML
    private TableColumn<Transaction, String> colReturnDate;
    @FXML
    private TableColumn<Transaction, String> colRenewal;
    @FXML
    private TableColumn<Transaction, String> colFine;
    @FXML
    private TableColumn<Transaction, LocalDate> colRetDateEditable;
    
    private Transaction selectedTrans;
    private String lastSearchStr;
    private Kunden lastSearchP;
    
    private TransactionDAO tdao = new TransactionDAO();
    private LibraryDateTime libDT = new LibraryDateTime();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tbvLendingHistory.setEditable(true);
        Callback<TableColumn<Transaction, LocalDate>, TableCell<Transaction, LocalDate>> dateCellFactory
                = (TableColumn<Transaction, LocalDate> param) -> new DatePickerCell();
        // TODO: Move styling to css sheet
        colRetDateEditable.setStyle( "-fx-alignment: CENTER;");
        colFine.setStyle("-fx-alignment: CENTER;");
        colReturnDate.setStyle("-fx-alignment: CENTER;");
        colPatron.setStyle("-fx-alignment: CENTER;");
        colRenewal.setStyle("-fx-alignment: CENTER;");
        colRetDateEditable.setCellFactory(dateCellFactory);
        colRetDateEditable.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Transaction, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Transaction, LocalDate> table) {
                int row = table.getTablePosition().getRow(); // Zeilennummer in der Tabelle
                Transaction trans = table.getTableView().getItems().get(row); // Objekt in der Tabelle
                String oldReturnDate = trans.getReturnDate(); // may not need this one
                String newReturnDate = table.getNewValue().toString();
                // only persists change in programme if there's no penalty
                // and renewal is 0/3 or 1/3
                if (trans.getRenewalValue() <= 2 && trans.getFineValue() == 0.0) {
                   
                    if (libDT.calculateDuration(oldReturnDate, newReturnDate) > 0) {
                        // trans.setNew_lendDate(trans.getReturnDate());
                        // trans.setRetDate(newReturnDate);
                        trans.setLendDate(LocalDate.now().toString());
                        trans.setReturnDate(newReturnDate);
                        trans.setDirty(true); // merkt sich die Änderungen im Programm aber speichert nicht in DB
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid date");
                        alert.setContentText("Next return date must be"
                                + " at least 1 day after current return date.");

                        alert.showAndWait();
                    }
                }
            }
        });
        
        tbvLendingHistory.getSelectionModel().selectedItemProperty().addListener(
            (tbvs, prevTrans, nextTrans) -> enableRelevantBtn(nextTrans)
        );
    }    

    @FXML
    private void renewBook(ActionEvent event) throws ParseException {
        if (selectedTrans != null && selectedTrans.isDirty()) {

            String newLendDate = LocalDate.now().toString();
            String newRetDate = selectedTrans.getReturnDate().toString();
            int currentRenewal = selectedTrans.getRenewalValue();
            tdao.renewTransaction(newLendDate, newRetDate, currentRenewal, selectedTrans.getId());

        } 
        else if (!selectedTrans.isDirty()) {
            automaticRenew(selectedTrans);
        }
        tbvLendingHistory.setItems(tdao.getBorrowingHistory(lastSearchStr, lastSearchP));
    }

    @FXML
    private void returnBook(ActionEvent event) throws ParseException {
        if (selectedTrans != null) {
            tdao.deleteTransaction(selectedTrans.getId());
            tbvLendingHistory.setItems(tdao.getBorrowingHistory(lastSearchStr, lastSearchP));
        }
    }

    @FXML
    private void payFine(ActionEvent event) throws ParseException {
        if (selectedTrans != null) {
            LibraryDateTime ldt = new LibraryDateTime();
            if (selectedTrans.getRenewalValue() >= 3 && selectedTrans.getFineValue() > 0) {
                // pay fine & return book (or delete transaction)
                Book book = BookDAO.fetchBookByCopy(selectedTrans.getCopyId());
                float totalFine = selectedTrans.getFineValue() + book.getPrice();
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Zahlung bestätigen");
                alert.setHeaderText(null);
                alert.setContentText("Die Abgabefrist für dieses Buch ist schon abgelaufen."
                        + "\nBitte bringen Sie ein neues Exemplar von dem Buch,"
                        + "\noder bezahlen Sie den aktuellen Preis für das Buch."
                        + "\nSie können dann wieder neue Bücher ausleihen, wenn"
                        + "\nSie die Mahnugsgebühr bezahlt haben."
                        + "\nMahngebühr: € " + selectedTrans.getFineValue()
                        + "\nPreis vom Buch: € " + book.getPrice()
                        + "\n=============================="
                        + "\n\nZu zahlen: € " + totalFine);
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Zahlen & Buch zurückgeben");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    tdao.deleteTransaction(selectedTrans.getId());
                    tbvLendingHistory.setItems(tdao.getBorrowingHistory(lastSearchStr, lastSearchP));
                }
                /*
                 This book is already overdue
                 Please provide a new replacement for this book
                 or pay for it at its current market price
                 You can only continue to borrow book after all
                 Mahnungsgebühr(en)? have been paid in full
                */
            } else {
                // TODO: pay fine (reset fine to 0) & update new lend date
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mahngebühr zahlen");
                alert.setHeaderText(null);
                alert.setContentText("Zu zahlen: € " + selectedTrans.getFine());
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK))
                        .setText("Zahlen und zurückgeben");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL))
                        .setText("Zahlen und erneuern");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // Pay fine and return book
                    tdao.deleteTransaction(selectedTrans.getId());
                    tbvLendingHistory.setItems(
                            tdao.getBorrowingHistory(lastSearchStr, lastSearchP));
                } else if (result.get() == ButtonType.CANCEL) {
                    // Pay fine and automatically renew

                    automaticRenew(selectedTrans);
                    tbvLendingHistory.setItems(
                            tdao.getBorrowingHistory(lastSearchStr, lastSearchP));
                }
            }
        }
    }

    @FXML
    private void searchTransactions(ActionEvent event) throws ParseException {
        String bookString = this.lastSearchStr = txtBookTitle.getText();
        Kunden p = this.lastSearchP = comboPatronList.getSelectionModel().getSelectedItem();
        // System.out.println(p.getPatronId());
        
        tbvLendingHistory.setItems(tdao.getBorrowingHistory(bookString, p));
        colPatron.setCellValueFactory(new PropertyValueFactory<>("patronLName"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colRenewal.setCellValueFactory(new PropertyValueFactory<>("currRenewal"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));
        
        colRetDateEditable.setCellValueFactory(new PropertyValueFactory<>("retDate"));
        
    }

    @FXML
    private void getTypedText(KeyEvent event) {
        // System.out.print(txtLastname.getText());
        if (event.getCode() == KeyCode.ENTER) {
            String searchString = txtLastname.getText();
            comboPatronList.setDisable(false);
            comboPatronList.setItems(KundenDAO.getPatronsByName(searchString));
        }
    }

    private void enableRelevantBtn(Transaction trans) {
        this.selectedTrans = trans;
        if (trans != null) {
            if (trans.getRenewalValue() > 3 || trans.getFineValue() != 0.0 
                    || (trans.getRenewalValue() >= 3 && trans.getFineValue() != 0.0)) {
                toggleDisable(true, true, false);
            } else if (trans.getFineValue() == 0.0 && trans.getRenewalValue() <= 2) {
                toggleDisable(false, false, true);
            }
            if (trans.getFineValue() == 0 && trans.getRenewalValue() == 3) {
                toggleDisable(true, false, true);
            }
        }
    }
    
    private void toggleDisable(Boolean value1, Boolean value2, Boolean value3) {
        renewBtn.setDisable(value1);
        returnBtn.setDisable(value2);
        payFineBtn.setDisable(value3);
    }
    
    private void automaticRenew(Transaction trans) {
        if (trans != null) {
            String newLendDate = LocalDate.now().toString();
            int duration = DAO.getDuration();
            LocalDate newRetDate = libDT.datePlusDays(LocalDate.now(), duration);
            int currentRenewal = selectedTrans.getRenewalValue();
            tdao.renewTransaction(newLendDate, newRetDate.toString(), 
                                currentRenewal, selectedTrans.getId());
        } else {
            System.out.println("FXMLReturnCtrl.automaticRenew: no trans selected.");
        }
    }
    
}
