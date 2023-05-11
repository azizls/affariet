/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import entity.user;
import entity.reclamation;
import java.io.File;
import service.ReclamationService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
//import javafx.stage.FileChooser;

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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.UserService;
import gui.Affariet;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class ReclamationController implements Initializable {

    @FXML
    private AnchorPane txt1;
    @FXML
    private RadioButton radio_button1;
    @FXML
    private RadioButton radio_button2;
    @FXML
    private RadioButton radiobutton3;
    @FXML
    private Label myLabel;
    @FXML
    private TextField reclamtion_message;
    @FXML
    private Button btnreclamation;
    @FXML
    private ToggleGroup typeReclamationToggleGroup;
    @FXML
    private TextField id_id;
    @FXML
    private Button id_mes;
    @FXML
    private Button btnAjouterImage;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView Image_user;
  


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    
    }    

    @FXML
private void addreclamation(ActionEvent event) throws IOException {
    String description = reclamtion_message.getText();

    if (description.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("La description est vide");
        alert.showAndWait();
        return;
    }

    RadioButton selectedRadioButton = (RadioButton) typeReclamationToggleGroup.getSelectedToggle();
    String typeReclamation = selectedRadioButton.getText();
    UserService us= new UserService ();
    // Get the logged-in user
    user loggedInUser = new user();
loggedInUser = us.readById(Affariet.userID);
    // Create a new reclamation object with the logged-in user
    reclamation rec = new reclamation(description, typeReclamation, loggedInUser);
  FileChooser fileChooser = new FileChooser(); 
    // Get the selected image file path
    String imagePath = "";
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        imagePath = selectedFile.getAbsolutePath();
    }
    
    // Set the image path in the reclamation object
    rec.setImagePath(imagePath);

    // Insert the reclamation into the database using the ReclamationService
    ReclamationService rs = new ReclamationService() {} ;
    rs.insert(rec);

    reclamtion_message.clear();
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Reclamation ajoutée avec succès");
    alert.showAndWait();
}

   @FXML
private void mes_reclamations(ActionEvent event) throws IOException {
    // Retrieve the logged-in user
    UserService us= new UserService ();
    user loggedInUser = new user();
    loggedInUser = us.readById(Affariet.userID);

    // Retrieve all reclamations made by the logged-in user from the database using the ReclamationService
    ReclamationService rs = new ReclamationService() {};
    List<reclamation> reclamations = rs.getByUser(loggedInUser);

    // Create a new FXML loader for the Mesreclamation view
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/mesreclamation.fxml"));
    Parent root = loader.load();
    MesreclamationController controller = loader.getController();

    // Set the reclamations to display in the TableView
    /*controller.setReclamations(reclamations);*/

    // Show the Mesreclamation view
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}

    @FXML
    private void ajouterImage(ActionEvent event) {
      FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Sélectionner une image");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
    );
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        // Enregistrer le chemin de l'image dans la base de données
        ReclamationService rs = new ReclamationService() {};
        reclamation rec = new reclamation();
        rec.setImagePath(file.getAbsolutePath()); // Le chemin absolu de l'image
        
     
        
        rs.updateImagePath(rec);
    }    
        
        
    }

   @FXML
    private void evenement(ActionEvent event) throws IOException {
        
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("FrontEv.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }

    @FXML
    private void blog(ActionEvent event) throws IOException {
          Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("frontannonce.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void monprofil(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("Frontmodifier.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void reclamation(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("reclamation.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void produit(ActionEvent event) throws IOException {
        
          Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("FrontProduit.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }

    @FXML
    private void commande(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("mesCommandeTroc.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }
    }

      
         
    
   

