/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;
import javafx.scene.control.ComboBox;
import entity.annonce;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class AddannoncefrontController implements Initializable {

    @FXML
    private TextField texte_image;
    @FXML
    private TextField texte_description;
    @FXML
    private Button texte_button_image;
    @FXML
    private ComboBox<String> comboBox_type;
    @FXML
    private Button button_apply;

    annonce a = new annonce();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "annonce commerciale",
                        "avis sur un produit",
                        "publicité"
                );
        comboBox_type.setItems(options);
    }

    @FXML
    private void apply(ActionEvent event) throws IOException {
        int id_user = Affariet.userID;

        String type = comboBox_type.getValue();
        String description = texte_description.getText();
        String nomImage = texte_image.getText();
        File selectedFile1 = new File(nomImage);
        annonce a = new annonce(type, description, selectedFile1.getName(), id_user);

        AnnonceService as = new AnnonceService();
        int annonceId = as.ajouterannonce(a, id_user);
        String id_annonce_str = String.valueOf(annonceId);
        String destinationPath = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\images\\";

        // Récupérer le fichier sélectionné
        File selectedFile = new File(texte_image.getText());

        // Définir le nom de l'image dans l'objet annonce

        // Copier le fichier sélectionné dans le dossier de destination
        File destinationFile = new File(destinationPath + selectedFile.getName());
        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        a.setImage(selectedFile.getName());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setContentText("annonce ajoutée");
    }

    @FXML
    private void image(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagename = selectedFile.getAbsolutePath();
            texte_image.setText(imagename);
        }
    }

    @FXML
    private void backss(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("frontannonce.fxml"));
                Scene scene = new Scene(root);
                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
    }
}
