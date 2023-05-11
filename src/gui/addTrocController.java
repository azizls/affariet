/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Categorie;
import entity.Commande;
import entity.Produit;
import entity.Troc;
import entity.user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import service.CommandeService;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import service.TrocService;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import service.UserService;
/**
 *
 * @author jawhe
 */
public class addTrocController implements Initializable  {
    private Produit idP;
    @FXML
    private Label Trocfx;
    @FXML
    private TextField descriptionTrocfx;
    @FXML
    private TextField nomProduitTrocfx;
    @FXML
    private Button imagefx;
    
    
    @FXML
    private Button trocfx;
    @FXML
    private AnchorPane txt1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void ajouterTroc(ActionEvent event) throws IOException {
        
    user u=new user();
      UserService us=new UserService();
      u= us.readById(Affariet.userID);
      user pu=new user();
      pu=us.readById(8);
       Categorie c=new Categorie(1,"oo");
      Produit p=new Produit(1,"chaise" ," jaune", 1000,"C:\\Users\\lando\\Documents\\NetBeansProjects\\affariet\\image\\95;ca.png", c,pu);
      
      
       
        int id=0;
        TrocService pst=new TrocService();
         Troc t=new Troc(idP,nomProduitTrocfx.getText(),descriptionTrocfx.getText(),imagefx.getText(),u);
          //pst.insertPst(t);
          id=pst.insertretrieveid(t);
          // Récupérer le chemin du dossier de destination
        String destinationPath = "C:\\xampp\\htdocs\\img\\"+id+";";

// Récupérer le fichier sélectionné
        File selectedFile = new File(imagefx.getText());

// Créer un nouveau fichier dans le dossier de destination avec le même nom que le fichier sélectionné
        File destinationFile = new File(destinationPath + selectedFile.getName());

// Copier le fichier sélectionné dans le dossier de destination
        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

// Définir le nom de l'image dans l'utilisateur
        t.setImage_produitTroc(selectedFile.getName());
          try {
              
              
              
              
              
        FXMLLoader loader = new FXMLLoader(getClass().getResource("detailTroc.fxml"));

            Parent root = loader.load();

             //Create a new Scene object for the new page
          DetailTrocController dc=loader.getController();
        dc.setT(t);
        dc.setId(id);
        dc.setP(p);
        dc.setDetailtrocfx(Trocfx.getText());
        dc.setImageTrocproduit(id);
        Trocfx.getScene().setRoot(root);
        dc.setNomProduitFX(nomProduitTrocfx.getText());
        dc.setDiscriptionfx(descriptionTrocfx.getText());
        dc.setP(idP);
        } catch (IOException e) {
            e.printStackTrace();
        }
          TrocService ts = new TrocService();
           Troc troc = ts.readById(id);
    System.out.println(troc);
    }
    public void setLabn(String labn) {
        this.Trocfx.setText(labn);
    }

    @FXML
    private void ajouterimage(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
        new ExtensionFilter("All Files", "*.*"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
       String imagePath=selectedFile.getAbsolutePath();
       imagefx.setText(imagePath);
   }
    }
     public void setid(Produit p) {
        this.idP=p;
    }
    
}
