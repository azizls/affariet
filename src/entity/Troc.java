/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.awt.Image;
/**
 *
 * @author jawhe
 */
public class Troc {
    private int id_troc;
    private Produit  produit;
    private String nom_produitTroc;
    private String description_produitTroc;
    private String image_produitTroc;
    private user user;
    private byte[] imageData;
    
    public Troc(int id_troc, Produit produit, String nom_produitTroc, String description_produitTroc) {
        this.id_troc = id_troc;
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
    }
    public Troc(int id_troc, Produit produit, String nom_produitTroc, String description_produitTroc,byte[] imageData) {
        this.id_troc = id_troc;
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
        this.imageData=imageData;
    }

    

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public Troc(int id_troc, Produit produit, String nom_produitTroc, String description_produitTroc, String image_produitTroc, user user) {
        this.id_troc = id_troc;
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
        this.image_produitTroc = image_produitTroc;
        this.user = user;
    }
    
    public Troc(Produit produit, String nom_produitTroc, String description_produitTroc, String image_produitTroc, user user) {
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
        this.image_produitTroc = image_produitTroc;
        this.user = user;
    }

    public Troc(Produit produit, String nom_produitTroc, String description_produitTroc, user user) {
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
        this.user = user;
    }

    public Troc(int id_troc, Produit produit, String nom_produitTroc, String description_produitTroc, user user) {
        this.id_troc = id_troc;
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
        this.user = user;
    }
    
    public Troc(Produit produit, String nom_produitTroc, String description_produitTroc, String image_produitTroc) {
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
        this.image_produitTroc = image_produitTroc;
    }
    
    public Troc( String nom_produitTroc, String description_produitTroc, String image_produitTroc) {
        
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
        this.image_produitTroc = image_produitTroc;
    }

    public Troc(Produit produit, String nom_produitTroc, String description_produitTroc) {
        this.produit = produit;
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
    }

    public Troc() {
        
    }

    public user getuser() {
        return user;
    }

    public void setuser(user user) {
        this.user = user;
    }
    
   

    public Troc(  String nom_produitTroc, String description_produitTroc) {
        this.id_troc = id_troc;
        
        this.nom_produitTroc = nom_produitTroc;
        this.description_produitTroc = description_produitTroc;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    

    public int getId_troc() {
        return id_troc;
    }

   

    public String getNom_produitTroc() {
        return nom_produitTroc;
    }

    public String getDescription_produitTroc() {
        return description_produitTroc;
    }

    public String getImage_produitTroc() {
        return image_produitTroc;
    }

    public void setId_troc(int id_troc) {
        this.id_troc = id_troc;
    }

    

    public void setNom_produitTroc(String nom_produitTroc) {
        this.nom_produitTroc = nom_produitTroc;
    }

    public void setDescription_produitTroc(String description_produitTroc) {
        this.description_produitTroc = description_produitTroc;
    }

    public void setImage_produitTroc(String image_produitTroc) {
        this.image_produitTroc = image_produitTroc;
    }

    @Override
    public String toString() {
        return "Troc{" + "id_troc=" + id_troc + ", id_produit=" + produit.getId_produit()  + ", id_user=" + user.getId_user()+ ", nom_user=" + user.getNom()  + ", nom produit=" + produit.getNom_produit() + ", description produit=" +produit.getDescription_produit()  + ", prix produit=" +produit.getPrix_produit()  +", nom_produitTroc=" + nom_produitTroc + ", description_produitTroc=" + description_produitTroc + ", image_produitTroc=" + image_produitTroc + '}';
    }
    
    
    
}
