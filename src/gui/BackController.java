/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
//import Interfaces.InterfaceCrud;

import Interfaces.InterfaceCrud;
import entity.reclamation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import service.ReclamationService;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import service.IService;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class BackController implements Initializable {
//ReclamationService rs=new ReclamationService();
InterfaceCrud rs=new ReclamationService() {};

reclamation r;
   private ObservableList<reclamation> reclamations;
    private Label Label;

    @FXML
    private Button btnDelete;

    private ComboBox<String> combobox;
    
 private  ObservableList<reclamation>masterdata4;
 private  ObservableList<String>masterdata3;


  
    @FXML
    private TableColumn<reclamation, Date> id_date;

    @FXML
    private TableColumn<reclamation, String> id_decription;

    @FXML
    private TableColumn<reclamation, Integer> id_id;

    @FXML
    private TableView<reclamation> id_list;

    @FXML
    private TableColumn<reclamation, String> id_type;
    @FXML
    private TableColumn<reclamation, String> id_etat;
    @FXML
    private ComboBox<String> comboEtat;
    private String[] typeetat = {"traité", "non traité", "en cours"};
    @FXML
    private Button id_modifier;
    @FXML
    private Button btn_mail;
 
  
    

 
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
       /* comboEtat.getItems().addAll(typeetat);
        id_id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));    
        id_date.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        id_decription.setCellValueFactory(new PropertyValueFactory<>("description"));
        id_type.setCellValueFactory(new PropertyValueFactory<>("type_reclamation"));
        id_etat.setCellValueFactory(new PropertyValueFactory<>("etat_reclamation"));
         
        
        loading ();
       */
      
         
        masterdata3 = FXCollections.observableArrayList(); 
          masterdata4 = FXCollections.observableArrayList(); 
          
           masterdata3.add("traité");
            masterdata3.add("non traité"); 
            masterdata3.add("en cours");
          
          
      comboEtat.getItems().addAll(typeetat);

        id_id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        id_date.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        id_decription.setCellValueFactory(new PropertyValueFactory<>("description"));
        id_type.setCellValueFactory(new PropertyValueFactory<>("type_reclamation"));
        id_etat.setCellValueFactory(new PropertyValueFactory<>("etat_reclamation"));
              
        
        
        
         id_etat.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), masterdata3));
            id_etat. setOnEditCommit (new EventHandler<TableColumn.CellEditEvent<reclamation,String>>(){
  
          @Override
    public void handle(TableColumn.CellEditEvent<reclamation, String> event) {
        // Get the selected reclamation object
        reclamation r = event.getTableView().getItems().get(event.getTablePosition().getRow());
        
        // Update the etat value with the new value from the ComboBoxTableCell
        r.setEtat_reclamation(event.getNewValue());
        
        // Save the updated reclamation object in the database
        rs.update(r);
    }
            });
              
            id_list.setEditable(true);
              
            
              
 /*
        id_etat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<reclamation, String>, ObservableValue<String>>() {
            
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<reclamation, String> param) {
                reclamation r = param.getValue();
                String etat = r.getEtat_reclamation();
                ComboBox<String> combo = new ComboBox<>();
                combo.getItems().addAll(typeetat);
                combo.setValue(etat);
                combo.setOnAction((event) -> {
                    String newEtat = combo.getSelectionModel().getSelectedItem();
                    r.setEtat_reclamation(newEtat);
                    rs.update(r);
                });
                return new SimpleStringProperty(etat);
            }
        });
*/
      
 
 loading();
       
    
}
        
    

   
   private void loading (){
    List<reclamation> lr= rs.readAll();
    reclamations=FXCollections.observableArrayList(lr);
    id_list.setItems(reclamations);
}

   /* private void selectCombo(ActionEvent event) {
     
     String etat = comboEtat.getSelectionModel().getSelectedItem();
        Label.setText(etat);
    }
    */
    /* @FXML
    void afficher_garage(ActionEvent event) {
      ObservableList<reclamation> r=FXCollections.observableArrayList(rs.readAll());
       id_list.setItems(r);*/
    
    
     /* ObservableList<reclamation> garages = FXCollections.observableArrayList(rs.readAll());
    
    // Configure les colonnes de la TableView
    date.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    type.setCellValueFactory(new PropertyValueFactory<>("type"));
    
    // Ajoute les colonnes à la TableView
   tablereclamation.getColumns().addAll(date, description, type);
    
    // Initialise la TableView avec les données de la liste de garages
     tablereclamation.setItems(garages);
      */
      
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    void fdelete(ActionEvent event) {
        reclamation r=id_list.getSelectionModel().getSelectedItem();
        if(r!=null) {
            rs.delete(r.getId_reclamation());
            reclamations.remove(r);
        }
          //int selectedId=  id_list.getSelectionModel().getSelectedItem().getId_reclamation();
        //rs.delete(selectedId);
     //  afficher_garage(event);

    }


    @FXML
    private void SelectedItems(javafx.scene.input.MouseEvent event) {
        comboEtat.getSelectionModel().clearSelection();
        comboEtat.setValue(id_list.getSelectionModel().getSelectedItem().getEtat_reclamation());
    }

    
     void getGarage(reclamation r){
   
          id_etat.setText(r.getEtat_reclamation());
            
}
    
    
    
    @FXML
    private void modifier_garage(ActionEvent event) {
        
        r.setEtat_reclamation(id_etat.getText());
        rs.update(r);
        
        
    }




    @FXML
    private void envoyer_mail(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SendEmail.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
        
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

    @FXML
    private void evennement_back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("addev.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

}
    
    
//@FXML
//private void selectItem(MouseEvent event) {
//    reclamation selectedReclamation = tablereclamation.getSelectionModel().getSelectedItem();
//    if (selectedReclamation != null) {
//        Label.setText(selectedReclamation.getDescription());
//        combobox.getSelectionModel().select(selectedReclamation.getType_reclamation());
//    }
//}


