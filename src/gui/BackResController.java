/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.evenement;
import entity.reservation;
import entity.user;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.UserService;
import service.reservationService;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class BackResController implements Initializable {

    @FXML
    private TableView<reservation> tableView;
    @FXML
    private TableColumn<reservation, Integer> idColumn;
    @FXML
    private TableColumn<reservation, String> nomColumn;
    @FXML
    private TableColumn<reservation, Date> dateColumn;
    @FXML
    private TableColumn<reservation, String> clientColumn;
    @FXML
    private TextField txt_recherche;
            reservationService rs = new reservationService();
            private ObservableList<reservation> res;
UserService us =new UserService();
    user CurrentUser = us.readById(Affariet.userID);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id_res"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_res"));
    nomColumn.setCellValueFactory(cellData -> {
        reservation ress = cellData.getValue();
        StringProperty nomEvProperty = new SimpleStringProperty(ress.getId_event().getNom_event());
      return nomEvProperty;
});
     clientColumn.setCellValueFactory(cellData -> {
        reservation ress = cellData.getValue();
        StringProperty clProperty = new SimpleStringProperty(ress.getId_user1().getNom());
      return clProperty;
});
     charge();
     }
     
        
       
        
    

       private void charge(){
        
         List<reservation> listRes = rs.readAll();
        res = FXCollections.observableArrayList(listRes);
        tableView.setItems(res);
        System.out.println(listRes);
    }

    @FXML
    private void rechercher(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String userName = txt_recherche.getText();
            
            reservation searchedRes = rs.readByNomUuser(userName);
            if (searchedRes != null) {
                tableView.refresh();
                tableView.getItems().setAll(searchedRes);
                tableView.refresh();
        }
            else  {
                
                showErrorMesssage("No event found", "No event found with the given Name");    
        }
    }
        
    }
public void showErrorMesssage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    alert.showAndWait();
}
    }
    

