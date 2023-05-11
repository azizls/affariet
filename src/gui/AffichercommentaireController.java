/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.annonce;
import entity.commentaire;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import service.CommentaireService;

/**
 * FXML Controller class
 *
 * @author lando
 */
public class AffichercommentaireController implements Initializable {
    
    private annonce annonceSelectionnee;
    @FXML
    private AnchorPane AnchorPane;
    
    public void setAnnonceSelectionnee(annonce annonceSelectionnee) {
        this.annonceSelectionnee = annonceSelectionnee;
    }
    
    @FXML
    private TableColumn<commentaire, String> texte_id_commentaire;
    @FXML
    private TableColumn<commentaire, String> texte_comment;
    @FXML
    private TableColumn<commentaire, String> texte_annonce;
    @FXML
    private TableColumn<commentaire, String> texte_user1;
    
    private ObservableList<commentaire> commentaires;
    
    CommentaireService cs = new CommentaireService();
    @FXML
    private TableView<commentaire> table_commentaire;
    private int id_annonce;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //id_annonce = annonceSelectionnee;
        
        texte_id_commentaire.setCellValueFactory(new PropertyValueFactory<>("id_commentaire"));
        texte_comment.setCellValueFactory(new PropertyValueFactory<>("coment"));
        texte_user1.setCellValueFactory(new PropertyValueFactory<>("id_user1"));
        texte_annonce.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));

        try {
            chargerCommentaires(annonceSelectionnee);
           

        } catch (SQLException ex) {
            Logger.getLogger(AffichercommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   private void chargerCommentaires(annonce a) throws SQLException {
        List<commentaire> list = cs.getCommentairesByAnnonce1(id_annonce);
        commentaires = FXCollections.observableArrayList(list);
        table_commentaire.setItems(commentaires);
    }}