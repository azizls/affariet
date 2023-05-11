/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entity.user;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author turki
 */
public class FrontmodifierController implements Initializable {

    @FXML
    private TextField text_nom;
    @FXML
    private TextField text_prenom;
    @FXML
    private TextField text_age;
    @FXML
    private TextField text_mdp;
    @FXML
    private TextField text_adresse;
    @FXML
    private TextField text_num;
    @FXML
    private Button btn_modifier;
    @FXML
    private ImageView imageview;
    @FXML
    private Button changer_image;
    @FXML
    private ImageView qrcodee;
    @FXML
    private TextField text_image;
    @FXML
    private ImageView Image_user;

 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int utilisateurconnecte =Affariet.userID;
        String email =Affariet.email;
        System.out.println(utilisateurconnecte);
       UserService userService = new UserService();
    user utilisateur = userService.readById(utilisateurconnecte);
    
    text_nom.setText(utilisateur.getNom());
    text_prenom.setText(utilisateur.getPrenom());
    text_num.setText(String.valueOf(utilisateur.getNumero()));
    text_adresse.setText(utilisateur.getAdresse());
    text_mdp.setText(utilisateur.getMdp());
    text_age.setText(String.valueOf(utilisateur.getAge()));
    
    
   user u = userService.readById(utilisateurconnecte);
       
     
    String dossierImages = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\images\\user\\";

       String nomImage = u.getImage(); // récupère le nom de l'image de l'annonce
    String cheminImage = dossierImages + nomImage; // construit le chemin absolu de l'image
    Image img = new Image(new File(cheminImage).toURI().toString());
    imageview.setImage(img);


        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            String Information = "nom : "+utilisateur.getNom()+"\n"+"prenom : "+utilisateur.getPrenom();
            int width = 300;
            int height = 300;
            
            BufferedImage bufferedImage = null; 
            BitMatrix byteMatrix = qrCodeWriter.encode(Information, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            
            System.out.println("Success...");
            
            
            

            
            qrcodee.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            // TODO
        } catch (WriterException ex) {
            Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        int id_utilisateur = Affariet.userID;
    UserService userService = new UserService();
    user utilisateur = userService.readById(id_utilisateur);
    String mdp = text_mdp.getText();
     String hashPassword = gui.Motdepass.hashPassword(mdp);
    
    utilisateur.setNom(text_nom.getText());
    utilisateur.setPrenom(text_prenom.getText());
    utilisateur.setNumero(Integer.parseInt(text_num.getText()));
    utilisateur.setAdresse(text_adresse.getText());
    utilisateur.setMdp(mdp);
    utilisateur.setAge(Integer.parseInt(text_age.getText()));
    utilisateur.setImage(btn_modifier.getText());
    utilisateur.setImage(text_image.getText());
     

    userService.update(utilisateur);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setHeaderText(null);
    alert.setContentText("L'utilisateur a été modifié avec succès !");
    alert.showAndWait();
    }

    @FXML
    private void changer_img(ActionEvent event) throws IOException {
          int utilisateurconnecte =Affariet.userID;
       // String email =Affariet.email;
        System.out.println(utilisateurconnecte);
       UserService userService = new UserService();
    user utilisateur = userService.readById(utilisateurconnecte);
    
                String dossierImages = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\images\\user\\";
         String email =Affariet.email;
        File dossier = new File(dossierImages);
        File[] fichiers = dossier.listFiles();
        for (File fichier : fichiers) {
            String nomFichier = fichier.getName();
           if (nomFichier.startsWith(nomFichier) && nomFichier.endsWith(".PNG")||nomFichier.startsWith(nomFichier) && nomFichier.endsWith(".gif")||nomFichier.startsWith(nomFichier) && nomFichier.endsWith(".png")||nomFichier.startsWith(nomFichier) && nomFichier.endsWith(".jpg")||nomFichier.startsWith(nomFichier) && nomFichier.endsWith(".JPG")) {
                // Si le fichier existe, le supprimer
                if (fichier.exists()) {
                    fichier.delete();
                }
            }
        }

       
        
        FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choisir une image");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"),
        new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
    );
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        String imagePath = selectedFile.getAbsolutePath();
        text_image.setText(imagePath);
    String destinationPath = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\images\\user\\";

// Récupérer le fichier sélectionné
        File selectedFile2 = new File(text_image.getText());

// Créer un nouveau fichier dans le dossier de destination avec le même nom que le fichier sélectionné
        File destinationFile = new File(destinationPath + selectedFile2.getName());

// Copier le fichier sélectionné dans le dossier de destination
        Files.copy(selectedFile2.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

// Définir le nom de l'image dans l'utilisateur
        utilisateur.setImage(selectedFile2.getName());
    }
    
    }

    private void home(MouseEvent event)throws IOException {
        
    Parent root = FXMLLoader.load(getClass().getResource("seconnecter.fxml"));
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}        

    private void disconnect(MouseEvent event) throws IOException {
        
    Parent root = FXMLLoader.load(getClass().getResource("seconnecter.fxml"));
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }
    

 @FXML
    private void evenement(ActionEvent event) throws IOException {
        
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("FrontEv.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }

    @FXML
    private void blog(ActionEvent event) throws IOException {
          Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("frontannonce.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void monprofil(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("Frontmodifier.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void reclamation(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("reclamation.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void produit(ActionEvent event) throws IOException {
        
          Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("FrontProduit.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }

    @FXML
    private void commande(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("mesCommandeTroc.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }
    
    
}
    

