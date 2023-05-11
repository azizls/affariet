/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.reclamation;
import entity.user;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.ReclamationService;
import service.UserService;
import javafx.scene.layout.HBox;

//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MALEK
 */
public class MesreclamationController implements Initializable {

    @FXML
    private TableColumn<reclamation, Date> id_date;

    @FXML
    private TableColumn<reclamation, String> id_decription;

    @FXML
    private TableColumn<reclamation, Integer> id_id;

    @FXML
    private TableView<reclamation> id_list;

    @FXML
    private TableColumn<reclamation, String> id_type;
    @FXML
    private TableColumn<reclamation, String> id_etat;
    @FXML
    private TextField userIdField;
    @FXML
    private Button id_search;
    /**
     * Initializes the controller class.
     */

    private ReclamationService rs = new ReclamationService() {
    };
    @FXML
    private TableColumn<reclamation, user> id_u;
    @FXML
    private TableColumn<reclamation, Integer> id_avis;
    @FXML
    private ImageView Image_user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        id_date.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        id_decription.setCellValueFactory(new PropertyValueFactory<>("description"));
        id_type.setCellValueFactory(new PropertyValueFactory<>("type_reclamation"));
        id_etat.setCellValueFactory(new PropertyValueFactory<>("etat_reclamation"));
        //id_u.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_user().getId()));
          id_avis.setCellValueFactory(new PropertyValueFactory<>("avis"));
    id_avis.setCellFactory(col -> new TableCell<reclamation, Integer>() {
    private final StarRating starRating = new StarRating(5);

    {
        starRating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            reclamation rec = (reclamation) getTableRow().getItem();
            if (rec != null) {
                rec.setAvis(newValue.intValue());
                rs.update(rec);
            }
        });
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            starRating.ratingProperty().set(item);
            setGraphic(starRating);
        }
    }
});
        
        
        
        
         // Get the logged-in user
        UserService userService = new UserService();
        user loggedInUser = userService.readById(Affariet.userID);

        // Get all the reclamations for the logged-in user
       ReclamationService rs = new ReclamationService() {};
List<reclamation> reclamations = rs.getByUser(loggedInUser);

        ObservableList<reclamation> reclamationList = FXCollections.observableArrayList(reclamations);

        id_list.setItems(reclamationList);
    }

    @FXML
    private void SelectedItems(MouseEvent event) {
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent event) {

        String userId = userIdField.getText();
        if (userId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer un ID d'utilisateur valide");
            alert.showAndWait();
            return;
        }

        // Récupérez les réclamations pour l'utilisateur spécifié
        List<reclamation> userReclamations = rs.getAllReclamationsForUser(userId);
        ObservableList<reclamation> reclamationsData = FXCollections.observableArrayList(userReclamations);
        id_list.setItems(reclamationsData);
    }

    void setReclamations(List<reclamation> reclamations) {
   ObservableList<reclamation> reclamationList = FXCollections.observableArrayList(reclamations);
        id_list.setItems(reclamationList);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("reclamation.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    

}
