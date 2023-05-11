/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.annonce;
import entity.role;
import entity.user;
//import static gui.affarietFXMain.id_user;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import service.AnnonceService;

/**
 * FXML Controller class
 *
 * @author lando
 */
public class AddannonceController implements Initializable {

    @FXML
    private TextField texte_type;
    @FXML
    private TextField texte_description;
    @FXML
    private TextField texte_image;
    @FXML
    private Button texte_button_image;
    @FXML
    private Button button_apply;
   // int id_annonce=FrontannonceCrontroller.id_annonce;
annonce a =new annonce();
   int id_user = Affariet.userID;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void apply(ActionEvent event) throws IOException {
        
        role r1 = new role(1,"admin");
        
       // user u1 = new user(1,"landolsi","aziz",23,"aziz@aziz.com","1234",99319146,"manar2",r1);
        
        String type =texte_type.getText();
        String description =texte_description.getText();
        String image = texte_button_image.getText();
        String nomImage =texte_image.getText();
        //int id_user=Integer.parseInt(texte_user.getText());
         File selectedFile1 = new File(nomImage);
         annonce a = new annonce(type,description,selectedFile1.getName(),id_user);
         
         
        
         
         
       
       
       
         String destinationPath = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\images\\";

// Récupérer le fichier sélectionné
       File selectedFile = new File(texte_image.getText());

// Créer un nouveau fichier dans le dossier de destination avec le même nom que le fichier sélectionné
        File destinationFile = new File(destinationPath + selectedFile.getName());

// Copier le fichier sélectionné dans le dossier de destination
        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

// Définir le nom de l'image dans l'utilisateur
        a.setImage(selectedFile.getName());
    }

    @FXML
    private void image(ActionEvent event) throws IOException {
        
        FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choisir une image");
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif", "*.PNG"),
        new ExtensionFilter("Tous les fichiers", "*.*")
    );
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        String imagePath = selectedFile.getAbsolutePath();
        texte_image.setText(imagePath);
        
      
    }
        
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }
    
}
