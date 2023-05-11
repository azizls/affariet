/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;



/**
 *
 * @author jawhe
 */
public class Commande {
    private int id_commande;
    private user user;
    private String methode;
    private Produit id_produit;
    private Date date_commande;
    private String charge_id;

    public String getCharge_id() {
        return charge_id;
    }

    public void setCharge_id(String charge_id) {
        this.charge_id = charge_id;
    }
    
    
    
    
    
    public Commande() {
    }

    public Date getDate_commande() {
        return date_commande;
    }
    
public Commande(int id_commande, user user, Produit id_produit, Date date_commande) {
        this.id_commande = id_commande;
        this.user = user;
        this.id_produit = id_produit;
        this.date_commande = date_commande;
    }
    public Commande( Produit id_produit,user user) {
        this.user = user;
        this.id_produit = id_produit;
    }

    public Commande(int id_commande, user user, Produit id_produit) {
        this.id_commande = id_commande;
        this.user = user;
        this.id_produit = id_produit;
    }

    public Commande( String methode, Produit id_produit,user user) {
        this.user = user;
        this.methode = methode;
        this.id_produit = id_produit;
    }

    public Commande(int id_commande,  String methode) {
        this.id_commande = id_commande;
        
        this.methode = methode;
    }

    public Commande(int id_commande, user user, String methode, Produit id_produit) {
        this.id_commande = id_commande;
        this.user = user;
        this.methode = methode;
        this.id_produit = id_produit;
    }

    public Commande(int id_commande,  String methode, Produit id_produit) {
        this.id_commande = id_commande;
        
        this.methode = methode;
        this.id_produit = id_produit;
    }

    public Commande(  String methode, Produit id_produit) {
        
        
        this.methode = methode;
        this.id_produit = id_produit;
    }

    
    public Commande(int id_commande) {
        this.id_commande = id_commande;
    }

   
    
    public int getId_commande() {
        return id_commande;
    }

   

    public String getMethode() {
        return methode;
    }

    public Produit getId_produit() {
        return id_produit;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public user getuser() {
        return user;
    }

    public void setuser(user user) {
        this.user = user;
    }

    

    
    public void setMethode(String methode) {
        this.methode = methode;
    }

    public void setId_produit(Produit id_produit) {
        this.id_produit = id_produit;
    }

    @Override
    public String toString() {
        return "Commande{" + "id_commande=" + id_commande + ", methode=" + methode + ", nom produit=" + id_produit.getNom_produit() + ", description produit=" +id_produit.getDescription_produit()  + ", prix produit=" +id_produit.getPrix_produit() + ", user=" +user.getId_user() +'}';
    }

  
    
    
    
}
