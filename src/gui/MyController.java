/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.evenement;
import entity.user;
import static gui.ModevController.ID;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.evenementService;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class MyController implements Initializable {

    @FXML
    private TextField nomEventField;
    @FXML
    private TextField emplacementField;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private DatePicker dateFinPicker;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField prixField;
    @FXML
    private Button modifierButton;
    @FXML
    private TextField nbr_maxField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void modifierEvenement(ActionEvent event) {
    String nomEvent = nomEventField.getText();
    String emplacement = emplacementField.getText();
    LocalDate dateDebut = dateDebutPicker.getValue();
    LocalDate dateFin = dateFinPicker.getValue();
    String description = descriptionArea.getText();
    int prix = Integer.parseInt(prixField.getText());
    int nbr_max = Integer.parseInt(nbr_maxField.getText());

    // Vérifier que les champs obligatoires ne sont pas vides
    if (nomEvent.isEmpty() || emplacement.isEmpty() || dateDebut == null || dateFin == null ) {
        showErrorMesssage("Erreur", "Veuillez remplir tous les champs obligatoires !");
        return;
    }
    else {
// Récupérer l'événement sélectionné dans la liste
            evenementService evss = new evenementService();
    evenement evenement = evss.readById(ModevController.ID);
            System.out.println(ID);

    // Modifier les attributs de l'événement
    evenement.setNom_event(nomEvent);
    evenement.setEmplacement(emplacement);
    evenement.setDate_debut(dateDebut);
    evenement.setDate_fin(dateFin);
    evenement.setDescription_event(description);
    evenement.setPrix(prix);
    evenement.setNbr_max(nbr_max);

    evenement.setId_event(ModevController.ID);
    evss.update(evenement);

    // Afficher un message de confirmation
        showConfirmationMessage("Modification réussie", "L'événement a été modifié avec succès !");
    }
    // Vider les champs de texte
    nomEventField.clear();
    emplacementField.clear();
    dateDebutPicker.setValue(null);
    dateFinPicker.setValue(null);
    descriptionArea.clear();
    prixField.clear();
    
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
        Parent root = FXMLLoader.load(getClass().getResource("suppev.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }
}
