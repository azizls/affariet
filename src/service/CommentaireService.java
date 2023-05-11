/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.annonce;
import entity.user;
import entity.commentaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author lando
 */
public class CommentaireService implements IService<commentaire>{
    
    private   Connection conn;
    private commentaire c;
   // private static int id_user1 ;

    public CommentaireService() {
        conn=DataSource.getInstance().getCnx();
    }
    


    public void insert(commentaire c, int id_annonce, int id_user1, String email) {
        String requete = "INSERT INTO commentaire (comment, id_annonce, id_user1) VALUES (?, ?, ?)";
        try (final PreparedStatement pst = conn.prepareStatement(requete)) {
            pst.setString(1, c.getComment());
            pst.setInt(2, id_annonce);
            pst.setInt(3, id_user1);
            //pst.setString(4, email);
            pst.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void delete(commentaire c) {
            String requete="delete from commentaire where id_commentaire = "+c.getId_commentaire();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

  
    
@Override
public List<commentaire> readAll() {
    String requete = "SELECT c.*, a.id_annonce, a.type, a.description, a.date_annonce, u1.id_user as user1_id, u1.nom as user1_nom, u1.prenom as user1_prenom, u1.email as user1_email, u2.id_user as annonceur_id, u2.nom as annonceur_nom, u2.prenom as annonceur_prenom, u2.email as annonceur_email " +
                     "FROM commentaire c " +
                     "INNER JOIN annonce a ON c.id_annonce = a.id_annonce " +
                     "INNER JOIN user u1 ON c.id_user1 = u1.id_user " +
                     "INNER JOIN user u2 ON a.id_user = u2.id_user";
    List<commentaire> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            user annonceur = new user();
            annonceur.setId_user(rs.getInt("annonceur_id"));
            annonceur.setNom(rs.getString("annonceur_nom"));
            annonceur.setPrenom(rs.getString("annonceur_prenom"));
            annonceur.setEmail(rs.getString("annonceur_email"));

            user commentaire_user = new user();
            commentaire_user.setId_user(rs.getInt("id_user1"));
            commentaire_user.setNom(rs.getString("user1_nom"));
            commentaire_user.setPrenom(rs.getString("user1_prenom"));
            commentaire_user.setEmail(rs.getString("user1_email"));

            annonce a = new annonce();
            a.setId_annonce(rs.getInt("id_annonce"));
//            a.setType(rs.getString("type"));
//            a.setDescription(rs.getString("description"));
//            a.setDate_annonce(rs.getDate("date_annonce"));
//            a.setId_user(annonceur);

            commentaire c = new commentaire(rs.getInt("id_commentaire"), rs.getString("comment"), a, commentaire_user);
            list.add(c);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}


public List<commentaire> getCommentairesByAnnonce1(int id_annonce) throws SQLException {
    List<commentaire> commentaires = new ArrayList<>();
    String query = "SELECT * FROM commentaire WHERE id_annonce = ?";
    try (PreparedStatement statement = conn.prepareStatement(query)) {
        statement.setInt(1, id_annonce);
    
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
           // int id_commentaire = resultSet.getInt("id_commentaire");
            // comment = resultSet.getString("comment");
            user u =new user();
            u.setId_user(resultSet.getInt("id_user1"));
            
            
//            u1.setNom(resultSet.getString("nom"));
//            u1.setPrenom(resultSet.getString("prenom"));
//           // u1.setEmail(resultSet.getString("email"));
            
            
            annonce a = new annonce(); 
            a.setId_annonce(resultSet.getInt("id_annonce"));
              
//            a.setType(resultSet.getString("type"));
//            a.setDescription(resultSet.getString("description"));
//            a.setDate_annonce(resultSet.getDate("date_annonce"));
           // a.setId_user(annonceur);
            
            
            commentaire c = new commentaire(resultSet.getInt("id_commentaire"), resultSet.getString("comment"),a);
            commentaires.add(c);
        }
    }
    return commentaires;
}






    @Override
   public commentaire readById(int id_annonce)  {
    String requete;
    requete = "SELECT * FROM commentaire WHERE id_annonce = ?";

   // commentaire c = null;
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            
             user u = new user();
           u.setId_user(rs.getInt("id_user"));
           u.setEmail(rs.getString("email"));
           u.setNom(rs.getString("nom"));
           u.setPrenom(rs.getString("prenom"));
            
             //int id_user1 = rs.getInt("id_user1");
            annonce a = new annonce();
            a.setId_annonce(rs.getInt("id_annonce"));
            a.setType(rs.getString("type"));
            a.setDescription(rs.getString("description"));
            a.setDate_annonce(rs.getDate("date_annonce"));
            a.setId_user(u);
          //  c = new commentaire(rs.getString("comment"), rs.getInt("id_commentaire"),u);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
    }

    return c;
}


   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
//    public List<commentaire> getCommentairesByAnnonce(annonce a) throws SQLException {
//        List<commentaire> commentaires = new ArrayList<>();
//        String query = "SELECT * FROM commentaire WHERE id_annonce = ?";
//        try (PreparedStatement statement = conn.prepareStatement(query)) {
//            statement.setInt(1, a.getId_annonce());
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                int id_commentaire = resultSet.getInt("id_commentaire");
//                String comment = resultSet.getString("comment");
//                int id_user1 = resultSet.getInt("id_user1");
//                commentaire c = new commentaire(id_commentaire, a, comment, id_user1);
//                commentaires.add(c);
//            }
//        }
//        return commentaires;
//    }
   
   
   
   
   
    @Override
    public void update(commentaire c) {
       
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE commentaire SET comment = ? WHERE id_commentaire = ?");
            ps.setString(1, c.getComment());
            ps.setInt(2, c.getId_commentaire());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public commentaire get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(commentaire t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    

   
   
    
    
}

