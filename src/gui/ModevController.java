/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;
 
import entity.evenement;
import entity.user;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.UserService;
import service.evenementService;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class ModevController implements Initializable {

    @FXML
    private TableView<evenement> Afficher;
    @FXML
    private TableColumn<?, ?> nom_col;
    @FXML
    private TableColumn<?, ?> emp_col;
    @FXML
    private TableColumn<?, ?> dd_col;
    @FXML
    private TableColumn<?, ?> df_col;
    @FXML
    private TableColumn<?, ?> des_col;
    @FXML
    private TableColumn<?, ?> prix_col;
    
    private evenementService evSer = new evenementService();
    @FXML
    private TextField text_recherche;
    @FXML
    private TextField text_recherche2;
    
    static int ID;
    @FXML
    private Button btn_mod;
UserService us =new UserService();
    user CurrentUser = us.readById(Affariet.userID);
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
        prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));
        des_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        emp_col.setCellValueFactory(new PropertyValueFactory<>("emplacement"));
        dd_col.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        df_col.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        
        // récupère les données des evenements depuis la base de données
        List<evenement> evList = evSer.readAll();
        
        // affiche les données dans le tableau
            Afficher.getItems().setAll(evList);
            
    }    

    @FXML
    private void Suppression(KeyEvent event) {
        
        evenement selectedevent = Afficher.getSelectionModel().getSelectedItem();
//       if (selectedevent == null) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("No user selected");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select a user in the table.");
//            alert.showAndWait();
//            return;
//        }
    if (event.getCode()==KeyCode.X && selectedevent.getId_user().getId_user()==CurrentUser.getId_user()){
        boolean result = showConfirmationMessage("Confirm deletion", "Are you sure you want to delete the selected event?");
        if (result ) {
        
            evSer.delete1(selectedevent.getId_event());
        List<evenement> eventList = evSer.readAll();
        
        // affiche les données dans le tableau
        Afficher.refresh();
        Afficher.getItems().setAll(eventList);
        Afficher.refresh();
        }}
}

    @FXML
    private void rechercher1(KeyEvent event2) {
        
        if (event2.getCode() == KeyCode.ENTER ) {
        int EventId = Integer.parseInt(text_recherche.getText());
        evenement searchedEv = evSer.readById(EventId);
        if (searchedEv != null) {
            Afficher.refresh();
            Afficher.getItems().setAll(searchedEv);
            Afficher.refresh();
        }else  {
            showErrorMesssage("No event found", "No event found with the given id");
        }
    }

    }

    @FXML
    private void rechercher2(KeyEvent event3) {
        
        if (event3.getCode() == KeyCode.ENTER) {
            String EventName = text_recherche2.getText();
            evenement searchedEv = evSer.readByNom(EventName);
            if (searchedEv != null) {
                Afficher.refresh();
                Afficher.getItems().setAll(searchedEv);
                Afficher.refresh();
        }
            else  {
                
                showErrorMesssage("No event found", "No event found with the given Name");    
        }
    }
        
    }

    @FXML
    private void Modifier(ActionEvent event) throws IOException {
     //   user CurrentUser = us.readById(Affariet.userID);
        int id_user = CurrentUser.getId_user();
        evenement selectedevent = Afficher.getSelectionModel().getSelectedItem();
        
        if (selectedevent != null && selectedevent.getId_user().getId_user() == id_user)
        
        {
        Parent root = FXMLLoader.load(getClass().getResource("modevv.fxml"));
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
        int currentEvId = selectedevent.getId_event();
        ID =currentEvId;
    }
        else {
            
            showErrorMesssage("No event selected" , "veuillez selectionner un evenement");

            
        }

    }

    public boolean showConfirmationMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    // afficher la boîte de dialogue et attendre que l'utilisateur fasse un choix
    ButtonType buttonType = alert.showAndWait().orElse(ButtonType.CANCEL);

    // retourner true si l'utilisateur a cliqué sur "OK", false sinon
    return buttonType == ButtonType.OK;
}

public void showErrorMesssage(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    alert.showAndWait();
}

    @FXML
    private void BackO(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addev.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }
}
