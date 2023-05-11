/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.evenement;
import entity.reservation;
import entity.user;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author zakar
 */
public class reservationService implements IService<reservation>{
    
    private final Connection conn;

    public reservationService() {
        conn=DataSource.getInstance().getCnx();
    }

//    
    
    @Override
    public void insert(reservation t)  {
    String sql = "INSERT INTO reservation (date_res, id_event, id_user1) VALUES (?, ?, ?)";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setDate(1, Date.valueOf(t.getDate_res()));
        statement.setInt(2, t.getId_event().getId_event());
        statement.setInt(3, t.getId_user1().getId_user());
        statement.executeUpdate();
    }   catch (SQLException ex) {
            Logger.getLogger(reservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    
    

    @Override
    public void delete(reservation t) {
        
        String requete="delete from reservation where id_res = "+t.getId_res();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(reservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(reservation t) {
        
        String requete = "update reservation set date_res=? where id_res=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            
            LocalDate dateRes = t.getDate_res();
            java.sql.Date sqlDateRes = java.sql.Date.valueOf(dateRes);
            
            pst.setDate(1, sqlDateRes);
            pst.setInt(2, t.getId_res());
            pst.executeUpdate();
            System.out.println("reservation mise à jour");
        } catch (SQLException ex) {
            System.out.println("erreur lors de la mise à jour de l'event" + ex.getMessage());
        }
        
    }

    
    
    
    
    @Override
   public List<reservation> readAll()  {
    List<reservation> reservations = new ArrayList<>();
    String sql = "SELECT r.*, e.*, u.id_user, u.nom, u.prenom " +
                 "FROM reservation r " +
                 "JOIN evenement e ON r.id_event = e.id_event " +
                 "JOIN user u ON r.id_user1 = u.id_user";
    
    try {
        Statement statement = conn.createStatement();
         ResultSet resultSet = statement.executeQuery(sql) ;
        while (resultSet.next()) {
            reservation reservation = new reservation();
            reservation.setId_res(resultSet.getInt("id_res"));
            reservation.setDate_res(resultSet.getDate("date_res").toLocalDate());
            evenement e = new evenement();
            user useEv = new user();
            e.setId_event(resultSet.getInt("id_event"));
            e.setNom_event(resultSet.getString("nom_event"));
            e.setEmplacement(resultSet.getString("emplacement"));
            e.setDate_debut(resultSet.getDate("date_debut").toLocalDate());
            e.setDate_fin(resultSet.getDate("date_fin").toLocalDate());
            e.setDescription_event(resultSet.getString("description_event"));
            e.setPrix(resultSet.getInt("prix"));
//            e.setAffiche(resultSet.getString("affiche"));
            useEv.setId_user(resultSet.getInt("id_user"));
            useEv.setNom(resultSet.getString("nom"));
            useEv.setPrenom(resultSet.getString("prenom"));
            e.setUser(useEv);
            reservation.setId_event(e);
            
            user u = new user();
            u.setId_user(resultSet.getInt("id_user1"));
            u.setNom(resultSet.getString("nom"));
            u.setPrenom(resultSet.getString("prenom"));
            reservation.setEvenement(e);
            reservation.setUser(u);
            reservations.add(reservation);
        }
    }   catch (SQLException ex) {
            Logger.getLogger(reservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    return reservations;
}
    
    
//    @Override
//    public List<reservation> readAll() {
//        String requete = "SELECT reservation.*, evenement.id_event , evenement.nom_event , evenement.emplacement , evenement.date_debut , evenement.date_fin , evenement.description_event , evenement.prix , user.id_user FROM reservation "
//                 +"INNER JOIN evenement ON reservation.id_event = evenement.id_event"
//                +"INNER JOIN user ON reservation.id_user = user.id_user";
//    List<reservation> list = new ArrayList<>();
//    try {
//        Statement st = conn.createStatement();
//        ResultSet rs = st.executeQuery(requete);
//        while (rs.next()) {
//            evenement e = new evenement();
//            e.setId_event(rs.getInt("id_event"));
//            e.setNom_event(rs.getString("nom_event"));
//            e.setEmplacement(rs.getString("emplacement"));
//            e.setDate_debut(rs.getDate("date_debut"));
//            e.setDate_fin(rs.getDate("date_fin"));
//            e.setDescription_event(rs.getString("description_event"));
//            e.setPrix(rs.getInt("prix"));
//            reservation r = new reservation(rs.getInt("id_res"), e);
//            list.add(r);
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(reservationService.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    return list;
//    }


//    
    @Override
    public reservation readById(int id) {
    String sql = "SELECT r.id_res, r.date_res, e.*, u.id_user, u.nom, u.prenom " +
                 "FROM reservation r " +
                 "JOIN evenement e ON r.id_event = e.id_event " +
                 "JOIN user u ON r.id_user1 = u.id_user " +
                 "WHERE r.id_res = ?";
    reservation reservation = new reservation();
    try {
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reservation.setId_res(resultSet.getInt("id_res"));
                reservation.setDate_res(resultSet.getDate("date_res").toLocalDate());
                evenement e = new evenement();
                e.setId_event(resultSet.getInt("id_event"));
                e.setNom_event(resultSet.getString("nom_event"));
                e.setEmplacement(resultSet.getString("emplacement"));
                e.setDate_debut(resultSet.getDate("date_debut").toLocalDate());
                e.setDate_fin(resultSet.getDate("date_fin").toLocalDate());
                e.setDescription_event(resultSet.getString("description_event"));
                e.setPrix(resultSet.getInt("prix"));
                e.setAffiche(resultSet.getString("affiche"));
                reservation.setEvenement(e);
                user u = new user();
                u.setId_user(resultSet.getInt("id_user"));
                u.setNom(resultSet.getString("nom"));
                u.setPrenom(resultSet.getString("prenom"));
                reservation.setUser(u);
                return reservation;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(reservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reservation;
    }
    
    
    
    
    public reservation readByNomUuser(String nom) {
//    String requete = "SELECT r.*, e.nom_event, u.nom FROM reservation r INNER JOIN user u ON r.id_user1 = u.id_user WHERE nom= ?";
    reservation reservation = new reservation() ;
    
String sql = "SELECT r.* , e.nom_event, u.id_user, u.nom " +
                 "FROM reservation r " +
                 "JOIN evenement e ON r.id_event = e.id_event " +
                 "JOIN user u ON r.id_user1 = u.id_user " +
                 "WHERE u.nom = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            
            reservation.setId_res(rs.getInt("id_res"));
            reservation.setDate_res(rs.getDate("date_res").toLocalDate());
            evenement e = new evenement();
            e.setId_event(rs.getInt("id_event"));
            e.setNom_event(rs.getString("nom_event"));
            user u = new user();
            u.setId_user(rs.getInt("id_user1"));
            u.setNom(rs.getString("nom"));
            reservation= new reservation(LocalDate.MAX, e, u);
           
        }
        //System.out.println(reservations.get(0));
    }
    
    catch (SQLException ex){
        Logger.getLogger(evenementService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return reservation;
    

    }

    @Override
    public reservation get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
}
    
    
    
    
    

 
    

