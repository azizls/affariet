package gui;

import entity.annonce;
import entity.user;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import service.AnnonceService;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class AfficherannonceController implements Initializable {

    @FXML
    private TableView<annonce> table_annonce;
    @FXML
    private Button button_supprimer;
    @FXML
    private TableColumn<annonce, Integer> texte_id_annonce;
    @FXML
    private TableColumn<annonce, String> texte_type;
    @FXML
    private TableColumn<annonce, String> texte_description;
    @FXML
    private TableColumn<annonce, user> texte_id_user;
    @FXML
    private TableColumn<annonce, String> texte_image;

    private ObservableList<annonce> annonces;
    private AnnonceService as = new AnnonceService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurer les colonnes de la tableù
        
        
        
        
        texte_id_annonce.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        texte_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        texte_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        texte_id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        //texte_image.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Charger les données
        chargerDonnees();
    }

    @FXML
    private void delete(ActionEvent event) {
        // Récupérer l'annonce sélectionnée dans la table
        annonce a = table_annonce.getSelectionModel().getSelectedItem();
        if (a != null) {
            // Appeler la méthode delete de annonceService pour supprimer l'annonce
            as.delete(a);
            // Retirer l'annonce de la liste des annonces
            annonces.remove(a);
        }
    }

  private void chargerDonnees() {
    // Récupérer la liste des annonces
    List<annonce> listeannonces = as.readAll();
    // Créer une liste observable pour la table
    annonces = FXCollections.observableArrayList(listeannonces);
    // Ajouter les annonces à la table
    table_annonce.setItems(annonces);
    // Mettre à jour la colonne de l'image
//    texte_image.setCellFactory(column -> {
//    return new TableCell<annonce, String>() {
//        private final AnchorPane hbox = new AnchorPane();
//        private final ImageView imageView = new ImageView();
//        private final Label label = new Label();
//
//        {
//            imageView.setFitHeight(50);
//            imageView.setFitWidth(50);
//            hbox.getChildren().addAll(imageView, label);
//        }
//
//        @Override
//        protected void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//            if (item == null || empty) {
//                setGraphic(null);
//                label.setText(null);
//            } else {
//                setGraphic(hbox);
//                try {
//                    InputStream stream = new FileInputStream(new File(item));
//                    Image image = new Image(stream);
//                    imageView.setImage(image);
//                    label.setText(null);
//                } catch (IOException e) {
//                    System.err.println("Erreur lors de la lecture de l'image : " + e.getMessage());
//                }
//            }
     }
    

        };
 



    


   