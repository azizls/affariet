/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author MALEK
 */
public class reclamation {
     private int id_reclamation ; 
    public Date date_reclamation ; 
    private String description ; 
    private String type_reclamation ;
    private user id_user;
    private String etat_reclamation;
     private int avis;
     
     private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public reclamation(int id, Date date, String description, String typeReclamation, String etatReclamation, user loggedInUser) {
        this(id, date, description, typeReclamation, loggedInUser, etatReclamation);
    }

    public reclamation(int id, Date date, String description, String typeReclamation, int loggedInUser1, String etatReclamation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getAvis() {
        return avis;
    }

    public void setAvis(int avis) {
        this.avis = avis;
    }

   

   public reclamation(int id, Date date, String description, String typeReclamation, user loggedInUser, String etatReclamation) {
    this.id_reclamation = id;
    this.date_reclamation = date;
    this.description = description;
    this.type_reclamation = typeReclamation;
    this.id_user = loggedInUser;
    this.etat_reclamation = etatReclamation;
    
}
    


    public int getId_reclamation() {
        return id_reclamation;
    }

    public String getEtat_reclamation() {
        return etat_reclamation;
    }

    public void setEtat_reclamation(String etat_reclamation) {
        this.etat_reclamation = etat_reclamation;
    }

    public reclamation(int id_reclamation, Date date_reclamation, String description, String type_reclamation, String etat_reclamation) {
        this.id_reclamation = id_reclamation;
        this.date_reclamation = date_reclamation;
        this.description = description;
        this.type_reclamation = type_reclamation;
        this.etat_reclamation = etat_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public Date getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(Date date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_reclamation() {
        return type_reclamation;
    }

    public void setType_reclamation(String type_reclamation) {
        this.type_reclamation = type_reclamation;
    }

    public user getId_user() {
        return id_user;
    }

    public void setId_user(user id_user) {
        this.id_user = id_user;
    }

    public reclamation() {
    }

    public reclamation(int id_reclamation, Date date_reclamation, String description, String type_reclamation, user id_user) {
        this.id_reclamation = id_reclamation;
        this.date_reclamation = date_reclamation;
        this.description = description;
        this.type_reclamation = type_reclamation;
        this.id_user = id_user;
    }

    public reclamation(Date date_reclamation, String description, String type_reclamation, user id_user) {
        this.date_reclamation = date_reclamation;
        this.description = description;
        this.type_reclamation = type_reclamation;
        this.id_user = id_user;
    }

    public reclamation(int id_reclamation, Date date_reclamation, String description, String type_reclamation) {
        this.id_reclamation = id_reclamation;
        this.date_reclamation = date_reclamation;
        this.description = description;
        this.type_reclamation = type_reclamation;
    }

    public reclamation(String description, String type_reclamation, user id_user) {
        this.description = description;
        this.type_reclamation = type_reclamation;
        this.id_user = id_user;
    }

    public reclamation(String description, String type_reclamation) {
        this.description = description;
        this.type_reclamation = type_reclamation;
    }
    
    
    
    
    
}
