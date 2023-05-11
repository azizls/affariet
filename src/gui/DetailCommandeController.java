/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.model.Transfer;
import entity.Categorie;
import entity.Commande;
import entity.Produit;
import entity.user;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.CommandeService;
import service.UserService;
/**
 *
 * @author jawhe
 */
public class DetailCommandeController implements Initializable {
    private static final String STRIPE_SECRET_KEY = "sk_test_51MekuZBuy83JycrRFwccFuthQTjhGfDUOGsgImRUeUxYnbUNLkNivEA10Wm6YI1b4LfmmFc37TCZs6jkv4ERDckf00mCAL6jCe";
    private static final String STRIPE_PUBLISHABLE_KEY = "pk_test_51MekuZBuy83JycrRRbGdfGMctgPbQ4NmTGHxB6Kgc0jE6yXsjHrjrwtcse1V1v2Ipa5WhnFzPb7k6mDNtEMLkJCo00GEA9e1ID";
    private int id=0;
    private Produit p;
    @FXML
    private Label       detailcommandefx;
    @FXML
    private Label nomProduitFX;
    @FXML
    private Label prixfx;
   @FXML
    private Button annulerfx;
    @FXML
    private AnchorPane deletefx;
    @FXML
    private Button payementfx;
    @FXML
    private TextField numberfx;
    @FXML
    private TextField exp_month;
    @FXML
    private TextField yearfx;
    @FXML
    private TextField cvcfx;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       
       load();
    }    

    @FXML
    private void annulerCommande(ActionEvent event) throws IOException {
         Categorie c=new Categorie(7,"Itlr");
      Produit p=new Produit(1,"kd" ," krlr", 265,"C:\\users\\jawhe\\Desktop\\test.java\\affariet\\src\\image\\SL.jpg", c);
      user u=new user(1);
      Commande p1=new Commande();
       CommandeService ps=new CommandeService();
       p1=ps.readById(u.getId_user());
        ps.delete(id);
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontProduit.fxml"));

            Parent root = loader.load();

            // Create a new Scene object for the new page
            Scene scene = new Scene(root);

            // Get the Stage object for the current page
            Stage stage = (Stage) annulerfx.getScene().getWindow();

            // Set the new Scene on the Stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setLabn(String labn) {
        this.detailcommandefx.setText(labn);
    }
    
     public void setid(int idd) {
        this.id=idd;
    }
     public void load(){
          if(id!=0){
        Commande c=new Commande();
        CommandeService cs=new CommandeService();
       c=cs.readById(id);
           System.out.println(c);
       
       nomProduitFX.setText(c.getId_produit().getNom_produit());
       prixfx.setText(String.format("%.2f", c.getId_produit().getPrix_produit()));
          }
     }

    private void afficher(ActionEvent event) {
          if(id!=0){
        Commande c=new Commande();
        CommandeService cs=new CommandeService();
       c=cs.readById(id);
//           System.out.println(c);
       
       nomProduitFX.setText(c.getId_produit().getNom_produit());
       prixfx.setText(String.format("%.2f", c.getId_produit().getPrix_produit()));
    }
    
}

    @FXML
    private void payerfx(ActionEvent event) throws IOException {
         Stripe.apiKey = STRIPE_SECRET_KEY;
        
        System.out.println(id);
       UserService us=new UserService();
       user u=new user();
       u=us.readById(Affariet.userID);
        try {
            // Create a customer object for the user who is paying
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email", u.getEmail());
            Customer payer = Customer.create(customerParams);
            
            Map<String, Object> retrieveParams = new HashMap<String, Object>();
		List<String> expandList = new ArrayList<String>();
		expandList.add("sources");
		retrieveParams.put("expand", expandList);
		Customer customer = Customer.retrieve(payer.getId(), retrieveParams, null); //add customer id here : it will start with cus_
		
		Map<String, Object> cardParam = new HashMap<String, Object>(); //add card details
		cardParam.put("number", numberfx.getText());
		cardParam.put("exp_month", exp_month.getText());
		cardParam.put("exp_year", yearfx.getText());
		cardParam.put("cvc", cvcfx.getText());

		Map<String, Object> tokenParam = new HashMap<String, Object>();
		tokenParam.put("card", cardParam);

		Token token = Token.create(tokenParam); // create a token

		Map<String, Object> source = new HashMap<String, Object>();
		source.put("source", token.getId()); //add token as source

		Card card = (Card)customer.getSources().create(source); // add the customer details to which card is need to link
		String cardDetails = card.toJson();
		System.out.println("Card Details : " + cardDetails);
		customer = Customer.retrieve(payer.getId());//change the customer id or use to get customer by id.
		System.out.println("After adding card, customer details : " + customer);
                
                
                
//                PaymentMethod paymentMethod = PaymentMethod.create(cardParam);
                
                
                
                
                
                
                
                
            System.out.println(customer.getId());       
 //Use the payment method to make a charge
    Map<String, Object> chargeParams = new HashMap<String, Object>();
    chargeParams.put("amount", 1000);
    chargeParams.put("currency", "usd");
    //chargeParams.put("description", "Example charge");
     //chargeParams.put("source", token.getId());
    chargeParams.put("customer", customer.getId());
   // ID of destination account
            Charge charge = Charge.create(chargeParams);
            
//          Map<String, Object> paymentIntentParams = new HashMap<String, Object>();
//                paymentIntentParams.put("amount", 1000);
//            paymentIntentParams.put("currency", "usd");
//                paymentIntentParams.put("payment_method", paymentMethod.getId());
//            paymentIntentParams.put("confirm", true);
//
//PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);
            
//            Map<String, Object> accountParams = new HashMap<String, Object>();
//accountParams.put("type", "custom");
//accountParams.put("country", "US");
//accountParams.put("email", "jskander@gmail.com");
//
//Account destinationAccount = Account.create(accountParams);
            
            // Create a transfer object to transfer the funds to the user who is receiving the payment
            
//    Map<String, Object> params = new HashMap<>();
//        params.put("amount", 900);
//        params.put("currency", "usd");
//        params.put(
//        "destination",
//        "acct_1MelEKBFWJIV10Uk"
//);
//params.put("transfer_group", "ORDER_95");
//
//Transfer transfer = Transfer.create(params);
              
     
      Commande p1=new Commande( p,u);
       CommandeService ps=new CommandeService();
       
        p1.setCharge_id(charge.getId());
        
        
       int id1=0;
        
        id1=ps.insertretrieveid(p1);
        System.out.println(id1);
       

            System.out.println("Payment successful!");
        } catch (StripeException e) {
            System.out.println("Error: " + e.getMessage());
            
        }
         FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontProduit.fxml"));

            Parent root = loader.load();

            // Create a new Scene object for the new page
            Scene scene = new Scene(root);

            // Get the Stage object for the current page
            Stage stage = (Stage) annulerfx.getScene().getWindow();

            // Set the new Scene on the Stage
            stage.setScene(scene);
            stage.show();
    }

    public void setP(Produit p) {
        this.p = p;
    }

}