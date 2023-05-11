/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class frontController implements Initializable {

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
        
        
    }    
    
    @FXML
    private void afficherListe(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontEv.fxml"));
        Parent root = loader.load();
        
        Scene scene = afficherListeBtn.getScene();
        scene.setRoot(root);
    }

    @FXML
    private void afficherReservation(ActionEvent event) throws IOException {
        
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("frontRes.fxml"));
        Parent root0 = loader.load();

        Scene scene0 = afficherReservationBtn.getScene();
        scene0.setRoot(root0);
        
    }
    
}
