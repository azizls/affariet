/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author 21656
 */
public class CategorieService implements IService<Categorie>{
    private Connection conn;

    public CategorieService() {
        conn=DataSource.getInstance().getCnx();
    }
    
    
    
    
    @Override
    public void insert(Categorie c) {
        String requete="insert into categorie (nom_categorie) values"
                + "('"+c.getNom_categorie()+"')";
        try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Categorie c) {
        String requete="delete from categorie where id_categorie = "+c.getId_categorie();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }

    @Override
    public void update(Categorie c) {
        String requete ="update categorie set nom_categorie=? where id_categorie="+c.getId_categorie();
         try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, c.getNom_categorie());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Categorie> readAll() {
        String requete ="select * from categorie";
        List<Categorie> list=new ArrayList<>();
        try {
            Statement st=conn.createStatement();
           ResultSet rs= st.executeQuery(requete);
           while(rs.next()){
               Categorie p=new Categorie
        (rs.getInt("id_categorie"), rs.getString("nom_categorie"));
               list.add(p);
                       
           }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    public List<String> readnotAll() {
    String requete = "SELECT nom_categorie FROM categorie";
    List<String> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            String categoryName = rs.getString("nom_categorie");
            list.add(categoryName);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

    @Override
    public Categorie readById(int id) {
        Categorie categorie = null;
    String requete ="select * from categorie where id_categorie = " + id;
    try {
        Statement st=conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if(rs.next()){
            categorie = new Categorie(rs.getInt("id_categorie"), rs.getString("nom_categorie"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (categorie != null) {
        System.out.println(categorie);
    } else {
        System.out.println("Categorie not found");
    }
    return categorie;
    }

   
    
   public List<Categorie> getByNom(String nom) {
        String requete = "SELECT * FROM categorie WHERE nom_categorie LIKE ?";
    List<Categorie> list = new ArrayList<>();
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setString(1, "%" + nom + "%");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Categorie categorie = new Categorie(rs.getInt("id_categorie"), rs.getString("nom_categorie"));
            list.add(categorie);
        }
    } catch (SQLException ex) {
        Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
    }

    @Override
    public Categorie get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    
   
}
