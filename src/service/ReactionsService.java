/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.annonce;
import entity.commentaire;
import entity.reactions;
import entity.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author lando
 */
public class ReactionsService  implements IService<reactions>{
    
     private   Connection conn;
    private reactions r;
    

    public ReactionsService() {
        conn=DataSource.getInstance().getCnx();
    }
    
    public void insert(reactions r, int id_annonce, int id_user) {
    String requete = "INSERT INTO reactions (id_annonce, id_user ,type_react) VALUES (?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(requete)) {
        pst.setInt(1, id_annonce);
        pst.setInt(2, id_user);
        pst.setString(3, r.getType_react());
        pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
    }    
}


    @Override
public void delete(reactions t) {
    String requete = "DELETE FROM reactions WHERE id = ?";
    try (PreparedStatement pst = conn.prepareStatement(requete)) {
        pst.setInt(1, t.getId());
        pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ReactionsService.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    
   public boolean checkUserReaction(int id_user, int id_annonce) throws SQLException {
    String query = "SELECT * FROM reactions WHERE id_user = ? AND id_annonce = ?";
    boolean userHasReacted = false;
    try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, id_user);
        ps.setInt(2, id_annonce);
        try (ResultSet result = ps.executeQuery()) {
            userHasReacted = result.next(); // Vérifier si la requête retourne une ligne
        }
    } catch (SQLException ex) {
        Logger.getLogger(ReactionsService.class.getName()).log(Level.SEVERE, null, ex);
        throw ex;
    }
    return userHasReacted;
}

   /**
 * Récupère la réaction d'un utilisateur sur une annonce donnée.
 *
 * @param userId l'identifiant de l'utilisateur
 * @param annonceId l'identifiant de l'annonce
 * @return la réaction de l'utilisateur sur l'annonce, ou null si l'utilisateur n'a pas réagi
 * @throws SQLException si une erreur SQL survient
 */
public reactions getReaction(int userId, int annonceId) throws SQLException {
    String query = "SELECT * FROM reactions WHERE id_user = ? AND id_annonce = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);
        stmt.setInt(2, annonceId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                reactions r = new reactions();
                r.setId(rs.getInt("id"));
                r.setType_react(rs.getString("type_react"));
                annonce a = new annonce();
                a.setId_annonce(rs.getInt("id_annonce"));
                r.setId_annonce(a);
                user u = new user();
                u.setId_user(rs.getInt("id_user"));
                r.setId_user(userId);
                return r;
            } else {
                return null;
            }
        }
    }
}

    

   
   
   public void deleteReaction(int id_user, int id_annonce) throws SQLException {
    String requete = "DELETE FROM reactions WHERE id_user = ? AND id_annonce = ?";
    try (PreparedStatement pst = conn.prepareStatement(requete)) {
        pst.setInt(1, id_user);
        pst.setInt(2, id_annonce);
        pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ReactionsService.class.getName()).log(Level.SEVERE, null, ex);
        // Afficher un message d'erreur en cas d'exception
        // ...
        throw ex;
    }
}


    
    @Override
    public void update(reactions t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<reactions> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public reactions readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public reactions get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(reactions t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   }
