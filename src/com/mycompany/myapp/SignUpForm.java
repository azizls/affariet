/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUtilisateur;
import java.util.Vector;



/**
 *
 * @author rayan
 */
public class SignUpForm extends BaseForm{
        public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
          TextField username = new TextField("", "Username", 20, TextField.ANY);
/*username.addDataChangeListener(new DataChangedListener() {
    @Override
    public void dataChanged(int type, int index) {
        String newText = username.getText().trim();
        if (newText.isEmpty()) {
            Dialog.show("Error", "Username cannot be empty", "OK", null);
        } else if (!newText.matches("[a-zA-Z0-9]+")) {
            Dialog.show("Error", "Username can only contain letters and numbers", "OK", null);
        }
    }
});*/
        TextField prenom = new TextField("", "Prenom", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        
        TextField mdp = new TextField("", "Password", 20, TextField.PASSWORD);
        
        TextField numero = new TextField("", "numero", 20, TextField.PASSWORD);
        TextField image = new TextField("", "Pic", 20, TextField.ANY);
        /*select pic*/
        Label filename = new Label("");
       
        Style filenamestyles = filename.getUnselectedStyle();
        filenamestyles.setMarginTop(1);
        
         Font materialFont = FontImage.getMaterialDesignFont();
        materialFont = materialFont.derive(60, Font.STYLE_PLAIN);
         Button btn_attach = new Button("Image");
        btn_attach.setUIID("addImgBtn");
        
        /*add imagefile*/
        Style sIcon = new Style(0xffffff, 0x000000, materialFont, (byte) 0l);
        btn_attach.setIcon(FontImage.create("\ue412", sIcon));
        btn_attach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                try {
                    filename.setText(filePath);
                } catch (Exception e) {
                    filename.setText("");

                }
                System.out.println(filename.getText());
                
                

            }

           
     
        });

           //Role 
        //Vector 3ibara ala array 7atit fiha roles ta3na ba3d nzidouhom lel comboBox
       
        
       String ima;
        ima = filename.getText();
        
        
        username.setSingleLineTextArea(false);
        prenom.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        mdp.setSingleLineTextArea(false);
        numero.setSingleLineTextArea(false);
        image.setSingleLineTextArea(false);
        Button next = new Button("SignUp");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> new SignInForm(res).show());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content;
        content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(prenom),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(mdp),
                createLineSeparator(),
                new FloatingHint(numero),
                createLineSeparator(),
             //   roles,//sinon y7otich role fi form ta3 signup
                filename,
                createLineSeparator(),
                btn_attach
                

                
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((ActionEvent e) -> {
            
           if (username.getText().length() == 0 || prenom.getText().length() == 0 || email.getText().length() == 0|| mdp.getText().length() == 0) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            }

              else if (containsDigit(username.getText().toString())){
        Dialog.show("Erreur", "Le champ nom ne doit pas contenir de chiffres.", "OK", null);
       
    }  else if (containsDigit(prenom.getText().toString())){
        Dialog.show("Erreur", "Le champ prenom ne doit pas contenir de chiffres.", "OK", null);
    }if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
    // show an error dialog
    Dialog.show("Error", "Please enter a valid email address.", "OK", null);

       
    }    else if (mdp.getText().length() < 6 || mdp.getText().length() < 6 ){
        Dialog.show("Erreur", "Password doit contenir aux moins 6 carateres.", "OK", null);
       
    } else if (!mdp.getText().toString().equals(mdp.getText().toString()) ){
        Dialog.show("Erreur", "Password confirmation incorrect", "OK", null);
       
    } 
            else {
                try {
                      ServiceUtilisateur.getInstance().signup(username,prenom , email, numero,mdp, res);
            Dialog.show("Success","account is saved","OK",null);
            new SignInForm(res).show();
                    
                } catch (NumberFormatException ev) {
                    Dialog.show("Error", "Invalid input", new Command("OK"));
                }
            }
        });

            
          
   
        }
        
        public static boolean containsDigit(String s){
        //tow new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            return true;
        }
    }
    return false;
    }
public static boolean isValidEmail(String email) {
    String emailRegex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    int emailLength = email.length();
    int regexLength = emailRegex.length();
    int i = 0, j = 0;
    while (i < emailLength && j < regexLength) {
        char emailChar = email.charAt(i);
        char regexChar = emailRegex.charAt(j);
        if (emailChar == regexChar) {
            i++;
            j++;
        } else if (regexChar == '\\') {
            j++;
        } else if (regexChar == '.') {
            i++;
            j++;
        } else {
            return false;
        }
    }
    return (i == emailLength && j == regexLength);
}
    
}
