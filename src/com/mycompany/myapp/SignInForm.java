/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.Profile;
import com.mycompany.services.ServiceUtilisateur;

/**
 *
 * @author Rayan
 */
public class SignInForm extends BaseForm {

    public SignInForm(Resources res) {
        super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
        add(BorderLayout.NORTH, new Label(res.getImage("Logo.png"), "LogoLabel"));
        
        TextField email = new TextField("", "email", 20, TextField.ANY);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        
        //mp oublié
        Button  mp = new Button("oublier mot de passe?","CenterLabel");
        
        
        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Vous n'avez aucune compte?");
        
        
        
        
        
        
        Container content = BoxLayout.encloseY(
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp),mp
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        
        /*signIn.addActionListener(e -> 
        {
               UserService.getInstance().signin(email, password, res);

           
        });*/
           signIn.addActionListener(e -> 
        { 
             /*if (new ServiceUtilisateur().signin(email, password ,res).equals("failed")) {
       Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK", null);*/
            if (email.getText().length() == 0|| password.getText().length() == 0) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            }
            else if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
    // show an error dialog
    Dialog.show("Error", "Please enter a valid email address.", "OK", null);   
    } 
            
  
             else{
           new ServiceUtilisateur().signin(email, password ,res);
               new ListProduitsForm(res).show();
          //  new AddStudent().show();
           //  }
           
            }});
        
        
        
        //Mp oublie event
        
       mp.addActionListener((e) -> {
           
            new ActivateForm(res).show();
            
            
        });
        
    }


    
}
