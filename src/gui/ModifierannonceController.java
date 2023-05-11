/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.annonce;
import entity.role;
import entity.user;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.AnnonceService;

/**
 * FXML Controller class
 *
 * @author lando
 */
public class ModifierannonceController implements Initializable {

    @FXML
    private TextField texte_image;
    @FXML
    private TextField texte_description;
    @FXML
    private Button texte_button_image;
    @FXML
    private TextField texte_type;
    @FXML
    private Button button_apply;
 private annonce annonceSelectionnee;
    
    public void setAnnonceSelectionnee(annonce annonceSelectionnee) {
        this.annonceSelectionnee = annonceSelectionnee;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void image(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choisir une image");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Fichiers image", ".png", ".JPG", "*.gif"),
        new FileChooser.ExtensionFilter("Tous les fichiers", ".")
    );
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        String imagePath = selectedFile.getAbsolutePath();
        texte_image.setText(imagePath);
    }
        
    
    }

    @FXML
    private void apply(ActionEvent event) {
        role r1 = new role(1,"admin");
        
      //  user u1 = new user(1,"landolsi","aziz",23,"aziz@aziz.com","1234",99319146,"manar2",r1);
        
        String type =texte_type.getText();
        String description =texte_description.getText();
       // String image = texte_button_image.getText();
//        String nomImage =texte_image.getText();
        //int id_user=Integer.parseInt(texte_user.getText());
//         annonce annonceSelectionnee = new annonce(type,description,u1);

       AnnonceService as= new AnnonceService() {} ;
       as.update(annonceSelectionnee);
        
       Stage stage = (Stage) texte_type.getScene().getWindow();
        stage.close();
    }
    
}
