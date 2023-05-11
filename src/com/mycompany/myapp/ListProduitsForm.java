/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import com.mycompany.myapp.AddCommandeForm;
import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceProduit;
/**
 *
 * @author ASUS
 */
public class ListProduitsForm extends BaseForm{

    Form current;
    public ListProduitsForm(Resources res ) {
        
        super("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("List des Produits");
        getContentPane().setScrollVisible(false);
        
        
        
        
        
        
        
        
        
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : this.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : this.getContentPane()) {
                    if (cmp instanceof MultiButton) {
                        MultiButton mb = (MultiButton) cmp;
                        String line1 = mb.getTextLine1();
                        String line2 = mb.getTextLine2();
                        mb.setUIIDLine1("libC");
                        mb.setUIIDLine2("btn");
                        boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                                || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                        mb.setHidden(!show);
                        mb.setVisible(show);
                    }
                }
                this.getContentPane().animateLayout(150);
            }
        }, 4);
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("nadji.jpg"),"","",res);
          
    
      
        
      
        
        
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
        RadioButton partage = RadioButton.createToggle("commande", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label( );


        mesListes.addActionListener((e) -> {
               
        
        
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
        
        
        
        
        
        
      
        //Appel affichage methode
        ArrayList<Produit>list = ServiceProduit.getInstance().getAllProduits();
        
        for(Produit rec : list ) {
             String urlImage ="nadji.jpg";//image statique pour le moment ba3d taw fi  videos jayin nwarikom image 
            
             Image placeHolder = Image.createImage(120, 90);
             EncodedImage enc =  EncodedImage.createFromImage(placeHolder,false);
             URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
             
                addButton(urlim,rec,res);
        
                ScaleImageLabel image = new ScaleImageLabel(urlim);
                
                Container containerImg = new Container();
                
                image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                
                
                     AddCommandeForm addCommandeForm = new AddCommandeForm(res);
addCommandeForm.setId_produit(rec.getId_produit());

 Button btnAjouter = new Button("commander");
        btnAjouter.addActionListener((c) -> { new AddCommandeForm(res).show();
        });
        add(btnAjouter);

        }
        
        

        
        
        
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

    private void addButton(Image img,Produit rec , Resources res) {
        
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        
        
        
        
       
        Label nTxt = new Label("Nom de Produit : "+rec.getNom_produit(),"NewsTopLine2" );
        Label empTxt = new Label("Prix : "+Float.toString(rec.getPrix_produit()),"NewsTopLine2" );
        
       
        
        
        
        createLineSeparator();
        
       ;
       
        
        //supprimer button
  


        
        
         
        
        
        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
                
                
                
                BoxLayout.encloseX(nTxt),
                BoxLayout.encloseX(empTxt)
                
               
                ));
        
        
        
        
        add(cnt);
        
        
    }
    
    
    private Container createEntry(Resources res , boolean selected, String startTime, String endTime, String location, String title, String attendance, String... images) {
        Component time = new Label(startTime, "CalendarHourUnselected");
        if(selected) {
            time.setUIID("CalendarHourSelected");
        }
        
        Container circleBox = BoxLayout.encloseY(new Label(res.getImage("label_round-selected.png")),
                new Label("-", "OrangeLine"),
                new Label("-", "OrangeLine")
        );
        
        Container cnt = new Container(BoxLayout.x());
        for(String att : images) {
            cnt.add(res.getImage(att));
        }
        Container mainContent = BoxLayout.encloseY(
                BoxLayout.encloseX(
                        new Label(title, "SmallLabel"), 
                        new Label("-", "SmallThinLabel"), 
                        new Label(startTime, "SmallThinLabel"), 
                        new Label("-", "SmallThinLabel"),
                        new Label(endTime, "SmallThinLabel")),
                new Label(attendance, "TinyThinLabel"),
                cnt
        );
        
        Label redLabel = new Label("", "RedLabelRight");
        FontImage.setMaterialIcon(redLabel, FontImage.MATERIAL_LOCATION_ON);
        Container loc = BoxLayout.encloseY(
                redLabel,
                new Label("Location:", "TinyThinLabelRight"),
                new Label(location, "TinyBoldLabel")
        );
        
        mainContent= BorderLayout.center(mainContent).
                add(BorderLayout.WEST, circleBox);
        
        return BorderLayout.center(mainContent).
                add(BorderLayout.WEST, FlowLayout.encloseCenter(time)).
                add(BorderLayout.EAST, loc);
    }
    





    
    

    @Override
    protected void initGlobalToolbar() {
        setToolbar(new Toolbar(true));
    }
    
   
}
