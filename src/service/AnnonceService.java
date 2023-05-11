/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;
import entity.annonce;

import entity.user;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;
/**
 *
 * @author lando
 */
public class AnnonceService implements IService<annonce>{
    
    private  Connection conn;

    public AnnonceService() {
        conn=DataSource.getInstance().getCnx();
    }
   

@Override
    public void insert(annonce a){
        String requete="insert into annonce  (type,description,image,id_user) values (?,?,?,?) ";
        try {
            PreparedStatement st=conn.prepareStatement(requete);
            st.setString(1, a.getType());
            st.setString(2, a.getDescription());
           
            
            
            
            st.setString(3, a.getImage());
            st.setInt(4, a.getId_user().getId_user());
            //usr.setBlob(8, (Blob) t.getImage());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
        }

   
    }

    @Override
    public void delete(annonce a) {
            String requete="delete from annonce where id_annonce = "+a.getId_annonce();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
   public void update(annonce a) {
       
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE annonce SET type = ? , description=?   WHERE id_annonce = ?");
            ps.setString(1, a.getType());
            ps.setString(2, a.getDescription());
         //   ps.setInt(3, a.getLikes());
            ps.setInt(3, a.getId_annonce());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateLike(annonce a) {
       
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE annonce SET nblikes = ?  WHERE id_annonce = ?");
           
            ps.setInt(2, a.getId_annonce());
           ps.setInt(1, a.getNblikes());
           
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 @Override
public List<annonce> readAll() {
    String requete = "SELECT annonce.*, user.id_user, user.email, user.nom, user.prenom FROM annonce INNER JOIN user ON annonce.id_user = user.id_user";
    List<annonce> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            
             
            
            
    user u = new user();
    u.setId_user(rs.getInt("id_user"));
    u.setEmail(rs.getString("email"));
    u.setNom(rs.getString("nom"));
    u.setPrenom(rs.getString("prenom"));
    annonce a = new annonce(rs.getInt("id_annonce"), rs.getString("type"), rs.getString("description"), rs.getDate("date_annonce"),rs.getString("image"),u);
    //String userInfos = " (User: " + u.getEmail() + ", " + u.getNom() + " " + u.getPrenom() + ")";
   // a.setDate_annonce(a.getDate_annonce() + userInfos);
    list.add(a);
}
    } catch (SQLException ex) {
        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}


    /**
     *
     * @return
     */
    @Override
    public  annonce readById(int id_annonce) {
      String requete = "select * from annonce where id_annonce=" + id_annonce;
    annonce a = null;
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            user u1 = new user();
            u1.setId_user(rs.getInt("id_user"));
            a = new annonce(rs.getInt("id_annonce"), rs.getString("type"), rs.getString("description"),
                    rs.getDate("date_annonce"), rs.getString("image"), u1);
        }
        rs.close();
        st.close();
    } catch (SQLException ex) {
        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return a;
      
    }

   @Override
public annonce get_annonce(int id_annonce) {
    String requete = "select * from annonce where id_annonce=" + id_annonce;
    annonce a = null;
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            user u1 = new user();
            u1.setId_user(rs.getInt("id_user"));
            a = new annonce(rs.getInt("id_annonce"), rs.getString("type"), rs.getString("description"),
                    rs.getDate("date_annonce"), rs.getString("image"), u1);
        }
        rs.close();
        st.close();
    } catch (SQLException ex) {
        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return a;
}

public List<annonce> get_annonces_by_user(int id_user) {
    String requete = "select * from annonce where id_user=" + id_user;
    List<annonce> annonces = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            user u1 = new user();
            u1.setId_user(rs.getInt("id_user"));
            annonce a = new annonce(rs.getInt("id_annonce"), rs.getString("type"), rs.getString("description"),
                    rs.getDate("date_annonce"), rs.getString("image"), u1);
            annonces.add(a);
        }
        rs.close();
        st.close();
    } catch (SQLException ex) {
        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return annonces;
}



//    public annonce get_annonce(int id_annonce_static) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

   
//      public List<annonce> afficher() {
//    List<annonce> annonces = new ArrayList<>();
//    try (Connection conn = DriverManager.getConnection(url, root, root);
//         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM annonce JOIN user ON annonce.id_user = user.id_user");
//         ResultSet rs = stmt.executeQuery()) {
//        while (rs.next()) {
//            int id_annonce = rs.getInt("id_annonce");
//            String type = rs.getString("type");
//            String description = rs.getString("description");
//            Date date_annonce = rs.getDate("date_annonce");
//            String image = rs.getString("image");
//            int id_user = rs.getInt("id_user");
//            String username = rs.getString("username");
//            String email = rs.getString("email");
//            String password = rs.getString("password");
//
//            user user = new user(id_user, nom, prenom, email);
//            annonce annonce = new annonce(id_annonce, type, description, date_annonce, image, user);
//            annonces.add(annonce);
//        }
//    } catch (SQLException ex) {
//        System.err.println(ex.getMessage());
//    }
//    return annonces;
//}
//
//    
//    //@Override
//    
//public void addlike(annonce annonce) {
//    try {
//        PreparedStatement ps = conn.prepareStatement("UPDATE annonce SET likes = likes + 1 WHERE id_annonce = ?");
//        ps.setInt(1, annonce.getId_annonce());
//        ps.executeUpdate();
//    } catch (SQLException ex) {
//        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
//    }
//}
       

        
    

  //  @Override
    public void disLike(int id_annonce) {

        //req
        String query = "UPDATE `annonce` AS T1,"
                + "      (SELECT `reactions`"
                + "        FROM `annonce` "
                + "        WHERE id_annonce = " + id_annonce + ") AS T2 "
                + "  SET T1.`reactions`=T2.`reactions` - 1 "
                + "WHERE T1.id_annonce =" + id_annonce;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            System.out.println("I DONT\'T LIKE :");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    
   

   public int ajouterannonce(annonce a, int id_user) throws FileNotFoundException, IOException {
    try {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO annonce(type,description,image,id_user) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, a.getType());
        ps.setString(2, a.getDescription());
        
       
        ps.setString(3, a.getImage());
        ps.setInt(4, id_user);
        ps.executeUpdate();
        
        // Récupérer l'id de l'annonce insérée
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id_annonce = rs.getInt(1);
            return id_annonce;
        } else {
            throw new SQLException("L'insertion de l'annonce a échoué, aucun ID généré.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
        return -1;
    }
}


    public List<annonce> rechercheMultiCritere(String type, String description) throws SQLException {
    String query = "SELECT * FROM annonce WHERE 1=1";
    if (!type.isEmpty()) {
        query += " AND type LIKE '%" + type + "%'";
    }
    if (!description.isEmpty()) {
        query += " AND description LIKE '%" + description + "%'";
    }
    List<annonce> annonces = new ArrayList<>();
    try {
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            // Créer un objet Annonce à partir des données de la ligne courante
            annonce a = new annonce();
            // Ajouter l'annonce à la liste des annonces trouvées
            annonces.add(a);
        }
    } catch (SQLException ex) {
        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return annonces;
}

    
  public List<annonce> search(String keyword) throws SQLException {
    String query = "SELECT * FROM annonce WHERE type LIKE '%" + keyword + "%' OR description LIKE '%" + keyword + "%'";
    List<annonce> annonces = new ArrayList<>();
    try {
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            // Créer un objet Annonce à partir des données de la ligne courante
            annonce a = new annonce();
            a.setId_annonce(result.getInt("id_annonce"));
            a.setType(result.getString("type"));
            a.setDate_annonce(result.getDate("date_annonce"));
            a.setDescription(result.getString("description"));
            a.setNblikes(result.getInt("nblikes"));
            //a.setId_user(result.getInt("id_user"));
            // Ajouter l'annonce à la liste des annonces trouvées
            annonces.add(a);
        }
    } catch (SQLException ex) {
        Logger.getLogger(AnnonceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return annonces;
}


    
//    public int countAnnonces() {
//    AnnonceService as = new AnnonceService();
//    return as.count();
//}
    
    
    
    public int countLikes(int id_annonce) throws SQLException {
    int nbLikes = 0;
    String req = "SELECT COUNT(*) FROM reactions WHERE id_annonce = ? AND type = 'like'";
    PreparedStatement ps = conn.prepareStatement(req);
    ps.setInt(1, id_annonce);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        nbLikes = rs.getInt(1);
    }
    rs.close();
    ps.close();
    return nbLikes;
}
    
    
     public int counDisLikes(int id_annonce) throws SQLException {
    int nbLikes = 0;
    String req = "SELECT COUNT(*) FROM reactions WHERE id_annonce = ? AND type = 'dislike'";
    PreparedStatement ps = conn.prepareStatement(req);
    ps.setInt(1, id_annonce);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        nbLikes = rs.getInt(1);
    }
    rs.close();
    ps.close();
    return nbLikes;
}
    
    
    public List<annonce> getAnnoncesTriesParLikes() throws SQLException {
    String sql = "SELECT a.*, COUNT(r.id) AS nblikes " +
                 "FROM annonce a " +
                 "LEFT JOIN reactions r ON a.id_annonce = r.id_annonce AND r.type_react = 'like' " +
                 "GROUP BY a.id_annonce " +
                 "ORDER BY nblikes DESC";
    List<annonce> annonces = new ArrayList<>();
    Connection connection = DataSource.getInstance().getCnx();
         PreparedStatement ps = connection.prepareStatement(sql) ;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            annonce annonce = new annonce(
                rs.getInt("id_annonce"),
                rs.getString("type"),
               
                rs.getString("description"),
                     rs.getDate("date_annonce"),
                    rs.getString("image"),
                rs.getInt("id_user")
                    
                     );
            annonce.setNblikes(rs.getInt("nblikes"));
            annonces.add(annonce);
        }
    
    return annonces;
}

     public List<annonce> trierAnnoncesParDate(List<annonce> annonces)throws SQLException {
    Collections.sort(annonces, (annonce a1, annonce a2) -> a2.getDate_annonce().compareTo(a1.getDate_annonce()));
    return annonces;
}

    
}
