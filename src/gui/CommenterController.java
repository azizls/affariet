/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.annonce;
import entity.commentaire;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.AnnonceService;
import service.CommentaireService;

/**
 * FXML Controller class
 *
 * @author lando
 */
public class CommenterController implements Initializable {
    private int id_annonce;
    private AnnonceService annonceService;
    private CommentaireService commentaireService;
    @FXML
    private TextField texte_comment;
    @FXML
    private Button button_submit;
    
           int id_user1 = Affariet.userID;
    String mail=Affariet.email;
    // Référence à votre tableview
    @FXML
    private TableView<commentaire> tabe_commentaire;

    // ...

  


    // ...
 @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    public void setAnnonceId(int id_annonce) {
        this.id_annonce = id_annonce;
        this.annonceService = new AnnonceService();
        this.commentaireService = new CommentaireService();
        annonce annonce = this.annonceService.get_annonce(id_annonce);

        // Afficher les informations de l'annonce dans le formulaire
        // Exemple: titre_annonce.setText(annonce.getTitre());
    }

    @FXML
    private void submit(ActionEvent event) throws SQLException {
        String comment = texte_comment.getText();
        
        annonce annonce = this.annonceService.get_annonce(id_annonce);
        commentaire c = new commentaire(comment, annonce, id_user1,Affariet.email);
        this.commentaireService.insert(c,id_annonce,id_user1,Affariet.email);
  
        // Fermer la fenêtre
        Stage stage = (Stage) texte_comment.getScene().getWindow();
        stage.close();
      
    }

    // ...
  // Appelé lorsque le commentaire a été ajouté avec succès
    private void updateTableView() throws SQLException {
        // Obtenez tous les commentaires de l'annonce
        List<commentaire> commentaires = this.commentaireService.getCommentairesByAnnonce1(id_annonce);

        // Effacez la tableview et ajoutez les nouveaux commentaires
        tabe_commentaire.getItems().clear();
        tabe_commentaire.getItems().addAll(commentaires);
    }
   
}