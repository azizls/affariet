/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Evenement;
import com.mycompany.services.ServiceEvenement;

/**
 *
 * @author ASUS
 */
public class ModifierEvenementForm  extends BaseForm {
    
    Form current;
    public ModifierEvenementForm(Resources res , Evenement e) {
        
        super("Newsfeed",BoxLayout.y());
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Evenement");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
       
        TextField n = new TextField(e.getNomEvent() , "Nom de l evenement" , 20 , TextField.ANY);
        TextField emp = new TextField(String.valueOf(e.getEmplacement()) , "Emplacement" , 20 , TextField.ANY);
        TextField d = new TextField(String.valueOf(e.getDescriptionEvent()) , "Description" , 20 , TextField.ANY);
        TextField p = new TextField(String.valueOf(e.getPrix()) , "Prix" , 20 , TextField.ANY);
        TextField a = new TextField(String.valueOf(e.getAffiche()) , "Affiche" , 20 , TextField.ANY);
        TextField nbr = new TextField(String.valueOf(e.getNbrMax()) , "Nombre maximal " , 20 , TextField.ANY);
        
        
 
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
        ComboBox etatCombo = new ComboBox();
        
        etatCombo.addItem("Non Traiter");
        
        etatCombo.addItem("Traiter");
        
     
        
        
        
        
        
        
        n.setUIID("NewsTopLine");
        emp.setUIID("NewsTopLine");
        d.setUIID("NewsTopLine");
        p.setUIID("NewsTopLine");
        a.setUIID("NewsTopLine");
        nbr.setUIID("NewsTopLine");
        
      
        
        n.setSingleLineTextArea(true);
        emp.setSingleLineTextArea(true);
        d.setSingleLineTextArea(true);
        p.setSingleLineTextArea(true);
        a.setSingleLineTextArea(true);
        nbr.setSingleLineTextArea(true);
        
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           
           e.setNomEvent(n.getText());
           e.setEmplacement(emp.getText());
           e.setDescriptionEvent(d.getText());
           e.setPrix(Integer.parseInt(p.getText()));
           e.setAffiche(a.getText());
           e.setNbrMax(Integer.parseInt(nbr.getText()));
          
           
           
          
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceEvenement.getInstance().modifierEvenement(e)) { // if true
           new ListEvenementForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(ev -> {
           new ListEvenementForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                
                new FloatingHint(n),
                createLineSeparator(),
                new FloatingHint(emp),
                createLineSeparator(),
                new FloatingHint(d),
                createLineSeparator(),
                new FloatingHint(p),
                createLineSeparator(),
                new FloatingHint(a),
                createLineSeparator(),
                new FloatingHint(nbr),
                createLineSeparator(),
               
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }

    
}
