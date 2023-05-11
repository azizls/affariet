/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.reservation;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import service.evenementService;
import service.reservationService;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class mesReservations implements Initializable {

    @FXML
    private HBox itemRes;
    @FXML
    private Label libelle;
    @FXML
    private Label label_prix;
    @FXML
    private ImageView image;
    @FXML
    private Label dateRes;
    
    evenementService srv = new evenementService();
    reservationService resS = new reservationService();
    private reservation r = resS.readById(FrontResController.id_res_static);
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image img =Retournerimage(r.getId_event().getId_event());
        
        image.setImage(img);

        libelle.setText(r.getId_event().getNom_event());
//        libelle.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        
        LocalDate localDf = r.getDate_res();
        String pattern ="MMMM ,dd , yyyy" ; 
        String datePattern = localDf.format(DateTimeFormatter.ofPattern(pattern));
        dateRes.setText(datePattern);
        
        

        label_prix.setText(String.valueOf(r.getId_event().getPrix()));
//        label_prix.setStyle("-fx-font-size: 16px;");    
    }



   private Image Retournerimage(int i) {
    // Chemin du dossier contenant les images
    String dossierImages = "C:\\xampp\\htdocs\\evenement\\";
  
    // Convertir l'entier i en une chaîne de caractères
    String id_event_str = String.valueOf(i);
    
    // Créer un objet File pour représenter le dossier
    File dossier = new File(dossierImages);       

    // Récupérer la liste des fichiers dans le dossier
    File[] fichiers = dossier.listFiles();
    
    // Parcourir la liste des fichiers
    for (File fichier : fichiers) {
        // Récupérer le nom du fichier
        String nomFichier = fichier.getName();
         
        // Vérifier si le nom de fichier commence par id_event_str et se termine par .jpg, .png ou .PNG
        if (nomFichier.startsWith(id_event_str) && (nomFichier.endsWith(".jpg") || nomFichier.endsWith(".png") || nomFichier.endsWith(".PNG"))) {
            // Si le fichier correspond, convertir le chemin du fichier en une URL d'image et créer un objet Image à partir de l'URL
            String cheminImage = fichier.toURI().toString();
            Image img = new Image(cheminImage);
            
            // Retourner l'image trouvée
            return img;
        }
    }
    
    // Si aucun fichier ne correspond, retourner null
    return null;
}

    @FXML
    private void delete(ActionEvent event) {
        
                resS.delete(r);
            showErrorMesssage("Reservation deleted", "Reservation deleted");
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
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    alert.showAndWait();
}
}





   
     
    

