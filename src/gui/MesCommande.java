/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Commande;
import entity.user;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.CommandeService;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author jawhe
 */
public class MesCommande implements Initializable {

    @FXML
    private ImageView Image_user;
    @FXML
    private TableView<Commande> tableCommande;
    @FXML
    private TableColumn<Commande, String> produitfx;
    @FXML
    private TableColumn<Commande, String> prixfx;
    @FXML
    private TableColumn<Commande, String> datefx;
    @FXML
    private Label nbCommande;
    @FXML
    private Label PrixTotale;
private ObservableList<Commande> commandes;
    private CommandeService cs = new CommandeService();
    /**
     * Initializes the controller class.
     */
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produitfx.setCellValueFactory(cellData -> {
    Commande commande = cellData.getValue();
    StringProperty produitNomProperty = new SimpleStringProperty(commande.getId_produit().getNom_produit());
    return produitNomProperty;
});
        prixfx.setCellValueFactory(cellData -> {
    Commande commande = cellData.getValue();
    FloatProperty prixProperty = new SimpleFloatProperty(commande.getId_produit().getPrix_produit());
    return prixProperty.asString("%.2f");
});
     datefx.setCellValueFactory(cellData -> {
        Commande commande = cellData.getValue();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(commande.getDate_commande());
        StringProperty dateProperty = new SimpleStringProperty(formattedDate);
        return dateProperty;
    });
     charge();
    }

    public void setNombreCommande(long nombreTroc) {
    this.nbCommande.setText(String.valueOf(nombreTroc));
}

   public void setPrixTotale(double prix) {
    this.PrixTotale.setText(String.valueOf(prix));
}
 private void charge(){
        
      UserService us=new UserService();
    user u1 = us.readById(Affariet.userID);
     
         List<Commande> listeCommande = cs.readAllById(u1.getId_user());
        commandes = FXCollections.observableArrayList(listeCommande);
        tableCommande.setItems(commandes);
        System.out.println(listeCommande);
        this.setNombreCommande(listeCommande.stream().count());
        float prixTotal = 0;
for(Commande c : listeCommande) {
    prixTotal += c.getId_produit().getPrix_produit();
}
this.setPrixTotale(prixTotal);
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
