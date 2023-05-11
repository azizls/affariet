/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Interfaces.InterfaceCrud;
import entity.reclamation;
import entity.user;
import utils.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;

/**
 *
 * @author MALEK
 */
public abstract class ReclamationService implements InterfaceCrud<reclamation>{
// Connection cnx;
 Connection cnx = DataSource.getInstance().getCnx();
    
 
    public void insert(reclamation r) {
    if (r.getDescription().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erreur de saisie");
        alert.setContentText("La description ne peut pas être vide !");
        alert.showAndWait();
        return;
    }
         
    String query = "INSERT INTO reclamation (date_reclamation, description, type_reclamation, id_user, imagePath) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
        Date currentDate = new Date();
        preparedStatement.setDate(1, new java.sql.Date(currentDate.getTime())) ;
        preparedStatement.setString(2, r.getDescription());
        preparedStatement.setString(3, r.getType_reclamation());
        preparedStatement.setInt(4, r.getId_user().getId_user());
        preparedStatement.setString(5, r.getImagePath());
        preparedStatement.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

 
    public void delete(int id_reclamation) {
try {
              String req="DELETE FROM `reclamation` WHERE (`id_reclamation`='"+id_reclamation+"' )";
              Statement st = cnx.createStatement();
              st.executeUpdate(req);
              System.out.println("reclamation supprimée avec succes");
          } catch (SQLException ex) {
              Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    @Override
    public void update(reclamation r) {
      try {
        String req = "UPDATE `reclamation` SET `etat_reclamation`='"+r.getEtat_reclamation()+"', `avis`='"+r.getAvis()+"' WHERE `id_reclamation`='"+r.getId_reclamation()+"'";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Reclamation modifiee avec succes");
    } catch (SQLException ex) {
        Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @Override
    public ArrayList<reclamation> readAll() {
     List<reclamation> lr=new ArrayList<>();
        try {
              String req="SELECT * FROM `reclamation`";
              Statement ste = cnx.createStatement();
              ResultSet res=ste.executeQuery(req);
              while(res.next()){
                  
                  reclamation r=new reclamation();
                  r.setId_reclamation(res.getInt(1));
                  r.setDate_reclamation(res.getDate(2));
                  r.setDescription(res.getString(3));
                  r.setType_reclamation(res.getString(4));
               
                 //r.setId_user((user) res.getObject(5));
                  r.setEtat_reclamation(res.getString(6));
                
                  lr.add(r);
              }
              
          } catch (SQLException ex) {
              Logger.getLogger( ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
          }
           return (ArrayList<reclamation>) lr; 
        
       /* try {
    Statement stmt = cnx.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM reclamation");
    while (rs.next()) {
        reclamation r = new reclamation();
        r.setId_reclamation(rs.getInt("id_reclamation"));
        r.setDate_reclamation(rs.getDate("date_reclamation"));
        r.setDescription(rs.getString("description"));
        r.setType_reclamation(rs.getString("type_reclamation"));

        // Récupérer l'objet user associé à la réclamation
        int userId = rs.getInt("user_id");
        user u = getUserById(userId);

        // Affecter l'objet user à la réclamation
        r.setId_user(u);

        r.setEtat_reclamation(rs.getString("etat_reclamation"));

        lr.add(r);
    }
} catch (SQLException ex) {
    System.out.println("Error while fetching reclamations: " + ex.getMessage());
}

// Renvoyer la liste des réclamations
return (ArrayList<reclamation>) lr;  */
        
    }

    @Override
    public reclamation readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public ArrayList<reclamation> sortBy(String nom_column, String Asc_Dsc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
public List<reclamation> getAllReclamationsForUser(String userId) {
    // Récupérez toutes les réclamations
    List<reclamation> allReclamations = readAll();
    // Filtrer les réclamations pour l'ID de l'utilisateur spécifié
    List<reclamation> userReclamations = allReclamations.stream()
            .filter(r -> r.getId_user().equals(userId))
            .collect(Collectors.toList());
    return userReclamations;
}

    private user getUserById(int userId) {
        user u = null;
    try {
        //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/affariet", "root", "root");
        String query = "SELECT * FROM user WHERE id=?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            u = new user();
            u.setId_user(rs.getInt("id"));
            
            // etc.
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return u;
    }

    public void updateImagePath(reclamation rec) {
           try {
        Connection cnx = DataSource.getInstance().getCnx();
        String updateQuery = "UPDATE reclamation SET imagePath=? WHERE id_reclamation=?";
        PreparedStatement stmt = cnx.prepareStatement(updateQuery);
        stmt.setString(1, rec.getImagePath());
        stmt.setInt(2, rec.getId_reclamation());
        stmt.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    
    public List<reclamation> getByUser(user loggedInUser) {
     
        
        List<reclamation> reclamations = new ArrayList<>();
    String query = "SELECT * FROM reclamation WHERE id_user = ?";
    try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
        preparedStatement.setInt(1, loggedInUser.getId_user());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id_reclamation");
            Date date = resultSet.getDate("date_reclamation");
            String description = resultSet.getString("description");
            String typeReclamation = resultSet.getString("type_reclamation");
               String etatReclamation = resultSet.getString("etat_reclamation");
            reclamation r = new reclamation(id, date, description, typeReclamation, loggedInUser, etatReclamation);
            reclamations.add(r);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return reclamations;
        
        
    }
    
    
}
