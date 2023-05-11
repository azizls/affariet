/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import javafx.fxml.FXMLLoader;
import entity.reservation;
import entity.user;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import service.UserService;
import service.reservationService;

/**
 * FXML Controller class
 *
 * @author zakar
 */
public class FrontResController implements Initializable {
    
    static int id_res_static=0 ;
    private int taille_res=0 ;
    private reservationService ress =new reservationService();
    private int indiceres=0 ;
UserService us =new UserService();
    user CurrentUser = us.readById(Affariet.userID);    
    @FXML
    private ScrollPane scrollpane_Salle;
    @FXML
    private HBox hbox_salle;
    private Button btn_back;
    @FXML
    private ImageView Image_user;
    @FXML
    private Button afficherListeBtn;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        

        List<reservation> lsr = ress.readAll();
        taille_res=lsr.size();
        System.out.println(taille_res);
        Node[] nodes_res = new Node[taille_res];
        scrollpane_Salle.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        for (indiceres =0; indiceres<taille_res ; indiceres++){
            if (lsr.get(indiceres).getId_user1().getId_user()==CurrentUser.getId_user()){
                    id_res_static=lsr.get(indiceres).getId_res();
                
            try {
                nodes_res[indiceres] = FXMLLoader.load(getClass().getResource("mesReservations.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FrontResController.class.getName()).log(Level.SEVERE, null, ex);
            }
                hbox_salle.getChildren().add(nodes_res[indiceres]);
                  
               }
        
        }
        
        
        
        
    }    

    private void handleBackButtonAction(ActionEvent event) throws IOException {
        // Get the scene and stage from the current button event
    FXMLLoader loader = new FXMLLoader(getClass().getResource("frontcl.fxml"));
        Parent root = loader.load();
        
        Scene scene = btn_back.getScene();
        scene.setRoot(root);

    }

    @FXML
    private void afficherListe(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontEv.fxml"));
        Parent root = loader.load();
        
        Scene scene = afficherListeBtn.getScene();
        scene.setRoot(root);
    }
    
}
