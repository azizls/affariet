/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;
import java.time.LocalDate;



/**
 *
 * @author zakar
 */
public class evenement {
    
    private int id_event;
    private String nom_event ;
    private String emplacement;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private String description_event;
    private int prix;
    private String affiche;
    private int nbr_max ;
    private user id_user; 

 
    public evenement(int id_event, String nom_event1, String emplacement1, LocalDate date_d, LocalDate date_f, String description, int prix2, String image, int nbr_max1, int id_user1) {
        this.id_event = id_event;
    }

    
    
    public evenement(int id_event, String nom_event, String emplacement, LocalDate date_debut, LocalDate date_fin, String description_event, int prix, int nbr_max, user id_user) {
        this.id_event = id_event;
        this.nom_event = nom_event;
        this.emplacement = emplacement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description_event = description_event;
        this.prix = prix;
        this.nbr_max = nbr_max;
        this.id_user = id_user;
    }

    
    
    
    public evenement(String nom_event, String emplacement, LocalDate date_debut, LocalDate date_fin, String description_event, int prix, String affiche, int nbr_max, user id_user) {
        this.nom_event = nom_event;
        this.emplacement = emplacement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description_event = description_event;
        this.prix = prix;
        this.affiche = affiche;
        this.nbr_max = nbr_max;
        this.id_user = id_user;
    }

    public evenement() {
    }

    public evenement(int id_event, String nom_event, String emplacement, LocalDate date_debut, LocalDate date_fin, String description_event, int prix, String affiche, int nbr_max, user id_user) {
        this.id_event = id_event;
        this.nom_event = nom_event;
        this.emplacement = emplacement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description_event = description_event;
        this.prix = prix;
        this.affiche = affiche;
        this.nbr_max = nbr_max;
        this.id_user = id_user;
    }

    public int getNbr_max() {
        return nbr_max;
    }

    public void setNbr_max(int nbr_max) {
        this.nbr_max = nbr_max;
    }

    public evenement(int id_event, String nom_event, String emplacement, LocalDate date_debut, LocalDate date_fin, String description_event, int prix, user id_user) {
        this.id_event = id_event;
        this.nom_event = nom_event;
        this.emplacement = emplacement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description_event = description_event;
        this.prix = prix;
        this.id_user = id_user;
    }

    public evenement(int id_event, String nom_event, String emplacement, LocalDate date_debut, LocalDate date_fin, String description_event, int prix, String affiche, user id_user) {
        this.id_event = id_event;
        this.nom_event = nom_event;
        this.emplacement = emplacement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description_event = description_event;
        this.prix = prix;
        this.affiche = affiche;
        this.id_user = id_user;
    }

    public evenement( String nom_event, String emplacement, LocalDate date_debut, LocalDate date_fin, String description_event, int prix, user id_user) {
        this.nom_event = nom_event;
        this.emplacement = emplacement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description_event = description_event;
        this.prix = prix;
        this.id_user = id_user;
    }

    public evenement(String nom_event, String emplacement, LocalDate date_debut, LocalDate date_fin, String description_event, int prix, String affiche, user id_user) {
        this.nom_event = nom_event;
        this.emplacement = emplacement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description_event = description_event;
        this.prix = prix;
        this.affiche = affiche;
        this.id_user = id_user;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public user getId_user() {
        return id_user;
    }

    public void setId_user(user id_user) {
        this.id_user = id_user;
    }

   

    public int getId_event() {
        return id_event;
    }

    public String getNom_event() {
        return nom_event;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public String getDescription_event() {
        return description_event;
    }

    public int getPrix() {
        return prix;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public void setDescription_event(String description_event) {
        this.description_event = description_event;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
     public void setUser(user u) {
    this.id_user = u;
}

    @Override
    public String toString() {
        return "evenement{" + "id_event=" + id_event + ", nom_event=" + nom_event + ", emplacement=" + emplacement + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", description_event=" + description_event + ", prix=" + prix + ", affiche=" + affiche + ", nbr_max=" + nbr_max + ", id_user=" + id_user + '}';
    }

 

 
    
    
    
    
    
}
