/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.io.ByteArrayInputStream;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author lando
 */
public class annonce {
    
    private int id_annonce;
    private String type;
    private String description;
    private Date date_annonce;
    private String image ;
    private int nblikes ;
    private user id_user ;
    private int nbdislikes ;
     //private String image;
    // private byte[] image;
   // private user user;

    public annonce(int id_annonce, String type, String description, Date date_annonce, String image, int nblikes, user id_user, int nbdislikes, List<commentaire> commentaires) {
        this(type, description, image, id_annonce);
    }

    public annonce(String type, String description, String image, int id_annonce) {
        this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.image = image;
        this.nblikes = nblikes;
        this.id_user = id_user;
        this.nbdislikes = nbdislikes;
        this.commentaires = commentaires;
    }

  

    public annonce(int id_annonce, String type, String description, Date date, String nomImage, int aInt1) {
        this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.id_user = id_user;
    }

    public annonce(int id_annonce, String type, String description, Date date_annonce, String image, user id_user) {
        this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.image = image;
        this.id_user = id_user;
    }

    public annonce(int id_annonce, String type, String description, Date date_annonce, String image, int nblikes, user id_user, List<commentaire> commentaires) {
        this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.image = image;
        this.nblikes = nblikes;
        this.id_user = id_user;
        this.commentaires = commentaires;
    }

    public annonce(String type, String description, Date date_annonce, String image, int nblikes, user id_user) {
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.image = image;
        this.nblikes = nblikes;
        this.id_user = id_user;
    }

    public annonce(String type, String description, Date date_annonce, String image, int nblikes, user id_user, List<commentaire> commentaires) {
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.image = image;
        this.nblikes = nblikes;
        this.id_user = id_user;
        this.commentaires = commentaires;
    }

    public int getNblikes() {
        return nblikes;
    }

    public void setNblikes(int nblikes) {
        this.nblikes = nblikes;
    }

   

   

    

    public annonce() {
    }
    
    /**
     *
     * @param id_annonce
     * @param type
     * @param description
     * @param id_user
     * @param image
     */
    public annonce(int id_annonce, String type, String description, user id_user, String image) {
        this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.id_user = id_user;
        this.image = image;
    }
    
    
public annonce(int id_annonce, String type, String description, Date date_annonce, user id_user) {
        this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.id_user = id_user;
    }

    public annonce(String type, String description, String image) {
        this.type = type;
        this.description = description;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public annonce(String type, String description, String image, user id_user) {
        this.type = type;
        this.description = description;
        this.image = image;
        this.id_user = id_user;
    }
      



    /**
     *
     * @param id_annonce
     * @param type
     * @param description
     * @param date_annonce
     */
    public annonce(int id_annonce, String type, String description,Date date_annonce) {
        this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        
    }
    
     public annonce( String type, String description,Date date_annonce) {
        
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        
    }

    public annonce(String type, String description, Date date_annonce, user id_user) {
        this.type = type;
        this.description = description;
        this.date_annonce = date_annonce;
        this.id_user = id_user;
    }

    public annonce(String type, String description, user id_user) {
        this.type = type;
        this.description = description;
        this.id_user = id_user;
    }

    public int getNbdislikes() {
        return nbdislikes;
    }

    public void setNbdislikes(int nbdislikes) {
        this.nbdislikes = nbdislikes;
    }
     
     
     
     

    /**
     *
     * @param type
     * @param description
     */
    public annonce(String type, String description) {
       this.type = type;
        this.description = description;
       
    }

    public annonce(int aInt, String string, String string0, String string1, java.sql.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
 

    public int getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(int id_annonce) {
        this.id_annonce = id_annonce;
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

    @Override
    public String toString() {
        return "annonce{" + "id_annonce=" + id_annonce + ", type=" + type + ", description=" + description + ", date_annonce=" + date_annonce + ", id_user=" + id_user + '}';
    }

   

//    public void setId_annonce(String id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public void getId_annonce(int i) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public user getId_user() {
        return id_user;
    }

    public void setId_user(user id_user) {
        this.id_user = id_user;
    }


    
   public user getUser() {
    return id_user;
}

   

    public annonce(String id_annonce, String type, String description, String image, user id_user) {
        //this.id_annonce = id_annonce;
        this.type = type;
        this.description = description;
        this.image = image;
        this.id_user = id_user;
    }

   
  private List<commentaire> commentaires;
    
    // autres méthodes de la classe Annonce
    
    public void setCommentaires(List<commentaire> commentaires) {
        this.commentaires = commentaires;
    }
    
    public List<commentaire> getCommentaires() {
        return commentaires;
    }
 
    
      
//    public Image getImageObject() {
//        return imageObject;
//    }
//
//    public void setImageObject(Image imageObject) {
//        this.imageObject = imageObject;
//    }
    
    
  

    
   
}  
     



