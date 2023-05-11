/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Produit;
import java.io.File;
import service.ProduitService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FrontProduitCrontroller implements Initializable {

    @FXML
    private ScrollPane scrollpane_Salle;
    @FXML
    private HBox hbox_salle;
    static int id_produit_static=0;
    
    private int taille_produit=0;
    private ProduitService SP = new ProduitService();
    private int indiceProduit=0;
    @FXML
    private ImageView Image_user;
    @FXML
    private Pane pane_aff;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             List<Produit> ls = SP.readAll();
         taille_produit=ls.size();
    Node[] nodes_produit = new Node[taille_produit];
        scrollpane_Salle.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for (indiceProduit = 0; indiceProduit < taille_produit; indiceProduit++) {
    try {
        id_produit_static = ls.get(indiceProduit).getId_produit();
        nodes_produit[indiceProduit] = FXMLLoader.load(getClass().getResource("/gui/itemProduit.fxml"));
        hbox_salle.getChildren().add(nodes_produit[indiceProduit]);
    } catch (IOException e) {
        // handle exception
    }
}
        String dossierImages = "C:\\xampp\\htdocs\\admin\\";
        String email= gui.Affariet.email ; 
        File dossier = new File(dossierImages);
        File[] fichiers = dossier.listFiles();
        for (File fichier : fichiers) {
        String nomFichier = fichier.getName();
        if (nomFichier.startsWith(email) && nomFichier.endsWith(".PNG")||nomFichier.startsWith(email) && nomFichier.endsWith(".gif")||nomFichier.startsWith(email) && nomFichier.endsWith(".png")||nomFichier.startsWith(email) && nomFichier.endsWith(".jpg")||nomFichier.startsWith(email) && nomFichier.endsWith(".JPG")) {
            String cheminImage = fichier.toURI().toString();
            Image image = new Image(cheminImage);
            Image_user.setImage(image);
            break;
        }
    } }   

    @FXML
    private void backss(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("AddProduit.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
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

    @FXML
    private void mesproduits(ActionEvent event) {
        int id_user = Affariet.userID;
         List<Produit> ln = SP.getByUser(id_user);
         taille_produit=ln.size();
    Node[] nodes_produit = new Node[taille_produit];
        scrollpane_Salle.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for (indiceProduit = 0; indiceProduit < taille_produit; indiceProduit++) {
    try {
        id_produit_static = ln.get(indiceProduit).getId_produit();
        nodes_produit[indiceProduit] = FXMLLoader.load(getClass().getResource("itemProduit.fxml"));
        hbox_salle.getChildren().add(nodes_produit[indiceProduit]);
    } catch (IOException e) {
        // handle exception
    }
}
    }}
        
    
    
    

