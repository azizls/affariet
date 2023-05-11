/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.Produit;
import entity.Categorie;
import entity.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 21656
 */
public class ProduitService implements IService<Produit> {
    
    private Connection conn;

    public ProduitService() {
        conn=DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Produit t) {
        String requete="insert into produit (nom_produit,description_produit,prix_produit,image_produit,id_categorie) values (?,?,?,?,?)";
        try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, t.getNom_produit());
            pst.setString(2, t.getDescription_produit());
            pst.setFloat(3, t.getPrix_produit());
            pst.setString(4, t.getImage_produit());
            pst.setInt(5, t.getId_categorie().getId_categorie());
           // FileInputStream fis = new FileInputStream(t.getImage_produit());
//      byte[] imageData = new byte[fis.available()];
//      fis.read(imageData);
//      fis.close();
            
//            pst.setBytes(4, imageData);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
 
        } 
    }

    @Override
    public void delete(Produit t) {
        String requete="delete from produit where id_produit = "+t.getId_produit();
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Produit t) {
        
        try {
    PreparedStatement prt = conn.prepareStatement("update produit set nom_produit=?, description_produit=?, prix_produit=? where id_produit=?");
    prt.setString(1, t.getNom_produit());
    prt.setString(2, t.getDescription_produit());
    prt.setFloat(3, t.getPrix_produit());
  //  prt.setInt(4, t.getId_categorie());
    prt.setInt(4, t.getId_produit());
    prt.executeUpdate();
}       catch (SQLException ex) {
    Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
}
    }

    @Override
    public List<Produit> readAll() {
    String requete = "SELECT produit.*, categorie.id_categorie, categorie.nom_categorie,user.id_user,user.nom,user.email  FROM produit INNER JOIN categorie ON produit.id_categorie = categorie.id_categorie INNER JOIN user ON produit.id_user=user.id_user";
    List<Produit> list = new ArrayList<>();
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            user u=new user(rs.getInt("id_user"),rs.getString("nom"),rs.getString("email"));
            Categorie c = new Categorie();
            c.setId_categorie(rs.getInt("id_categorie"));
            c.setNom_categorie(rs.getString("nom_categorie"));
            Produit p = new Produit(rs.getInt("id_produit"), rs.getString("nom_produit"),rs.getString("description_produit"),rs.getFloat("prix_produit") ,c,u);
            list.add(p);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

    @Override
    public Produit readById(int id) {
       
Produit produit = null;
    String requete = "select * from produit where id_produit = " + id;
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            CategorieService categorieService = new CategorieService();
            user u=new user();
            UserService us=new UserService();
            u=us.readById(rs.getInt("id_user") );
            Categorie categorie = categorieService.readById(rs.getInt("id_categorie"));
            produit = new Produit(
                rs.getInt("id_produit"),
                rs.getString("nom_produit"),
                rs.getString("description_produit"),
                rs.getFloat("prix_produit"),
                //rs.getString("image_produit"),
                categorie,u
            );
        }
    } catch (SQLException ex) {
        Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (produit != null) {
        System.out.println(produit);
    } else {
        System.out.println("Produit not found");
    }
    return produit;
    
}

   
    public int insertretrieveid(Produit t) {
          int id=0;
         String requete="insert into produit (nom_produit,description_produit,prix_produit,image_produit,id_categorie,id_user) values (?,?,?,?,?,?)";
        try {
           PreparedStatement pst=conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, t.getNom_produit());
            pst.setString(2, t.getDescription_produit());
            pst.setFloat(3, t.getPrix_produit());
            pst.setString(4, t.getImage_produit());
            pst.setInt(5, t.getId_categorie().getId_categorie());
            pst.setInt(6, t.getuser().getId_user());
            int rowsInserted =pst.executeUpdate();
           ResultSet rs = pst.getGeneratedKeys();
    if (rs.next()) {
        id = rs.getInt(1);
        System.out.println("Inserted row with ID = " + id);
    }
           
            
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    
    public List<Produit> getByNom(String nom) {
            List<Produit> produits = new ArrayList<>();
    String query = "SELECT * FROM produit WHERE nom_produit LIKE ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, "%" + nom + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Produit produit = new Produit();
            produit.setId_produit(rs.getInt("id_produit"));
            produit.setNom_produit(rs.getString("nom_produit"));
            produit.setDescription_produit(rs.getString("description_produit"));
            produit.setPrix_produit(rs.getFloat("prix_produit"));
            //produit.setId_categorie(new Categorie(rs.getInt("id_categorie")));
            produits.add(produit);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return produits;
    
}
//    public Produit getProduitPlusCher() {
//    Produit produitPlusCher = null;
//    float maxPrix = Float.MIN_VALUE;
//    for (Produit produit : listeProduits) {
//        if (produit.getPrix_produit() > maxPrix) {
//            maxPrix = produit.getPrix_produit();
//            produitPlusCher = produit;
//        }
//    }
//    return produitPlusCher;
//}
    
    public Map<String, Produit> findMostExpensiveProducts(List<Categorie> categories) {
        Map<String, Produit> mostExpensiveProducts = new HashMap<>();
        
        // Iterate through each category
        for (Categorie category : categories) {
            List<Produit> products = retrieveProductsByCategory(category.getId_categorie());
            Produit mostExpensiveProduct = null;
            
            // Iterate through each product in the category and find the most expensive one
            for (Produit product : products) {
                if (mostExpensiveProduct == null || product.getPrix_produit() > mostExpensiveProduct.getPrix_produit()) {
                    mostExpensiveProduct = product;
                }
            }
            
            // Store the most expensive product for the category in the map
            if (mostExpensiveProduct != null) {
                mostExpensiveProducts.put(category.getNom_categorie(), mostExpensiveProduct);
            }
        }
        
        return mostExpensiveProducts;
    }
    
    // Method to retrieve all products belonging to a category from the database
    private List<Produit> retrieveProductsByCategory(int categoryId) {
        // TODO: Implement code to retrieve products by category from the database
        return new ArrayList<>();
    }
        
    
    public static Categorie getCategoryWithMostProducts(List<Produit> produits) {
        Map<Categorie, Integer> categoryCounts = new HashMap<>();

        for (Produit produit : produits) {
            Categorie categorie = produit.getId_categorie();
            if (!categoryCounts.containsKey(categorie)) {
                categoryCounts.put(categorie, 1);
            } else {
                categoryCounts.put(categorie, categoryCounts.get(categorie) + 1);
            }
        }

        int maxCount = 0;
        Categorie categoryWithMostProducts = null;
        for (Map.Entry<Categorie, Integer> entry : categoryCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                categoryWithMostProducts = entry.getKey();
            }
        }

        return categoryWithMostProducts;
    }

    @Override
    public Produit get_annonce(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<Produit> getByUser(int loggedInUser) {
     
        
        List<Produit> produits = new ArrayList<>();
    String query = "SELECT * FROM produit WHERE user = ?";
    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
        preparedStatement.setInt(1, loggedInUser);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id_produit");
            String nom = resultSet.getString("nom_produit");
            String description = resultSet.getString("description_produit");
 		float prix = resultSet.getFloat("prix_produit");
               String image = resultSet.getString("image_produit");
            Produit p = new Produit(id, nom, description, prix, image, loggedInUser);
            produits.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return produits;
        
        
    }
    
}