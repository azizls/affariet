/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.annonce;
import entity.commentaire;
import entity.reactions;
import entity.role;
import entity.user;
import static gui.FrontannonceCrontroller.id_annonce;
import static gui.HomeController.id_annonce;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.print.DocFlavor;
import service.AnnonceService;
import service.CommentaireService;
import service.ReactionsService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author lando
 */
public class ItemannonceController implements Initializable {

    private ItemannonceController ItemannonceController;
    @FXML
    private HBox itemC;
    @FXML
    private Pane pane;
    @FXML
    private Label texte_id_user;
    @FXML
    private ImageView button_dislike;
    @FXML
    private ImageView button_like;
    @FXML
    private Label label_like;
    @FXML
    private Label label_dislike;
   
     private  Connection conn;
    @FXML
    private Button BtnSupprimer;

    public ItemannonceController() {
        conn=DataSource.getInstance().getCnx();
    }

     public void setItemannonceController(ItemannonceController controller) {
        this.ItemannonceController = controller;
    }

    
    


    @FXML
    private Label libelle;
    @FXML
    private ImageView image;
    @FXML
    private TextField texte_type;
    @FXML
    private TextField texte_date;
    
    @FXML
    private TextArea texte_description;

    /**
     * Initializes the controller class.
     
     */
    int id_user=Affariet.userID;
    AnnonceService  as = new AnnonceService();
    //private annonce a = as.get_annonce(id_annonce);
   // public static int id_annonce;
    @FXML
    private ListView<?> table_commentaire;
    
    CommentaireService cs = new CommentaireService();
    private ObservableList<commentaire> commentaires;
    @FXML
     TableView<commentaire> tabe_commentaire;
    @FXML
    private TableColumn<?, ?> texte_comment;
    @FXML
    private TableColumn<?, ?> texte_id_user1;
    // int id_annonce=97;
     //AnnonceService as = new AnnonceService();
    //annonce a=new annonce();
   annonce a = as.get_annonce(FrontannonceCrontroller.id_annonce);
   // annonce a =new annonce();
    @FXML
    private Button button_commenter;
     
  @Override
public void initialize(URL url, ResourceBundle rb) {
    tabe_commentaire.refresh();
    int id_annonce = FrontannonceCrontroller.id_annonce;
    label_like. setText(String.valueOf(a.getNblikes()));
    texte_type.setText(String.valueOf(a.getType()));
    texte_date.setText(String.valueOf(a.getDate_annonce()));
    texte_description.setText(String.valueOf(a.getDescription()));

   // Chargement de l'image de l'annonce
    String dossierImages = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\images\\";
    String nomImage = a.getImage(); // récupère le nom de l'image de l'annonce
    String cheminImage = dossierImages + nomImage; // construit le chemin absolu de l'image
    Image img = new Image(new File(cheminImage).toURI().toString());
    image.setImage(img);
        try {
            as.get_annonce(id_annonce);
            affichercommentaire(id_annonce);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(ItemannonceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        button_commenter.setText("Commenter");
        button_commenter.setOnAction(event -> {
            try {
                commenter(event);
                 tabe_commentaire.refresh();
            } catch (IOException ex) {
                Logger.getLogger(ItemannonceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

         if (a.getId_user().getId_user()== id_user) {
            // Afficher le bouton de suppression
            BtnSupprimer.setVisible(true);
        } else {
            // Cacher le bouton de suppression
            BtnSupprimer.setVisible(false);
        }
    
        
        // Récupération du nombre de likes pour l'annonce en question
       try {
    String queryLikes = "SELECT COUNT(*) FROM reactions WHERE id_annonce = ? AND type_react = 'like'";
    PreparedStatement psLikes = conn.prepareStatement(queryLikes);
    psLikes.setInt(1, id_annonce);
    ResultSet rsLikes = psLikes.executeQuery();
    rsLikes.next();
    int nbr_likes = rsLikes.getInt(1);
    
    String queryDislikes = "SELECT COUNT(*) FROM reactions WHERE id_annonce = ? AND type_react = 'dislike'";
    PreparedStatement psDislikes = conn.prepareStatement(queryDislikes);
    psDislikes.setInt(1, id_annonce);
    ResultSet rsDislikes = psDislikes.executeQuery();
    rsDislikes.next();
    int nbr_dislikes = rsDislikes.getInt(1);

    // Affichage du nombre de likes et dislikes dans les labels correspondants
    label_like.setText(String.valueOf(nbr_likes));
    label_dislike.setText(String.valueOf(nbr_dislikes));
} catch (SQLException ex) {
    Logger.getLogger(ItemannonceController.class.getName()).log(Level.SEVERE, null, ex);
}
    
    
    
    
}

// TODO

    @FXML
private void commenter(ActionEvent event) throws IOException {
    
// Récupérer la scène actuelle
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Commenter.fxml"));
    Parent root = loader.load();
    CommenterController commenterController = loader.getController();
    commenterController.setAnnonceId(a.getId_annonce());
   updateUI(a);
   // commenterController.setItemannonceController(this);
    Scene scene = button_commenter.getScene();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
    
    // updateTableView(tabe_commentaire);
    
    
   
    
}






        

  


public void affichercommentaire(int id_annonce ) throws IOException, SQLException  {
        // annonce annonceSelectionnee = table_annonce.getSelectionModel().getSelectedItem();
       // annonceSelectionnee.getId_annonce();
   
      // texte_id_commentaire.setCellValueFactory(new PropertyValueFactory<>("id_commentaire"));
        texte_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        texte_id_user1.setCellValueFactory(new PropertyValueFactory<>("email"));
        //texte_annonce.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));

       // annonce a = as.get_annonce(FrontannonceCrontroller.id_annonce);
       as.get_annonce(FrontannonceCrontroller.id_annonce);
        List<commentaire> list = cs.getCommentairesByAnnonce1(id_annonce);
        commentaires = FXCollections.observableArrayList(list);
        tabe_commentaire.setItems(commentaires);
        
    }

 private void chargerDonnees() {
    // Récupérer la liste des annonces
    List<annonce> listeannonces = as.readAll();
    // Créer une liste observable pour la table
    FXCollections.observableArrayList(listeannonces);
    // Ajouter les annonces à la table
    //a.setItems(annonces);
 }

    @FXML
    private void importeranonce(MouseEvent event) {
        
        
        
    }

    
    

    

  @FXML
private void addlike(MouseEvent event) throws IOException {

    
  ReactionsService rs = new ReactionsService();
int id_annonce = a.getId_annonce();
annonce a = as.get_annonce(id_annonce);
int id_user = Affariet.userID;
annonce an = new annonce();

try {
        boolean userHasReacted = rs.checkUserReaction(id_user, id_annonce);

        if (userHasReacted) {
            // L'utilisateur a déjà réagi
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setContentText("Vous avez déjà réagi à cette annonce");
            
            alert.showAndWait();
        } else {
            // L'utilisateur n'a pas encore réagi
            boolean userHasDisliked = rs.checkUserReaction(id_user, id_annonce);

            if (userHasDisliked) {
                // L'utilisateur a déjà réagi avec un dislike, demander confirmation
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation");
                alert.setContentText("Vous avez déjà disliké cette annonce. Voulez-vous supprimer votre dislike et ajouter un like ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // Supprimer la réaction dislike existante
                    rs.deleteReaction(id_user, id_annonce);
an.setNblikes(a.getNbdislikes() - 1);

                    // Ajouter une nouvelle réaction like
                    reactions r = new reactions();
                    r.setType_react("like");
                    r.setId_annonce(a);
                    r.setId_user(id_user);
                    rs.insert(r, id_annonce, id_user);
an.setNblikes(a.getNblikes() + 1);

                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setHeaderText("Succès");
                    alert2.setContentText("Dislike supprimé et like ajouté !");
                    alert2.showAndWait();
                } else {
                    // L'utilisateur a annulé, ne rien faire
                }
            } else {
                // L'utilisateur n'a pas réagi avec un dislike, ajouter une réaction de type "like"
                reactions r = new reactions();
                r.setType_react("like");
                r.setId_annonce(a);
                r.setId_user(id_user);
                rs.insert(r, id_annonce, id_user);
                a.setNblikes(a.getNblikes() + 1);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Succès");
                alert.setContentText("Like ajouté !");
                alert.showAndWait();
            }
        }

        // Rafraîchir la vue de l'annonce pour afficher le nouveau nombre de likes
        updateUI(a);
    
    //label_like. setText(String.valueOf(a.getNblikes()));

} catch (SQLException ex) {
    Logger.getLogger(ItemannonceController.class.getName()).log(Level.SEVERE, null, ex);
    // Afficher un message d'erreur en cas d'exception
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText("Erreur");
    alert.setContentText("Erreur lors de l'ajout du like");
    alert.showAndWait();
}
}

   
    

 @FXML
private void adddislike(MouseEvent event) throws IOException {

    ReactionsService rs = new ReactionsService();
    int id_annonce = a.getId_annonce();
    annonce a = as.get_annonce(id_annonce);
    int id_user = Affariet.userID;
annonce an = new annonce();
    try {
        boolean userHasReacted = rs.checkUserReaction(id_user, id_annonce);

        if (userHasReacted) {
            // L'utilisateur a déjà réagi, afficher une boîte de dialogue pour confirmer s'il veut supprimer son dislike
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Confirmation");
            alert.setContentText("Vous avez déjà réagi à cette annonce. Voulez-vous supprimer votre dislike ?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression de son dislike, supprimer la réaction existante et ajouter un like
                rs.deleteReaction(id_user, id_annonce);
                
                

                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setHeaderText("Succès");
                successAlert.setContentText("Dislike supprimé  !");
                successAlert.showAndWait();
                an.setNblikes(a.getNbdislikes() - 1);
                // Rafraîchir la vue de l'annonce pour afficher le nouveau nombre de likes
                //itemannonceController.updateAnnonceView(a);
            }
        } else {
            // L'utilisateur n'a pas encore réagi, ajouter une réaction de type "dislike"
            reactions r = new reactions();
            r.setType_react("dislike");
            r.setId_annonce(a);
            r.setId_user(id_user);
            rs.insert(r, id_annonce, id_user);
an.setNblikes(a.getNbdislikes() + 1);
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setHeaderText("Succès");
            successAlert.setContentText("Dislike ajouté !");
            successAlert.showAndWait();
        }

        // Rafraîchir la vue de l'annonce pour afficher le nouveau nombre de likes et dislikes
        //itemannonceController.updateAnnonceView(a);
    } catch (SQLException ex) {
        Logger.getLogger(ItemannonceController.class.getName()).log(Level.SEVERE, null, ex);
        // Afficher un message d'erreur en cas d'exception
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Erreur");
        alert.setContentText("Erreur lors de l'ajout du dislike");
        alert.showAndWait();
    }
}


    public void updateUI(annonce annonce) {
       // annonce a = as.get_annonce(FrontannonceCrontroller.id_annonce);
     label_dislike.setText(String.valueOf(a.getNblikes()));   
    label_like.setText(String.valueOf(a.getNblikes()));
    texte_type.setText(String.valueOf(a.getType()));
    texte_date.setText(String.valueOf(a.getDate_annonce()));
    texte_description.setText(String.valueOf(a.getDescription()));
 
    String dossierImages = "C:\\Users\\lando\\Documents\\NetBeansProjects\\affariet\\image\\";
    String id_annonce_str = String.valueOf(a.getId_annonce());
    File dossier = new File(dossierImages);

    File[] fichiers = dossier.listFiles();
    for (File fichier : fichiers) {
        String nomFichier = fichier.getName();

        if (nomFichier.startsWith(id_annonce_str) && (nomFichier.endsWith(".jpg")
                || nomFichier.endsWith(".png") || nomFichier.endsWith(".PNG"))) {
            String cheminImage = fichier.toURI().toString();
            Image img = new Image(cheminImage);
            image.setImage(img);

        }
    }
}

    @FXML
    private void supprimerAnnonce(ActionEvent event) {
        
        as.delete(a);
        
    }

















}

    
//    public void refreche() throws SQLException {
//
//        texte_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
//        texte_id_user1.setCellValueFactory(new PropertyValueFactory<>("id_user1"));
//        table_commentaire.getItems().clear();
//
//        table_commentaire.setItems( cs.getCommentairesByAnnonce1(id_annonce));
//
//    }


