/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lending;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.FXMLBibliothekController;
import model.Author;
import model.AuthorDAO;
import model.Book;
import model.BookDAO;
import model.Collection;
import model.CollectionDAO;
import model.Genre;
import model.GenreDAO;
import model.Publisher;
import model.PublisherDAO;

/**
 * FXML Controller class
 *
 * @author flygm
 */
public class FXMLLendingController implements Initializable {

    @FXML
    private Pane advSearchField;
    @FXML
    private ComboBox<Book> comboBookTitles;
    @FXML
    private ComboBox<Genre> comboBookGenre;
    @FXML
    private ComboBox<Author> comboAuthor;
    @FXML
    private ComboBox<Publisher> comboPublisher;
    @FXML
    private Button btnBookSearch;
    @FXML
    private TableView<Collection> tbvBookSearch;
    @FXML
    private TableColumn<Collection, String> colBookTitle;
    @FXML
    private TableColumn<Collection, String> colAuthors;
    @FXML
    private TableColumn<Collection, String> colCopies;
    @FXML
    private Button btnViewBook;
    @FXML
    private Button btnBack;
    
    private int selectedBookIdx = 0;
    private int selectedGenreIdx = 0;
    private int selectedAuthorIdx = 0;
    private int selectedPublisherIdx = 0;
    
    private String selectedTitle = "";
    private Author selectedAuthor = null;
    private Publisher selectedPub = null;
    private Genre selectedGenre = null;
    
    private int selectedTitleId = 0;
    @FXML
    private TextField txtTitleSearch;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBookTitles.setItems(BookDAO.getBooks());
        comboBookGenre.setItems(GenreDAO.getGenres());
        comboAuthor.setItems(AuthorDAO.getAuthors());
        comboPublisher.setItems(PublisherDAO.getPublishers());
        
        comboBookTitles.getSelectionModel().select(0);
        comboBookGenre.getSelectionModel().select(0);
        comboAuthor.getSelectionModel().select(0);
        comboPublisher.getSelectionModel().select(0);
    }    

    @FXML
    private void searchBook(ActionEvent event) {   
        selectedTitle = txtTitleSearch.getText();
        selectedGenre = comboBookGenre.getSelectionModel().getSelectedItem();
        selectedAuthor = comboAuthor.getSelectionModel().getSelectedItem();
        selectedPub = comboPublisher.getSelectionModel().getSelectedItem();
        
        setTextwrapInTablecell(colBookTitle);
        setTextwrapInTablecell(colAuthors);
        
        tbvBookSearch.setItems(CollectionDAO.fetchSearchResult(
                selectedTitle, selectedGenre.getId(), selectedAuthor.getId(), selectedPub.getId()));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));
        
        tbvBookSearch.getSelectionModel().selectedItemProperty().addListener(
                (tbvBookSearch, oldTitle, newTitle) -> showViewCard(newTitle)
        );
    }

    @FXML
    private void viewBookDetails(ActionEvent event) throws ParseException {
        FXMLBibliothekController openFunction = new FXMLBibliothekController();
        openFunction.openScene("/view_book/FXMLViewBook.fxml", selectedTitleId, null);
    }

    @FXML
    private void backToHomepage(ActionEvent event) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
    
    private void showViewCard(Collection title) {
        btnViewBook.setDisable(false);
        try {
            selectedTitle = title.getTitle();
            selectedTitleId = title.getBookId();
        }
        catch (NullPointerException nullErr) {
            System.out.println(nullErr);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void setTextwrapInTablecell(TableColumn<Collection, String> columnName) {
        columnName.setCellFactory(tc -> {
            TableCell<Collection, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(columnName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
    }
    
}
