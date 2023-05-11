/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.user;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author turki
 */
public class AdminController implements Initializable {

    @FXML
    private TableColumn id_colone;
    @FXML
    private TableColumn nom_colone;
    @FXML
    private TableColumn prenom_colone;
    @FXML
    private TableColumn age_colone;
    @FXML
    private TableColumn email_colone;
    @FXML
    private TableColumn mdp_colone;
    @FXML
    private TableColumn num_colone;
    @FXML
    private TableColumn adresse_colone;
   // private TableColumn image_colone;
    @FXML
    private TableColumn id_role_colone;

    private UserService userService = new UserService();
    @FXML
    private TableView <user> afficher;
    
    
    @FXML
    private Button supprimeruser;
    @FXML
    private TextField text_recherche;
    @FXML
    private TextField text_rechercher2;
    @FXML
    private Button btn_produit;
    @FXML
    private Button btn_commande;
    @FXML
    private Button btn_event;
    @FXML
    private Button btn_blog;
    @FXML
    private Button btn_reclamation;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_colone.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        nom_colone.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_colone.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        age_colone.setCellValueFactory(new PropertyValueFactory<>("age"));
        email_colone.setCellValueFactory(new PropertyValueFactory<>("email"));
        mdp_colone.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        num_colone.setCellValueFactory(new PropertyValueFactory<>("numero"));
        adresse_colone.setCellValueFactory(new PropertyValueFactory<>("adresse"));
       // image_colone.setCellValueFactory(new PropertyValueFactory<>("image"));
        id_role_colone.setCellValueFactory(new PropertyValueFactory<>("id_role"));
        
        System.out.println(Affariet.userID);
       
        
        // récupère les données des utilisateurs depuis la base de données
        List<user> userList = userService.readAll();
        
        // affiche les données dans le tableau
            afficher.getItems().setAll(userList);
            
            int id_role = Affariet.id_role;
            System.out.println(id_role);
        

    }   

    @FXML
    private void supprimeruser(ActionEvent event) {
       
        user selectedUser = afficher.getSelectionModel().getSelectedItem();
       
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No user selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user in the table.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected user?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
           
            userService.delete1(selectedUser.getId_user());
             List<user> userList = userService.readAll();
        
        // affiche les données dans le tableau
        afficher.getItems().setAll(userList);
        }}

    @FXML
    private void rechercher(KeyEvent event) {
         if (event.getCode() == KeyCode.ENTER) {
        int userId = Integer.parseInt(text_recherche.getText());
        user searchedUser = userService.readById(userId);
        if (searchedUser != null) {
            afficher.getItems().setAll(searchedUser);
        }else  {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No user found");
            alert.setHeaderText(null);
            alert.setContentText("No user found with the given ID.");
            alert.showAndWait();
        }
    }
        
    }

    @FXML
    private void rechercher2(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
        String email = text_rechercher2.getText();
        user searchedUser = userService.readByEmail(email);
        if (searchedUser != null) {
            afficher.getItems().setAll(searchedUser);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No user found");
            alert.setHeaderText(null);
            alert.setContentText("No user found with the given e-mail.");
            alert.showAndWait();
        }
    }
}

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
    private void event_back(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("addev.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }


    }
 
    
