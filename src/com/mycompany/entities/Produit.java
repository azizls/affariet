/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author 21656
 */
public class Produit {
   private int id_produit;
    private String nom_produit;
    private String description_produit;
    private float prix_produit;
    private String image_produit;
    private Categorie id_categorie;
    private user user;
    public Produit() {
    }

    public Produit(int id_produit, String nom_produit, String description_produit, float prix_produit) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.description_produit = description_produit;
        this.prix_produit = prix_produit;
    }

    public Produit(int id_produit, String nom_produit, String description_produit, float prix_produit, String image_produit,Categorie id_categorie) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.description_produit = description_produit;
        this.prix_produit = prix_produit;
        this.image_produit = image_produit;
        
    }

    public Produit(String nom_produit, String description_produit, float prix_produit, String image_produit, Categorie id_categorie, user user) {
        this.nom_produit = nom_produit;
        this.description_produit = description_produit;
        this.prix_produit = prix_produit;
        this.image_produit = image_produit;
        this.id_categorie = id_categorie;
        this.user = user;
    }

    public Produit(int id_produit, String nom_produit, String description_produit, float prix_produit, String image_produit, Categorie id_categorie, user user) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.description_produit = description_produit;
        this.prix_produit = prix_produit;
        this.image_produit = image_produit;
        this.id_categorie = id_categorie;
        this.user = user;
    }

    

    public int getId_produit() {
        return id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public String getDescription_produit() {
        return description_produit;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public String getImage_produit() {
        return image_produit;
    }

    public Categorie getId_categorie() {
        return id_categorie;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public user getUser() {
        return user;
    }
    

    

    

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public void setDescription_produit(String description_produit) {
        this.description_produit = description_produit;
    }

    public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }

    public void setImage_produit(String image_produit) {
        this.image_produit = image_produit;
    }

    public void setId_categorie(Categorie id_categorie) {
        this.id_categorie = id_categorie;
    }

    @Override
    public String toString() {
        return "Produit{" + "id_produit=" + id_produit + ", nom_produit=" + nom_produit + ", description_produit=" + description_produit + ", prix_produit=" + prix_produit + ", image_produit=" + image_produit + ", id_categorie=" + id_categorie + ", user=" + user + '}';
    }

    

    
    
    
    
    
    
    
    
}
