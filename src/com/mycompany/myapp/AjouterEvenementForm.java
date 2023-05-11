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
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
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
import com.mycompany.entities.Evenement;
import com.mycompany.services.ServiceEvenement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class AjouterEvenementForm extends BaseForm {
    
    Form current;
    public AjouterEvenementForm(Resources res){
        
        
        super("Newsfeed", BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter evenement");
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
        
        
       
        
        TextField nom = new TextField("" , "entrez le nom de l evenement !");
        nom.setUIID("TextFieldBlack");
        addStringValue("Nom", nom);
        
        
        
        TextField emplacement = new TextField("" , "entrez emplacement!");
        emplacement.setUIID("TextFieldBlack");
        addStringValue("Emplacement", emplacement);
        
        TextField description = new TextField("" , "entrez description!");
        description.setUIID("TextFieldBlack");
        addStringValue("Description", description);
        
        TextField prix = new TextField("" , "entrez prix!");
        prix.setUIID("TextFieldBlack");
        addStringValue("prix", prix);
        
        TextField affiche = new TextField("" , "entrez affiche!");
        affiche.setUIID("TextFieldBlack");
        addStringValue("Affiche", affiche);
        
        TextField nbr_max = new TextField("" , "entrez le nombre maximal!");
        nbr_max.setUIID("TextFieldBlack");
        addStringValue("Nombre maximal", nbr_max);
        
     
        
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        Button btnAnnuler = new Button("Annuler");
        addStringValue("", btnAnnuler);
        btnAnnuler.addActionListener(e -> {
           new ListEvenementForm(res).show();
       });
        
         btnAjouter.addActionListener((e) -> {
            
            
            try {
                
                if(nom.getText().equals("")|| emplacement.getText().equals("")|| description.getText().equals("")|| prix.getText().equals("")|| affiche.getText().equals("")|| nbr_max.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();;
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    
                    Evenement ev = new Evenement(
                                  String.valueOf(nom.getText()).toString(),
                                  String.valueOf(emplacement.getText()).toString(), 
                                  String.valueOf(description.getText()).toString(),
                                  Integer.parseInt(prix.getText()),
                                  String.valueOf(affiche.getText()).toString(),
                                  Integer.parseInt(nbr_max.getText())
                                 
                    );
                    
                    System.out.println("data  evenement == "+ev);
                    
                    
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    ServiceEvenement.getInstance().AjouterEvenement(ev);
                    
                    iDialog.dispose(); 
                    
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    status.setMessage("Evenement ajouté avec succès");
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
                    
                    new ListEvenementForm(res).show();
                    
                    
                    refreshTheme();//Actualisation
                    
                    
                            
                }
                
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
            
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
    
    
    
    
   

       
}
