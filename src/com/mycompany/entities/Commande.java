/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.entities;
import java.util.Date;

/**
 *
 * @author bhk
 */
public class Commande {
    private int id_commande;
    private int user;
    
    
    private Date date_commande;
    private int id_produit;
    private String nom_produit;
    private float prix_produit;
    private String chargeId;
    public Commande() {
    }
     public float getPrix_produit() {
        return prix_produit;
    }
     public String getNom_produit() {
        return nom_produit;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public Commande(int id_commande, Date date_commande, String nom_produit, float prix_produit) {
        this.id_commande = id_commande;
        this.date_commande = date_commande;
        this.nom_produit = nom_produit;
        this.prix_produit = prix_produit;
    }
     
public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }
public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }
    public Date getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(Date date_commande) {
        this.date_commande = date_commande;
    }

    public Commande(int id_commande, int user, int id_produit, Date date_commande) {
        this.id_commande = id_commande;
        this.user = user;
        this.id_produit = id_produit;
        this.date_commande = date_commande;
    }

    public Commande( int id_produit,int user) {
        this.user = user;
        this.id_produit = id_produit;
    }

    public Commande(int id_commande, int user, int id_produit) {
        this.id_commande = id_commande;
        this.user = user;
        this.id_produit = id_produit;
    }

  

  

    

    

    
    public Commande(int id_commande) {
        this.id_commande = id_commande;
    }

   
    
    public int getId_commande() {
        return id_commande;
    }

   

    

    public int getId_produit() {
        return id_produit;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    

    
   

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    @Override
    public String toString() {
        return "Commande{" + "id_commande=" + id_commande +  ", nom produit=" + nom_produit + ", description produit=" +prix_produit  + ", prix produit=" +'}';
    }

  
    
    
    
}
