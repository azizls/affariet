/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;
import gui.Affariet;
import entity.evenement;
import entity.user;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import service.UserService;
import service.evenementService;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class AddevController implements Initializable {

    @FXML
    private ImageView im2;
    @FXML
    private TextField txt_nom_event;
    @FXML
    private TextField txt_emp;
    @FXML
    private TextField txt_prix;
    @FXML
    private TextField txt_des;
    @FXML
    private Button btn_ajouter_ev;
    @FXML
    private Label lbl_Ddebut;
    @FXML
    private Label lbl_Dfin;
    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private Button btn_modifier_ev;
    @FXML
    private Button btn_supprimer_ev;
    @FXML
    private Button button_im;
    @FXML
    private TextField ImageView;
    @FXML
    private TextField txt_nbrMax;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    UserService us =new UserService();
    user CurrentUser = us.readById(Affariet.userID);
    @FXML
    private ImageView Image_user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        

                System.out.println(Affariet.userID);
       
        }

//    @FXML
//    private void sauvegarder(ActionEvent event) {
//        
//    }
//
//    @FXML
//    private void image(ActionEvent event) {
//    }

    @FXML
    private void date_debut(ActionEvent event) {
        LocalDate localDD = date_debut.getValue();
        String pattern ="MMMM ,dd , yyyy" ; 
        String datePattern = localDD.format(DateTimeFormatter.ofPattern(pattern));
        lbl_Ddebut.setText("Date debut : "+datePattern);
        
    }

    @FXML
    private void date_fin(ActionEvent event) {
        LocalDate localDf = date_fin.getValue();
        String pattern ="MMMM ,dd , yyyy" ; 
        String datePattern = localDf.format(DateTimeFormatter.ofPattern(pattern));
        lbl_Dfin.setText("Date fin : "+datePattern);
    }

    @FXML
    private void sauvegarder(ActionEvent event) throws IOException, SQLException {
      user u1 = us.readById(21);

       String nom_event = txt_nom_event.getText();
        String emplacement = txt_emp.getText();
        LocalDate date_d = this.date_debut.getValue();
        LocalDate date_f = this.date_fin.getValue();
        String description = txt_des.getText();
        int nbr_max = Integer.parseInt( txt_nbrMax.getText());
        String image =ImageView.getText();
        File selectedFile1 = new File(image);
        
        
        
        int prix = Integer.parseInt(txt_prix.getText());
//        String affiche = btn_image.getText();
//        String afficheim =txt_image.getText
        // Créer un nouvel evenement 
        evenement e = new evenement(prix, nom_event, emplacement, date_d, date_f, description, prix, selectedFile1.getName(), nbr_max, u1);
        // Utiliser le service eventService pour ajouter l'utilisateur à la base de données
         evenementService evSer = new evenementService();
   

        
        
        int idev=evSer.insert1(e);

        
             
         // Récupérer le chemin du dossier de destination
        String destinationPath = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\event_images\\";

// Récupérer le fichier sélectionné
        File selectedFile = new File(image);

// Créer un nouveau fichier dans le dossier de destination avec le même nom que le fichier sélectionné
        File destinationFile = new File(destinationPath + selectedFile.getName());

// Copier le fichier sélectionné dans le dossier de destination
        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

// Définir le nom de l'image dans l'utilisateur
        e.setAffiche(selectedFile.getName());
        
        
        // Afficher un message de confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Evenement ajouté avec succès !");
        alert.showAndWait();
             
        
 
        
        
        

        // Réinitialiser le formulaire
        txt_des.setText("");
        txt_emp.setText("");
//        txt_image.setText("");
        txt_nom_event.setText("");
        txt_prix.setText("");
         
    }

//    @FXML
//    private void modifier(ActionEvent event) {
//    }
//
//    @FXML
//    private void supprimer(ActionEvent event) {
//    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("suppev.fxml"));
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }

    @FXML
    private void supprimer(ActionEvent event) throws IOException {
        
       Parent root = FXMLLoader.load(getClass().getResource("suppev.fxml"));
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    
    }

    @FXML
    private void browse(ActionEvent event) {
      
         FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choisir une image");
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.PNG", "*.gif"),
        new ExtensionFilter("Tous les fichiers", "*.*")
    );
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        String imagePath = selectedFile.getAbsolutePath();
        
        ImageView.setText(imagePath);
    }
  
        
        
        
}

     @FXML
    private void user_back(ActionEvent event) throws IOException {
     
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void produit_back(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("Produit.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
        
    }
    

    @FXML
    private void commande_back(ActionEvent event) throws IOException {
        
          Parent root = FXMLLoader.load(getClass().getResource("listedeTrocPropose.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void evenement_back(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("addev.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void blog_back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void reclamation_back(ActionEvent event) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("back.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }


}
