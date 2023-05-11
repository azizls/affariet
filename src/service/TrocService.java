/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.Commande;
import entity.Produit;
import entity.Troc;
import entity.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;
import java.io.*;

/**
 *
 * @author jawhe
 */
public class TrocService implements IService<Troc>{
    
     private Connection conn;
      public TrocService () {
        conn=DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Troc t) {
//         String requete="insert into troc (id_commande,nom_produitTroc,description_produitTroc,image_produitTroc) values"
//                + "('"+t.getId_commande()+"','"+t.getNom_produitTroc()+"',"+t.getDescription_produitTroc()+"','"+t.getImage_produitTroc()+")";
//        try {
//            Statement ste=conn.createStatement();
//            ste.executeUpdate(requete);
//        } catch (SQLException ex) {
//            Logger.getLogger(CommandeService.class.getName()).
//                    log(Level.SEVERE, null, ex);
//        }  
    }
    public void insertPst(Troc t) {
         
      
        String requete="insert into troc (produit,nom_produitTroc,description_produitTroc,image_produitTroc,user) values (?,?,?,?,?)";
        try {
            
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setInt(1, t.getProduit().getId_produit());
            pst.setString(2, t.getNom_produitTroc());
            pst.setString(3, t.getDescription_produitTroc());
         
            
            pst.setString(4, t.getImage_produitTroc());
           pst.setInt(5, t.getuser().getId_user());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TrocService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    
    }
    
    public void delete(int t) {
        String requete="delete from troc where id_troc = "+t;
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(TrocService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Troc t) {
         String requete ="update troc set nom_produitTroc=? ,description_produitTroc=? ,image_produitTroc=? where id_troc="+t.getId_troc();
         try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, t.getNom_produitTroc());
            pst.setString(2, t.getDescription_produitTroc());
            pst.setString(3, t.getImage_produitTroc());
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TrocService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Troc> readAll() {
         String requete ="SELECT troc.*, produit.id_produit, produit.nom_produit, produit.description_produit, produit.prix_produit, user.id_user ,user.nom FROM troc INNER JOIN produit ON troc.produit = produit.id_produit INNER JOIN user ON troc.user = user.id_user  ";
        List<Troc> list=new ArrayList<>();
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               
               //FileOutputStream fos = new FileOutputStream("image.jpg");
               Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
               user u=new user(rs.getInt("id_user"), rs.getString("nom"));
               Troc t=new Troc
                
        (rs.getInt("id_troc"),p,rs.getString("nom_produitTroc"),rs.getString("description_produitTroc"),u);
               list.add(t);
                       
           }
           
        } catch (SQLException ex) {
            Logger.getLogger(TrocService.class.getName()).log(Level.SEVERE, null, ex);
       } 
        return list;
    }
    public List<Troc> readByUserName(String name) {
    String requete = "SELECT troc.*, produit.id_produit, produit.nom_produit, produit.description_produit, produit.prix_produit, user.id_user,user.nom "
            + "FROM troc "
            + "INNER JOIN produit ON troc.produit = produit.id_produit "
            + "INNER JOIN user ON troc.user = user.id_user "
            + "WHERE user.nom='" + name + "'";
    List<Troc> list=new ArrayList<>();
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               
               //FileOutputStream fos = new FileOutputStream("image.jpg");
               Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
               user u=new user(rs.getInt("id_user"), rs.getString("nom"));
               Troc t=new Troc
                
        (rs.getInt("id_troc"),p,rs.getString("nom_produitTroc"),rs.getString("description_produitTroc"),u);
               list.add(t);
                       
           }
           
        } catch (SQLException ex) {
            Logger.getLogger(TrocService.class.getName()).log(Level.SEVERE, null, ex);
       } 
        return list;
}
    @Override
    public Troc readById(int id) {
        String requete ="SELECT troc.*, produit.id_produit, produit.nom_produit, produit.description_produit, produit.prix_produit, user.id_user FROM troc INNER JOIN produit ON troc.produit = produit.id_produit INNER JOIN user ON troc.user = user.id_user where id_troc="+id;
       Troc t=new Troc();
       List<Troc> list=new ArrayList<>();
       try {
             Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               user u=new user(rs.getInt("id_user"));
                Produit p=new Produit(rs.getInt("id_Produit"),rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit"));
           t.setId_troc(rs.getInt(1));
            
           t.setNom_produitTroc(rs.getString(2));
           t.setDescription_produitTroc(rs.getString(3));
           t.setImageData(rs.getBytes(4));
           t.setuser(u);
           t.setProduit(p);
           //c.setId_produit(rs.getInt(1));
           }
           
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return t;
    }

   
    public int insertretrieveid(Troc t) {
        String requete="insert into troc (produit,nom_produitTroc,description_produitTroc,image_produitTroc,user) values (?,?,?,?,?)";
        int id=0;
        try {
            
            PreparedStatement pst=conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, t.getProduit().getId_produit());
            pst.setString(2, t.getNom_produitTroc());
            pst.setString(3, t.getDescription_produitTroc());
          
            
            pst.setString(4, t.getImage_produitTroc());
            pst.setInt(5, t.getuser().getId_user());
           
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
    public void delete(Troc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Troc get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}