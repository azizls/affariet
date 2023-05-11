/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Commande;
import com.mycompany.services.ServiceCommande;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;


/**
 *
 * @author ASUS
 */
public class AddCommandeForm extends BaseForm {
    private int id_produit=3;
    Form current;
    private static final String STRIPE_SECRET_KEY = "sk_test_51MekuZBuy83JycrRFwccFuthQTjhGfDUOGsgImRUeUxYnbUNLkNivEA10Wm6YI1b4LfmmFc37TCZs6jkv4ERDckf00mCAL6jCe";
    private static final String STRIPE_PUBLISHABLE_KEY = "pk_test_51MekuZBuy83JycrRRbGdfGMctgPbQ4NmTGHxB6Kgc0jE6yXsjHrjrwtcse1V1v2Ipa5WhnFzPb7k6mDNtEMLkJCo00GEA9e1ID";
    public AddCommandeForm(Resources res){
        
        
        super("Newsfeed", BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Commande");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);
        
        
         tb.addSearchCommand(e ->  {
            
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
       addTab(swipe,s1, res.getImage("nadji.jpg"),"","",res);
        
        //
        
         swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Dashboard", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Reservations", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Evenements", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
               
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        TextField searchField = new TextField();
        searchField.setHint("Search...");
        
        searchField.addDataChangeListener((i, ii) -> {
        String searchTerm = searchField.getText();
    // Call method to filter or search through data using searchTerm
    // Display filtered or search results
        });
        
        
       
        
        TextField card = new TextField("" , "card ");
        card.setUIID("TextFieldBlack");
        addStringValue("card", card);
        
        
        
        TextField Month = new TextField("" , "exp_Month!");
        Month.setUIID("TextFieldBlack");
        addStringValue("Month", Month);
        
        TextField year = new TextField("" , "exp_year");
        year.setUIID("TextFieldBlack");
        addStringValue("year", year);
        
        TextField cvc = new TextField("" , "cvc!");
        cvc.setUIID("TextFieldBlack");
        addStringValue("cvc",cvc);
        
        
        
        
     
        
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        Button btnAnnuler = new Button("Annuler");
        addStringValue("", btnAnnuler);
        btnAnnuler.addActionListener(e -> {
           new AddCommandeForm(res).show();
       });
        
         btnAjouter.addActionListener((e) -> {
            
            
            try {
                
                if(card.getText().equals("")|| Month.getText().equals("")|| year.getText().equals("")|| cvc.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();;
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    Stripe.apiKey = STRIPE_SECRET_KEY;
        
        
       

    
            // Create a customer object for the user who is paying
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("email", "jawherjaziri2021@gmail.com");
            Customer payer = Customer.create(customerParams);
            
            Map<String, Object> retrieveParams = new HashMap<String, Object>();
		ArrayList<String> expandList = new ArrayList<String>();
		expandList.add("sources");
		retrieveParams.put("expand", expandList);
		Customer customer = Customer.retrieve(payer.getId(), retrieveParams, null); //add customer id here : it will start with cus_
		
		Map<String, Object> cardParam = new HashMap<String, Object>(); //add card details
		cardParam.put("number", card.getText() );
		cardParam.put("exp_month", Month.getText() );
		cardParam.put("exp_year", year.getText() );
		cardParam.put("cvc", cvc.getText() );

		Map<String, Object> tokenParam = new HashMap<String, Object>();
		tokenParam.put("card", cardParam);

		Token token = Token.create(tokenParam); // create a token

		Map<String, Object> source = new HashMap<String, Object>();
		source.put("source", token.getId()); //add token as source

		Card card1 = (Card)customer.getSources().create(source); // add the customer details to which card is need to link
		String cardDetails = card1.toJson();
		System.out.println("Card Details : " + cardDetails);
		customer = Customer.retrieve(payer.getId());//change the customer id or use to get customer by id.
		System.out.println("After adding card, customer details : " + customer);
                
                
                
//                PaymentMethod paymentMethod = PaymentMethod.create(cardParam);
                
                
                
                
                
                
                
                
            System.out.println(customer.getId());       
 //Use the payment method to make a charge
    Map<String, Object> chargeParams = new HashMap<String, Object>();
    chargeParams.put("amount", 2000);
    chargeParams.put("currency", "usd");
    //chargeParams.put("description", "Example charge");
     //chargeParams.put("source", token.getId());
    chargeParams.put("customer", customer.getId());
   // ID of destination account
            Charge charge = Charge.create(chargeParams);
            

 Commande t = new Commande();
                        t.setId_produit(id_produit);
                        t.setChargeId(charge.getId());
                        System.out.println(t.getChargeId());
                        if( ServiceCommande.getInstance().addCommande(t))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
            System.out.println("Payment successful!");
             iDialog.dispose(); 
        } 
                           
                       
                    
                    
                } catch (StripeException ex) {
                //Logger.getLogger(AddCommandeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
           
                    
                   
                    
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    status.setMessage("commande ajouté avec succès");
                    status.setExpires(4000);  
                    status.show();
                    System.out.println("hallllllllllllllllllllllllllllllo");
                    
                    LocalNotification n = new LocalNotification();
                    n.setId("demo-notification");
                    n.setAlertBody("It's time to take a break and look at me");
                    n.setAlertTitle("Break Time!");
                    
                    Display.getInstance().scheduleLocalNotification(
                            n,
                            System.currentTimeMillis() + 10 * 1000, // fire date/time
                            LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency
                    );//na7io loading ba3d ma3mlna ajout
                    
                    new ListCommandes(res).show();
                    
                    
                    refreshTheme();//Actualisation
                    
                    
                            
             
         
         });
        


        
         
                 }
         
         
        
        
        

    

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
    
     private void addTab(Tabs swipe, Label spacer , Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        
        
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","ImageOverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                        )
                    )
                );
        
        swipe.addTab("",res.getImage("nadji.jpg"), page1);
        
        
        
        
    }
    

    public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }
    
    
    
    
   
 public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
       
}
