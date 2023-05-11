/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.Commande;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;
import entity.Produit;
import entity.user;
import java.time.LocalDate;
/**
 *
 * @author jawhe
 */
public class CommandeService implements IService<Commande>{
     private Connection conn;
      public CommandeService () {
        conn=DataSource.getInstance().getCnx();
    }
    @Override
    public void insert(Commande t) {
         String requete="insert into commande (id_produit,user) values"
                + "('"  + t.getId_produit().getId_produit() + "'," + t.getuser().getId_user() + ")";
        try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete);
           
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).
                    log(Level.SEVERE, null, ex);
        }  
    }

    public void delete(int t) {
        String requete="delete from commande where id_commande = "+t;
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Commande t) {
        String requete ="update commande set methode=? where id_commande="+t.getId_commande();
         try {
            PreparedStatement pst=conn.prepareStatement(requete);
            
            pst.setString(1, t.getMethode());
            //pst.setInt(3, t.getId_produit().getId_produit());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public Commande readuserName(String nom) {
       String requete ="SELECT commande.*, produit.id_produit, produit.nom_produit, produit.description_produit, produit.prix_produit, user.id_user, user.nom\n" +
"FROM commande \n" +
"INNER JOIN produit ON commande.id_produit = produit.id_produit \n" +
"INNER JOIN user ON commande.user = user.id_user \n" +
"WHERE user.nom LIKE '%" + nom + "%'";
       Commande c=new Commande();
       List<Commande> list=new ArrayList<>();
       try {
             Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
                Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
           c.setId_commande(rs.getInt(1));
            
           
           c.setId_produit(p);
           //c.setId_produit(rs.getInt(1));
           }
           
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return c;
    }
    @Override
    public Commande readById(int id) {
       String requete ="SELECT commande.*, produit.id_produit,produit.nom_produit,produit.description_produit,produit.prix_produit ,user.id_user FROM commande INNER JOIN produit ON commande.id_produit = produit.id_produit INNER JOIN user ON commande.user = user.id_user where id_commande="+id;
       Commande c=new Commande();
       List<Commande> list=new ArrayList<>();
       try {
             Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
                Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
           c.setId_commande(rs.getInt(1));
            
           
           c.setId_produit(p);
           //c.setId_produit(rs.getInt(1));
           }
           
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return c;
    }

    public List<Commande> readAllYear(int year) {
    String requete = "SELECT YEAR(date_commande) AS id_commande_year, commande.*, produit.id_produit,produit.nom_produit,produit.description_produit,produit.prix_produit,user.id_user,user.nom FROM commande INNER JOIN produit ON commande.id_produit = produit.id_produit INNER JOIN user ON commande.user = user.id_user WHERE YEAR(date_commande) = " + year;
    List<Commande> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            user u = new user(rs.getInt("id_user"), rs.getString("nom"));
            Produit p = new Produit(rs.getInt("id_Produit"), rs.getString("nom_produit"), rs.getString("description_produit"), rs.getFloat("prix_produit"));
            Commande c = new Commande(rs.getInt("id_commande_year"), u, p, rs.getDate("date_commande"));
            list.add(c);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
    public List<Commande> readAllYearAndMonth(int year, int month) {
    String requete = "SELECT YEAR(date_commande) AS id_commande_year, MONTH(date_commande) AS id_commande_month, commande.*, produit.id_produit,produit.nom_produit,produit.description_produit,produit.prix_produit,user.id_user,user.nom FROM commande INNER JOIN produit ON commande.id_produit = produit.id_produit INNER JOIN user ON commande.user = user.id_user WHERE YEAR(date_commande) = " + year + " AND MONTH(date_commande) = " + month;
    List<Commande> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            user u = new user(rs.getInt("id_user"), rs.getString("nom"));
            Produit p = new Produit(rs.getInt("id_Produit"), rs.getString("nom_produit"), rs.getString("description_produit"), rs.getFloat("prix_produit"));
            Commande c = new Commande(rs.getInt("id_commande_year"), u, p, rs.getDate("date_commande"));
            list.add(c);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
    public List<Commande> readAllMonth(int month) {
    String requete = "SELECT YEAR(date_commande) AS id_commande_year, MONTH(date_commande) AS id_commande_month, commande.*, produit.id_produit,produit.nom_produit,produit.description_produit,produit.prix_produit,user.id_user,user.nom FROM commande INNER JOIN produit ON commande.id_produit = produit.id_produit INNER JOIN user ON commande.user = user.id_user WHERE MONTH(date_commande) = " + month;
    List<Commande> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            user u = new user(rs.getInt("id_user"), rs.getString("nom"));
            Produit p = new Produit(rs.getInt("id_Produit"), rs.getString("nom_produit"), rs.getString("description_produit"), rs.getFloat("prix_produit"));
            Commande c = new Commande(rs.getInt("id_commande_year"), u, p, rs.getDate("date_commande"));
            list.add(c);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
    public int insertretrieveid(Commande t) {
         String requete="insert into commande (id_produit,user,date_commande,charge_id) values(?,?,?,?) ";
         int id=0;
        try {
            
            PreparedStatement pst=conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            
            pst.setInt(1, t.getId_produit().getId_produit());
            pst.setInt(2, t.getuser().getId_user());
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            pst.setString(4,t.getCharge_id());
            int rowsInserted =pst.executeUpdate();
           ResultSet rs = pst.getGeneratedKeys();
    if (rs.next()) {
        id = rs.getInt(1);
        System.out.println("Inserted row with ID = " + id);
    }
           
            
        } catch (SQLException ex) {
            Logger.getLogger(TrocService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public List<Commande> readAll() {
         String requete ="SELECT commande.*, produit.id_produit,produit.nom_produit,produit.description_produit,produit.prix_produit,user.id_user FROM commande INNER JOIN produit ON commande.id_produit = produit.id_produit INNER JOIN user ON commande.user = user.id_user";
        List<Commande> list=new ArrayList<>();
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               user u=new user(rs.getInt("id_user"));
               Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
               Commande c=new Commande
        (rs.getInt("id_commande"),u,p);
               list.add(c);
                       
           }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

   
    public List<Commande> readAllT(Commande t) {
        String requete ="SELECT commande.*, produit.id_produit,produit.nom_produit,produit.description_produit,produit.prix_produit,user.id_user FROM commande INNER JOIN produit ON commande.id_produit = produit.id_produit INNER JOIN user ON commande.user = user.id_user";
        List<Commande> list=new ArrayList<>();
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
              user u=new user(rs.getInt("id_user"));
               Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
               Commande c=new Commande
        (rs.getInt("id_commande"),u,p);
               list.add(c);
                       
           }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

   
    public List<Commande> read() {
        String requete ="SELECT commande.*, produit.id_produit,produit.nom_produit,produit.description_produit,produit.prix_produit,user.id_user FROM commande INNER JOIN produit ON commande.id_produit = produit.id_produit INNER JOIN user ON commande.user = user.id_user";
        List<Commande> list=new ArrayList<>();
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               user u=new user(rs.getInt("id_user"));
               Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
               Commande c=new Commande
        (rs.getInt("id_commande"),u,p);
               list.add(c);
                       
           }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void delete(Commande t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Commande get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public List<Commande> readAllById(int id) {
        String requete ="SELECT commande.*, produit.id_produit, produit.nom_produit, produit.description_produit, produit.prix_produit, user.id_user " +
                "FROM commande " +
                "INNER JOIN produit ON commande.id_produit = produit.id_produit " +
                "INNER JOIN user ON commande.user = user.id_user " +
                "WHERE commande.user = "+id;
        List<Commande> list=new ArrayList<>();
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               user u=new user(rs.getInt("id_user"));
               Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
               Commande c=new Commande
        (rs.getInt("id"),u,p,rs.getDate("date_commande"));
               list.add(c);
                       
           }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
