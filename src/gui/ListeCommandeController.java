/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Commande;
import entity.Troc;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CommandeService;
import service.TrocService;

/**
 * FXML Controller class
 *
 * @author jawhe
 */
public class ListeCommandeController implements Initializable {

    @FXML
    private TableView<Commande> tableCommande;
    @FXML
    private TableColumn<Commande, Integer> idcommandefx;
    private TableColumn<Commande, Integer> id_produitfx;
    
    
    private ObservableList<Commande> commandes;
    private CommandeService cs = new CommandeService();
    @FXML
    private Button suppriemrfx;
    @FXML
    private TableColumn<Commande, String> produitfx;
    @FXML
    private TableColumn<Commande, String> acheteurfx;
    @FXML
private TableColumn<Commande, String> datefx;
    @FXML
    private Button chercherbutonyear;
    @FXML
    private TextField recherYear;
    @FXML
    private Button afficherAll;
    @FXML
    private ComboBox<Integer> monthfx;
   private  List<Integer> optionsList = Arrays.asList(1, 2, 3,4,5,6,7,8,9,10,11,12);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthfx.setItems(FXCollections.observableList(optionsList));
        
         idcommandefx.setCellValueFactory(new PropertyValueFactory<>("id_commande"));
        produitfx.setCellValueFactory(cellData -> {
    Commande commande = cellData.getValue();
    StringProperty produitNomProperty = new SimpleStringProperty(commande.getId_produit().getNom_produit());
    return produitNomProperty;
});
          acheteurfx.setCellValueFactory(cellData -> {
    Commande commande = cellData.getValue();
    StringProperty produitNomProperty = new SimpleStringProperty(commande.getuser().getNom());
    return produitNomProperty;
});
     datefx.setCellValueFactory(cellData -> {
        Commande commande = cellData.getValue();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(commande.getDate_commande());
        StringProperty dateProperty = new SimpleStringProperty(formattedDate);
        return dateProperty;
    });
        
  
  // Display the date in a column table (assuming you are using JavaFX)
  
  
        
        charge();
    }   

    public void setMonthfx(ComboBox<Integer> monthfx) {
        this.monthfx = monthfx;
    }

    
    
    private void charge(){
        
         List<Commande> listeCommande = cs.readAll();
        commandes = FXCollections.observableArrayList(listeCommande);
        tableCommande.setItems(commandes);
        System.out.println(listeCommande);
    }

    @FXML
    private void supprimer(ActionEvent event) {
         Commande t = tableCommande.getSelectionModel().getSelectedItem();
        if (t != null) {
            // Appeler la méthode delete de annonceService pour supprimer l'annonce
            cs.delete(t.getId_commande());
            // Retirer l'annonce de la liste des annonces
            commandes.remove(t);
    }
}

    @FXML
    private void chercherYear(ActionEvent event) {
        if (recherYear.getText().isEmpty()&&monthfx.getValue()!=null){
             Integer selectedValue = monthfx.getValue();
            int intmonth = selectedValue.intValue();
            
        List<Commande> listeCommande = cs.readAllMonth(intmonth);
        commandes = FXCollections.observableArrayList(listeCommande);
        tableCommande.setItems(commandes);
        System.out.println(listeCommande);
        monthfx.setValue(null);
        }
        if(monthfx.getValue()==null&&recherYear.getText()!=null){
             int intValue = Integer.parseInt(recherYear.getText());
        List<Commande> listeCommande = cs.readAllYear(intValue);
        commandes = FXCollections.observableArrayList(listeCommande);
        tableCommande.setItems(commandes);
        System.out.println(listeCommande);
        monthfx.setValue(null);
        }
        if(recherYear.getText()!=null&&monthfx.getValue()!=null){
            Integer selectedValue = monthfx.getValue();
            int intmonth = selectedValue.intValue();
            int intValue = Integer.parseInt(recherYear.getText());
        List<Commande> listeCommande = cs.readAllYearAndMonth(intValue,intmonth);
        commandes = FXCollections.observableArrayList(listeCommande);
        tableCommande.setItems(commandes);
        System.out.println(listeCommande);
        monthfx.setValue(null);
        }
    }

    @FXML
    private void afficherAll(ActionEvent event) {
        charge();
    }
  
}
