/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.Preferences;

/**
 *
 * @author rayan
 */
public class SessionManager {
        public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 

    private static int id;
    private static String username;
    private static String email;
    private static String photo;
    private static String nom;
    private static String prenom;
    private static String motdepasse;
        private static String roles;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SessionManager.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SessionManager.username = username;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }

    public static String getPhoto() {
        return photo;
    }

    public static void setPhoto(String photo) {
        SessionManager.photo = photo;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        SessionManager.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        SessionManager.prenom = prenom;
    }

    public static String getMotdepasse() {
        return motdepasse;
    }

    public static void setMotdepasse(String motdepasse) {
        SessionManager.motdepasse = motdepasse;
    }

    public static String getRoles() {
        return roles;
    }

    public static void setRoles(String roles) {
       SessionManager.roles = roles; //To change body of generated methods, choose Tools | Templates.
    }
   
}
