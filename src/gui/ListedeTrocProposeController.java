/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.TrocService;
import entity.Troc;
import entity.user;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jawhe
 */
public class ListedeTrocProposeController implements Initializable {

    @FXML
    private TableView<Troc> tabelauTroc;
    @FXML
    private Button deletefx;
    @FXML
    private TableColumn<Troc, String> nomProduitfx;
    @FXML
    private TableColumn<Troc, String> descriptionfx;
    @FXML
    private TableColumn<Troc, String> produitAechangefx;
    @FXML
    private TableColumn<Troc, String> descriptionAechanger;
    

    
    private ObservableList<Troc> trocs;
    private TrocService ts = new TrocService();
    
    private Label selectP;
    @FXML
    private TableColumn<Troc,String> acheteurfx;
    @FXML
    private Button chercherParnom;
    @FXML
    private TextField chercherAcheteur;
    @FXML
    private Label nombreTroc;
    @FXML
    private Button refreshTab;
    @FXML
    private ImageView Image_user;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomProduitfx.setCellValueFactory(new PropertyValueFactory<>("nom_produitTroc"));
        descriptionfx.setCellValueFactory(new PropertyValueFactory<>("description_produitTroc"));
        produitAechangefx.setCellValueFactory(cellData -> {
    Troc troc = cellData.getValue();
    StringProperty produitNomProperty = new SimpleStringProperty(troc.getProduit().getNom_produit());
    return produitNomProperty;
});
        descriptionAechanger.setCellValueFactory(cellData -> {
    Troc troc = cellData.getValue();
    StringProperty produitNomProperty = new SimpleStringProperty(troc.getProduit().getDescription_produit());
    return produitNomProperty;
});
        acheteurfx.setCellValueFactory(cellData -> {
    Troc troc = cellData.getValue();
    StringProperty produitNomProperty = new SimpleStringProperty(troc.getuser().getNom());
    return produitNomProperty;
});
       
        chargerDonnees();
    }    

    @FXML
    private void delete(ActionEvent event) {
        // Récupérer l'annonce sélectionnée dans la table
        Troc t = tabelauTroc.getSelectionModel().getSelectedItem();
        if (t != null) {
            // Appeler la méthode delete de annonceService pour supprimer l'annonce
            ts.delete(t.getId_troc());
            // Retirer l'annonce de la liste des annonces
            trocs.remove(t);
        }
    }

    public void setNombreTroc(long nombreTroc) {
    this.nombreTroc.setText(String.valueOf(nombreTroc));
}
    
    
    private void chargerDonnees() {
        
        List<Troc> listeTroc = ts.readAll();
        trocs = FXCollections.observableArrayList(listeTroc);
        tabelauTroc.setItems(trocs);
        System.out.println(listeTroc);
        this.setNombreTroc(listeTroc.stream().count());
    }

    

    public void setSelectP(String selectP) {
        this.selectP.setText(selectP);
    }

    @FXML
    private void chercherNom(ActionEvent event) {
        String nomAcheteur = chercherAcheteur.getText().trim(); 
        List<Troc> listeTroc = ts.readByUserName(nomAcheteur);
        trocs = FXCollections.observableArrayList(listeTroc);
        tabelauTroc.setItems(trocs);
        System.out.println(listeTroc);
        this.setNombreTroc(listeTroc.stream().count());
    }

    @FXML
    private void refresh(ActionEvent event) {
        List<Troc> listeTroc = ts.readAll();
        trocs = FXCollections.observableArrayList(listeTroc);
        tabelauTroc.setItems(trocs);
        System.out.println(listeTroc);
        this.setNombreTroc(listeTroc.stream().count());
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

}
