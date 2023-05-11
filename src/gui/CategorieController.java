package gui;

import entity.Categorie;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.CategorieService;

public class CategorieController implements Initializable {

    @FXML
    private TextField tfNomCategorie;

    @FXML
    private Button btnAjouter;

    @FXML
    private TableView<Categorie> tblCategories;

    @FXML
    private TableColumn<Categorie, Integer> colIdCategorie;

    @FXML
    private TableColumn<Categorie, String> colNomCategorie;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    private CategorieService categorieService;
    @FXML
    private Button btnChercher;
    @FXML
    private ImageView Image_user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdCategorie.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
        colNomCategorie.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
        categorieService = new CategorieService();
        afficherCategories();
    }

    @FXML
    private void ajouterCategorie() {
        String nomCategorie = tfNomCategorie.getText();
        if (nomCategorie.isEmpty()) {
            showErrorMessage("Le nom de la catégorie est obligatoire.");
            return;
        }
        Categorie categorie = new Categorie(nomCategorie);
        categorieService.insert(categorie);
        afficherCategories();
    }

    @FXML
    private void modifierCategorie() {
        Categorie categorieSelectionnee = tblCategories.getSelectionModel().getSelectedItem();
        if (categorieSelectionnee == null) {
            showErrorMessage("Veuillez sélectionner une catégorie à modifier.");
            return;
        }
        String nouveauNomCategorie = tfNomCategorie.getText();
        if (nouveauNomCategorie.isEmpty()) {
            showErrorMessage("Le nom de la catégorie est obligatoire.");
            return;
        }
        categorieSelectionnee.setNom_categorie(nouveauNomCategorie);
        categorieService.update(categorieSelectionnee);
        afficherCategories();
    }

    @FXML
    private void supprimerCategorie(ActionEvent event) {
    Categorie selectedCategorie = tblCategories.getSelectionModel().getSelectedItem();
    if (selectedCategorie == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression de catégorie");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une catégorie.");
        alert.showAndWait();
        return;
    }
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Suppression de catégorie");
    alert.setHeaderText(null);
    alert.setContentText("Voulez-vous vraiment supprimer cette catégorie?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        CategorieService cs = new CategorieService();
        cs.delete(selectedCategorie);
        tblCategories.getItems().remove(selectedCategorie);
    }
}

    private void afficherCategories() {
        List<Categorie> categories = categorieService.readAll();
        tblCategories.getItems().setAll(categories);
        tblCategories.getSelectionModel().clearSelection();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void chercherCategorie(ActionEvent event) {
        String nomCategorie = tfNomCategorie.getText();
    if (nomCategorie.isEmpty()) {
        showErrorMessage("Le nom de la catégorie est obligatoire.");
        return;
    }
    List<Categorie> categories = categorieService.getByNom(nomCategorie);
    if (categories.isEmpty()) {
        showErrorMessage("Aucune catégorie trouvée avec ce nom.");
        return;
    }
    tblCategories.getItems().setAll(categories);
    tblCategories.getSelectionModel().clearSelection();
    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
        Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("ProduitStats.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Produit.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }
}
