/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.annonce;
import entity.commentaire;
import entity.role;
import entity.user;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.AnnonceService;
import service.CommentaireService;

/**
 * FXML Controller class
 *
 * @author lando
 */
public class AddcommentaireController implements Initializable {

    @FXML
    private Button button_submit;
    @FXML
    private TextField texte_comment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    int id_annonce = FrontannonceCrontroller.id_annonce;
    int id_user1 = Affariet.userID;
    String email =Affariet.email;
    private annonce annonceSelectionnee;
    
    public void setAnnonceSelectionnee(annonce annonceSelectionnee) {
        this.annonceSelectionnee = annonceSelectionnee;
    }
    
   public void setIdAnnonce(int id_annonce) {
        this.id_annonce = id_annonce;
    }


    @FXML
    private void ajoutercomment(ActionEvent event) {
        String comment = texte_comment.getText();
       
        
        // Créer le commentaire avec l'annonce sélectionnée et le texte du commentaire
        commentaire c = new commentaire(comment, annonceSelectionnee, id_user1,email);
        
        
        CommentaireService cs = new CommentaireService();
        cs.insert(c,annonceSelectionnee.getId_annonce(),Affariet.userID ,Affariet.email);
        
      
        
        // Fermer la fenêtre
        Stage stage = (Stage) texte_comment.getScene().getWindow();
        stage.close();
        
    }
    
    public void setAnnonceId(int id_annonce) {
    AnnonceService as = new AnnonceService();
    annonce a = as.get_annonce(id_annonce);
    // utiliser l'objet a pour afficher les i
    
}}

    
    
  
    

