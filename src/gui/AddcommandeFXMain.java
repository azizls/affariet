/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Transfer;
import com.stripe.model.Card;

import java.util.HashMap;
import java.util.Map;
import entity.Categorie;
import entity.Commande;
import entity.Produit;
import entity.user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import service.CommandeService;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.stripe.model.Card;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import java.util.ArrayList;
import java.util.List;
import service.UserService;
/**
 *
 * @author jawhe
 */
public class AddcommandeFXMain implements Initializable  {
    private Produit pr;
    private int id;
    private Commande cc;
     
   
    @FXML
    private Label produitCfx;
    @FXML
    private ChoiceBox<String>  methodeFX;
    @FXML
    private Button commandeFX;
    @FXML
    private Button proposeTroc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterCommande(ActionEvent event) throws IOException {
         Categorie c=new Categorie(1,"oo");
      Produit p=new Produit(1,"chaise" ," jaune", 1000,"C:\\Users\\lando\\Documents\\NetBeansProjects\\affariet\\image\\95;ca.png", c);
      user u=new user();
      UserService us=new UserService();
     u= us.readById(Affariet.userID);
      Commande p1=new Commande( p,u);
       CommandeService ps=new CommandeService();
       
        
        
        
       
        
        id=ps.insertretrieveid(p1);
        System.out.println(id);
       


        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailCommandeFXML.fxml"));

            Parent root = loader.load();

            // Create a new Scene object for the new page
          DetailCommandeController dc=loader.getController();
        dc.setid(id);
        produitCfx.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }

    @FXML
    private void afficherTroc(ActionEvent event) {
         UserService pst=new UserService();
          user user= new user();
          user=pst.readById(Affariet.userID);
          System.out.println(user);
         Categorie c=new Categorie(7,"Itlr");
      Produit p=new Produit(1,"kd" ," krlr", 265,"C:\\users\\jawhe\\Desktop\\test.java\\affariet\\src\\image\\SL.jpg", c,user);
      user u=new user(1);
      
           try {
           FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddtrocFXML.fxml"));

            Parent root = loader1.load();

            // Create a new Scene object for the new page
          addTrocController dc=loader1.getController();
        dc.setid(p);
        produitCfx.getScene().setRoot(root);
         } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    
}

