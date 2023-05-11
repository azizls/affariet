/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import javafx.fxml.FXMLLoader;
import entity.evenement;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.evenementService;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class FrontEvController implements Initializable {

    @FXML
    private ScrollPane scrollpane_Salle;
    @FXML
    private HBox hbox_salle;
    
    static int id_ev_static=0 ;
    private int taille_ev ;
    private evenementService evsp = new evenementService();
    private int indiceEv=0 ;
    private Button btn_back;
    @FXML
    private ImageView Image_user;
    @FXML
    private Button afficherListeBtn;
    @FXML
    private Button afficherReservationBtn;
    
    
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        List<evenement> ls = evsp.readAll();
        taille_ev=ls.size();
        Node[] nodes_eve = new Node[taille_ev];
        scrollpane_Salle.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for (indiceEv =0; indiceEv<taille_ev ; indiceEv++){
            try {
                System.out.println("evevev");
                id_ev_static=ls.get(indiceEv).getId_event();
                nodes_eve[indiceEv] = FXMLLoader.load(getClass().getResource("listeEvenements.fxml"));
                hbox_salle.getChildren().add(nodes_eve[indiceEv]);
            } catch (IOException e) {
            }
            
        }
        
        
        
        
    }    

    private void goBack(ActionEvent event) throws IOException {
        // Get the scene and stage from the current button event
    FXMLLoader loader = new FXMLLoader(getClass().getResource("frontcl.fxml"));
        Parent root = loader.load();
        
        Scene scene = btn_back.getScene();
        scene.setRoot(root);
    }

    
    @FXML
    private void afficherReservation(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("frontRes.fxml"));
        Parent root0 = loader.load();

        Scene scene0 = afficherReservationBtn.getScene();
        scene0.setRoot(root0);
        
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
