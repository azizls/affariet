/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Evenement {
    
    private int idEvent;
    private String nomEvent;
    private String emplacement;
    private String descriptionEvent;
    private int prix;
    private String affiche;
    private int nbrMax;


    public Evenement(int idEvent, String nomEvent, String emplacement, String descriptionEvent, int prix, String affiche, int nbrMax) {
        this.idEvent = idEvent;
    
        this.nomEvent = nomEvent;
        this.emplacement = emplacement;
        this.descriptionEvent = descriptionEvent;
        this.prix = prix;
        this.affiche = affiche;
        this.nbrMax = nbrMax;
        
    }

    public Evenement( String nomEvent, String emplacement, String descriptionEvent, int prix, String affiche, int nbrMax) {
       
        this.nomEvent = nomEvent;
        this.emplacement = emplacement;
        this.descriptionEvent = descriptionEvent;
        this.prix = prix;
        this.affiche = affiche;
        this.nbrMax = nbrMax;
       
    }

   

   
    
    
   
    
    public Evenement() {
        
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

   
   

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    

    public int getNbrMax() {
        return nbrMax;
    }

    public void setNbrMax(int nbrMax) {
        this.nbrMax = nbrMax;
    }

    
    
    

   

   

    
}
