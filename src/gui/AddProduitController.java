package gui;

import entity.Produit;
import entity.Categorie;
import entity.user;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import service.ProduitService;
import service.CategorieService;
import service.UserService;

public class AddProduitController {
    private final String UPLOAD_DIR = "C:\\xampp\\htdocs\\image";
    String path="";
    private int id_produit_static;
    

    @FXML
    private TextField nom_produit_field;

    @FXML
    private TextField description_produit_field;

    @FXML
    private TextField prix_produit_field;

    @FXML
    private ComboBox<Categorie> categorie_field;

    @FXML
    private ImageView image_produit_view;

    private File imageFile;

    private ProduitService produitService;
    private CategorieService categorieService;

    public AddProduitController() {
        produitService = new ProduitService();
        categorieService = new CategorieService();
    }

    @FXML
    void initialize() {
        List<Categorie> categories = categorieService.readAll();
        categorie_field.getItems().addAll(categories);
    }

    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            path=selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            image_produit_view.setImage(image);
        }
    }

    @FXML
    void addProduit() throws IOException {
        String nom_produit = nom_produit_field.getText();
        String description_produit = description_produit_field.getText();
        float prix_produit = Float.parseFloat(prix_produit_field.getText());
        Categorie categorie = categorie_field.getSelectionModel().getSelectedItem();
        Image photo =image_produit_view.getImage();
        if (nom_produit.isEmpty() || description_produit.isEmpty() || prix_produit <= 0 || photo == null || categorie == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields and select an image file");
            alert.showAndWait();
            return;
        }
         user u=new user();
      UserService us=new UserService();
     u= us.readById(Affariet.userID);
        Produit produit = new Produit(nom_produit, description_produit, prix_produit, path, categorie,u);
        int id=0;
        id=produitService.insertretrieveid(produit);
        
         String destinationPath = "C:\\xampp\\htdocs\\image\\"+id+";";

// Récupérer le fichier sélectionné
        File selectedFile = new File(path);

// Créer un nouveau fichier dans le dossier de destination avec le même nom que le fichier sélectionné
        File destinationFile = new File(destinationPath + selectedFile.getName());

// Copier le fichier sélectionné dans le dossier de destination
        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Product added successfully");
        alert.showAndWait();
        clearFields();
    }

    private void clearFields() {
        nom_produit_field.setText("");
        description_produit_field.setText("");
        prix_produit_field.setText("");
        categorie_field.getSelectionModel().clearSelection();
        imageFile = null;
        image_produit_view.setImage(null);
    }

    
}
