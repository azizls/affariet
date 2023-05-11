package gui;

import entity.Categorie;
import entity.Produit;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ProduitService;
import service.CategorieService;

import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProduitController {

    @FXML
    private TableView<Produit> produitTable;

    @FXML
    private TableColumn<Produit, Integer> idColumn;

    @FXML
    private TableColumn<Produit, String> nomColumn;

    @FXML
    private TableColumn<Produit, String> descriptionColumn;

    @FXML
    private TableColumn<Produit, Float> prixColumn;

    @FXML
    private TableColumn<Produit, Categorie> categorieColumn;

    @FXML
    private Button deleteButton;
    @FXML
    private Button ajouter;
    private ProduitService produitService = new ProduitService();

    private ObservableList<Produit> produitList = FXCollections.observableArrayList();
    @FXML
    private Button refreshButton;
    @FXML
    private ImageView Image_user;

    public void initialize() {
        // Set up the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description_produit"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));

        // Load data from the database
        List<Produit> produits = produitService.readAll();
        produitList.addAll(produits);

        // Bind the table to the observable list
        produitTable.setItems(produitList);
    }

    @FXML
    void deleteProduit(ActionEvent event) {
        Produit selectedProduit = produitTable.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            produitService.delete(selectedProduit);
            produitList.remove(selectedProduit);
        }
    }
    @FXML
    void ajouterProduit(ActionEvent event) throws IOException {
        // Load the new FXML file
FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduit.fxml"));
Parent root = loader.load();

// Get the current scene and set the new FXML file as the root node
Scene scene = ajouter.getScene();
scene.setRoot(root);
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

    @FXML
    private void categorie(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Categorie.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();

        
    }

    
}