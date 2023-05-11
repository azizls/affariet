/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.codename1.ui.EncodedImage;
import java.util.Date;

import com.codename1.ui.EncodedImage;
import java.util.Date;



/**
 *
 * @author lando
 */
public class annonce {
     private int idAnnonce;
    private String type;
    private String description;
    private Date date_annonce;
    private String image ;
    private int nblikes ;
    private user id_user ;
    private int nbdislikes ;

   
    public annonce(int idAnnonce, String type, String description, Date date_annonce, String image, int nblikes, user id_user, int nbdislikes) {
        this.idAnnonce = idAnnonce;
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.image = image;
        this.nblikes = nblikes;
        this.id_user = id_user;
        this.nbdislikes = nbdislikes;
    }

   

    public annonce(String type, String description, Date date_annonce, String image, int nblikes, user id_user, int nbdislikes) {
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.image = image;
        this.nblikes = nblikes;
        this.id_user = id_user;
        this.nbdislikes = nbdislikes;
    }

    public annonce(int idAnnonce, String type, String description, String image) {
        this(idAnnonce, type, description);
    }

    public annonce(int idAnnonce, String type, String description) {
        this.idAnnonce = idAnnonce;
        this.type = type;
        this.description = description;
        this.image = image;
    }

    public annonce(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public annonce() {
    }

   
   
    
    

    public int getId_annonce() {
        return idAnnonce;
    }

    public void setId_annonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_annonce() {
        return date_annonce;
    }

    public void setDate_annonce(Date date_annonce) {
        this.date_annonce = date_annonce;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNblikes() {
        return nblikes;
    }

    public void setNblikes(int nblikes) {
        this.nblikes = nblikes;
    }

    public user getId_user() {
        return id_user;
    }

    public void setId_user(user id_user) {
        this.id_user = id_user;
    }

    public int getNbdislikes() {
        return nbdislikes;
    }

    public void setNbdislikes(int nbdislikes) {
        this.nbdislikes = nbdislikes;
    }
    
    
    
     public String getEncodedImage() {
        return image;
    }

    public void setEncodedImage(EncodedImage image) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}

