/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lending.FXMLLendingController;
import lendingDetails.FXMLLendingDetailsController;
import view_book.FXMLViewBookController;

/**
 *
 * @author mido
 */
public class FXMLBibliothekController implements Initializable {
    
    @FXML
    private Button borrowBtn;
    @FXML
    private Button returnBtn;
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openBorrowPane(ActionEvent event) throws ParseException {
        openScene("/lending/FXMLLending.fxml", 0, null);
    }

    @FXML
    private void openReturnPane(ActionEvent event) throws ParseException {
        openScene("/returning/FXMLReturn.fxml", 0, null);
    }
    
    public void openScene(String fxmlSource, int targetId, TableView t) throws ParseException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlSource));
            Parent root = loader.load();
            
            if (loader.getController() instanceof FXMLViewBookController) {
                ((FXMLViewBookController)loader.getController()).loadBookDetails(targetId);
            }
            else if (loader.getController() instanceof FXMLLendingDetailsController) {
                ((FXMLLendingDetailsController)loader.getController()).loadBookTitle(targetId);
                ((FXMLLendingDetailsController)loader.getController()).loadTableView(t);
            }
            
            Stage movieStage = new Stage();
            movieStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            movieStage.setScene(scene);
            movieStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
