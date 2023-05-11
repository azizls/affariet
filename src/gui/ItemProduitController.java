/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Categorie;
import entity.Commande;
import entity.Produit;
import entity.user;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import service.CommandeService;
import service.ProduitService;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class ItemProduitController implements Initializable {

    @FXML
    private HBox itemC;
    @FXML
    private Label libelle;
    @FXML
    private Label label_prix;
    @FXML
    private Label prix;
    @FXML
    private ImageView image;
    @FXML
    private Label desc;
    ProduitService SP = new ProduitService();
    private Produit p = SP.readById(FrontProduitCrontroller.id_produit_static);
    @FXML
    private Label nom;
    @FXML
    private Button echangefx;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          nom.setText(String.valueOf(p.getNom_produit()));
        prix.setText(String.valueOf(p.getPrix_produit()));
        desc.setText(String.valueOf(p.getDescription_produit()));
        // get the image path of the product
   
                Image img = Retournerimage(p.getId_produit());
                image.setImage(img);
            
        
   
}
    
private Image Retournerimage(int i) {
    // Chemin du dossier contenant les images
    String dossierImages = "C:\\xampp\\htdocs\\image\\";
  
    // Convertir l'entier i en une chaîne de caractères
    String id_event_str = String.valueOf(i);
    
    // Créer un objet File pour représenter le dossier
    File dossier = new File(dossierImages);       

    // Récupérer la liste des fichiers dans le dossier
    File[] fichiers = dossier.listFiles();
    
    // Parcourir la liste des fichiers
    for (File fichier : fichiers) {
        // Récupérer le nom du fichier
        String nomFichier = fichier.getName();
         
        // Vérifier si le nom de fichier commence par id_event_str et se termine par .jpg, .png ou .PNG
        if (nomFichier.startsWith(id_event_str) && (nomFichier.endsWith(".jpg") || nomFichier.endsWith(".png") || nomFichier.endsWith(".PNG"))) {
            // Si le fichier correspond, convertir le chemin du fichier en une URL d'image et créer un objet Image à partir de l'URL
            String cheminImage = fichier.toURI().toString();
            Image img = new Image(cheminImage);
            
            // Retourner l'image trouvée
            return img;
        }
    }
    
    // Si aucun fichier ne correspond, retourner null
    return null;
}
    

    @FXML
    private void commander(ActionEvent event) {
        user u=new user();
      UserService us=new UserService();
     u= us.readById(Affariet.userID);
      Commande p1=new Commande( p,u);
       CommandeService ps=new CommandeService();
       
        
        
        
       int id=0;
        
        //id=ps.insertretrieveid(p1);
        System.out.println(id);
       


        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailCommandeFXML.fxml"));

            Parent root = loader.load();

            // Create a new Scene object for the new page
          DetailCommandeController dc=loader.getController();
        
        dc.setP(p);
        label_prix.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void echange(ActionEvent event) {
        UserService pst=new UserService();
          user user= new user();
          user=pst.readById(Affariet.userID);
          System.out.println(user);
         
      
           try {
           FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddtrocFXML.fxml"));

            Parent root = loader1.load();

            // Create a new Scene object for the new page
          addTrocController dc=loader1.getController();
        dc.setid(p);
        nom.getScene().setRoot(root);
         } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

   
