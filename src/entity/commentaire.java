/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import entity.annonce;
import entity.user;

/**
 *
 * @author lando
 */
public class commentaire {
    private int id_commentaire ;
    private String comment ;
    private annonce id_annonce;
    private user id_user1 ; 
    private String email;

   

//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }

  
//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
    public commentaire(int id_commentaire, String comment, annonce id_annonce, user id_user1, String email) {
        this(id_commentaire, comment, id_annonce, email);
    }

//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
    public commentaire(int id_commentaire, String comment, annonce id_annonce, String email) {
        this(comment, id_annonce, id_commentaire, email);
    }

//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }

//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
    public commentaire(String comment, annonce id_annonce, int id_commentaire, String email) {
        this.id_commentaire = id_commentaire;
        this.comment = comment;
        this.id_annonce = id_annonce;
        this.id_user1 = id_user1;
        this.email = email;
    }
//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
//    public commentaire(String comment, annonce annonceSelectionnee, int id_commentaire) {
//        this(comment, id_commentaire, u);
//    }
    

    public commentaire() {
    }
    
    
    

    public commentaire(int id_commentaire, String comment, annonce id_annonce, user id_user1) {
        this.id_commentaire = id_commentaire;
        this.comment = comment;
        this.id_annonce = id_annonce;
        this.id_user1 = id_user1;
    }

    public commentaire(String comment, annonce id_annonce, user id_user1) {
        this.comment = comment;
        this.id_annonce = id_annonce;
        this.id_user1 = id_user1;
    }

    public commentaire(int id_commentaire, String comment, user id_user1) {
        this.id_commentaire = id_commentaire;
        this.comment = comment;
        this.id_user1 = id_user1;
    }
        
        
    

    public commentaire(int id_commentaire, String comment, annonce id_annonce) {
        this.id_commentaire = id_commentaire;
        this.comment = comment;
        this.id_annonce = id_annonce;
    }
    
    /**
     *
     * @param id_commentaire
     * @param comment
     * @param id_annonce
     */
    public commentaire(int id_commentaire, String comment) {
        this.id_commentaire = id_commentaire;
        this.comment = comment;
       
        
    }
    

    public commentaire(String comment, annonce id_annonce) {
        this.comment = comment;
        this.id_annonce = id_annonce;
    }

    public commentaire(String test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public commentaire(int id_commentaire, int id_annonce, String texte_comment, int id_user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId_commentaire() {
        return id_commentaire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId_commentaire(int id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

   public user getId_user1() {
        return id_user1;
    }

    public void setId_user1(user id_user1) {
        this.id_user1 = id_user1;
    }

    public annonce getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(annonce id_annonce) {
        this.id_annonce = id_annonce;
    }

    @Override
    public String toString() {
        return "commentaire{" + "id_commentaire=" + id_commentaire + ", comment=" + comment + ", id_annonce=" + id_annonce + '}';
    }

    public annonce readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    

   
    
    
}
