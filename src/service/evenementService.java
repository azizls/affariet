/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.evenement;
import entity.user;
import java.io.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;
import java.time.LocalDate;
/**
 *
 * @author zakar
 */
public class evenementService implements IService<evenement> {
    
    
    private Connection conn;

    public evenementService() {
        conn=DataSource.getInstance().getCnx();
        
    }
    
    
    

    @Override
    public void insert(evenement e) {
        
        String requete="insert into evenement (nom_event,emplacement,date_debut,date_fin,description_event,prix,affiche,nbr_max,id_user) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst=conn.prepareStatement(requete);
            LocalDate dateFin = e.getDate_fin();
            java.sql.Date sqlDateFin = java.sql.Date.valueOf(dateFin);
            LocalDate dateD = e.getDate_debut();
            java.sql.Date sqlDateD = java.sql.Date.valueOf(dateD);
            pst.setString(1, e.getNom_event());
            pst.setString(2, e.getEmplacement());
            pst.setDate(3, sqlDateD);
            pst.setDate(4, sqlDateFin);
            
            pst.setString(5, e.getDescription_event());
            pst.setInt(6, e.getPrix());
            
//            convertir l'image en Byte
            FileInputStream fis = new FileInputStream(e.getAffiche());
            byte[] imageData = new byte[fis.available()];
            fis.read(imageData);
            fis.close();
            pst.setBytes(7, imageData);
            pst.setInt(8, e.getNbr_max());
            
            
            pst.setInt(9, e.getId_user().getId_user());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(evenementService.class.getName()).log(Level.SEVERE, null, ex);
        }catch (FileNotFoundException ex) {
             Logger.getLogger(evenementService.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(evenementService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void delete(evenement e) {
        
        String requete="delete from evenement where id_event= "+e.getId_event();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            System.out.println("erreur lors de la supp de l'event" + ex.getMessage());
        }
    }

    @Override
    public void update(evenement e) {
        String requete = "update evenement set nom_event=?, emplacement=? , date_debut=? , date_fin=?  , description_event=? , prix=? , nbr_max=? where id_event=?";
        try {
            
            //           pour convertir les Localdate en java.sql.date 
            LocalDate dateFin = e.getDate_fin();
            java.sql.Date sqlDateFin = java.sql.Date.valueOf(dateFin);
            LocalDate dateD = e.getDate_debut();
            java.sql.Date sqlDateD = java.sql.Date.valueOf(dateD);
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, e.getNom_event());
            pst.setString(2, e.getEmplacement());
            pst.setDate(3, sqlDateD);
            pst.setDate(4, sqlDateFin);
            pst.setString(5, e.getDescription_event());
           pst.setInt(6, e.getPrix());
           pst.setInt(7, e.getNbr_max());
            pst.setInt(8, e.getId_event());
            pst.executeUpdate();
            System.out.println("event maj");
        } catch (SQLException ex) {
            System.out.println("erreur lors de la mise à jour de l'event" + ex.getMessage());
        }
    }

    /**
     *
     * @return
     */

    @Override
    public List<evenement> readAll () {
        
           String requete ="select evenement.*, user.id_user from evenement INNER JOIN user ON evenement.id_user = user.id_user";
        List<evenement> list=new ArrayList<>();
            
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               evenement e=new evenement();
               e.setId_event(rs.getInt("id_event"));
               e.setNom_event(rs.getString("nom_event"));
               e.setEmplacement(rs.getString("emplacement"));
               
               // pour convertir les date en localdate 
               Date DD = rs.getDate("date_debut");
               LocalDate LDD = DD.toLocalDate();
               Date DF = rs.getDate("date_fin");
               LocalDate LDF = DF.toLocalDate();
               e.setDate_debut(LDD);
               e.setDate_fin(LDF);
               e.setPrix(rs.getInt("prix"));
               e.setDescription_event(rs.getString("description_event"));
               e.setAffiche(rs.getString("affiche"));
               e.setNbr_max(rs.getInt("nbr_max"));
               
              // instance d'un user 
               user u = new user();
               u.setId_user(rs.getInt("id_user"));
               e.setUser(u);
               list.add(e);
                       
           }
        } catch (SQLException ex) {
            Logger.getLogger(evenement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public evenement readById(int id) {
        
String requete ="select evenement.*,user.id_user from evenement INNER JOIN user ON evenement.id_user = user.id_user where id_event="+id;
       evenement e=new evenement();
       try {
             Statement st=conn.createStatement();
          ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
           e.setId_event(rs.getInt("id_event"));
           e.setNom_event(rs.getString("nom_event"));
           e.setEmplacement(rs.getString("emplacement"));
           
//           pour convertir les date en localdate 
           Date DD = rs.getDate("date_debut");
           LocalDate LDD = DD.toLocalDate();
           Date DF = rs.getDate("date_fin");
           LocalDate LDF = DF.toLocalDate();
           e.setDate_debut(LDD);
           e.setDate_fin(LDF);
           e.setPrix(rs.getInt("prix"));
           e.setDescription_event(rs.getString("description_event"));
           e.setAffiche(rs.getString("affiche"));
           e.setNbr_max(rs.getInt("nbr_max"));
           
           // instance d'un user 
               user u = new user();
           u.setId_user(rs.getInt("id_user"));
           e.setUser(u);

           
           
           }
           
        } catch (SQLException ex) {
            Logger.getLogger(evenement.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return e;    }


 public void delete1(int id) {
        
        String requete="delete from evenement where id_event= "+id;
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            System.out.println("erreur lors de la supp de l'event" + ex.getMessage());
        }
    }
 
 
 
 
 

public evenement readByNom(String nom) {
    String requete = "SELECT * FROM evenement WHERE nom_event = ?";
    evenement e = null;
    try {
        PreparedStatement ps = conn.prepareStatement(requete);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user u = new user();
            u.setId_user(rs.getInt("id_user"));
            e = new evenement(rs.getInt("id_event"), rs.getString("nom_event"), rs.getString("emplacement"), rs.getDate("date_debut").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getString("description_event"), rs.getInt("prix"), u);
        }
    }
    catch (SQLException ex){
        Logger.getLogger(evenementService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return e;
}
 
     public int insert1(evenement e) throws SQLException, FileNotFoundException, IOException {
        Statement st=conn.createStatement();
        PreparedStatement ps = conn.prepareStatement("insert into evenement (nom_event,emplacement,date_debut,date_fin,description_event,prix,affiche,nbr_max,id_user) values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        try {
       
            LocalDate dateFin = e.getDate_fin();
            java.sql.Date sqlDateFin = java.sql.Date.valueOf(dateFin);
            LocalDate dateD = e.getDate_debut();
            java.sql.Date sqlDateD = java.sql.Date.valueOf(dateD);
            ps.setString(1, e.getNom_event());
            ps.setString(2, e.getEmplacement());
            ps.setDate(3, sqlDateD);
            ps.setDate(4, sqlDateFin);
            
            ps.setString(5, e.getDescription_event());
            ps.setInt(6, e.getPrix());
            
            ps.setString(7,e.getAffiche());
            ps.setInt(8, e.getNbr_max());
            
            ps.setInt(9, e.getId_user().getId_user());
            ps.executeUpdate();
            
             // Récupérer l'id de l'evenet insérée
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id_event = rs.getInt(1);
            return id_event;
        } else {
            throw new SQLException("L'insertion de l'annonce a échoué, aucun ID généré.");
        }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(evenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public evenement get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
