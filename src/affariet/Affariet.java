/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package affariet;
import entity.annonce;
import entity.role;
import entity.commentaire;
import entity.role;
import entity.user;
import java.sql.SQLException;
import utils.DataSource;
import java.util.Date;
import java.util.List;
import service.AnnonceService;
import service.CommentaireService;
/**
 *
 * @author lando
 */
public class Affariet {
    private static int id_user1 ;

    private static int id_annonce;

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
//        DataSource ds1 = DataSource.getInstance();
//        System.out.println(ds1);
//        DataSource ds2 = DataSource.getInstance();
//        System.out.println(ds2);
//
//        DataSource ds3 = DataSource.getInstance();
//        System.out.println(ds3);

       DataSource ds1 = DataSource.getInstance();
       Date date = new Date();
        annonce a1;
        //System.out.println("Titre de l'annonce : " + a1.getType());
        //System.out.println("Description de l'annonce : " + a1.getDescription());
        //a1 = new annonce("test", "test", date);
        
       role r1 = new role(1,"admin");
        
      //  user u1 = new user(1,"landolsi","aziz",23,"aziz@aziz.com","1234",99319146,"manar2",r1);
        //user u2 =new user (2,"aaaa","aaaa",23,"aaa@aa","aaaa",1234,"aaaa",r1);
        //String nom, String prenom, int age, String email, String mdp, int numero, String adresse, role id_role
        //annonce a4 = new annonce(16,"today", "tes",date,u1);
    
    AnnonceService as= new AnnonceService() {} ;
    CommentaireService cs= new CommentaireService() {} ;
    
    commentaire c1;
    commentaire c2;
        
       // c1 = new commentaire("test",a4);
      //  c2 = new commentaire("kkk",a4,u2);
//
        // cs.insert(c2);
        //as.insert(a4);
//as.delete(a4);
      
        //as.update(a4);
        //as.readAll().forEach(System.out::println);
//  annonce a = new annonce();
//    a.setId_annonce(19);
//    a.setType("marhbee bik");
//    a.setDescription("tfadhel khouya");
//     a.setLikes( 2);
//    a.setDate_annonce(new java.util.Date(System.currentTimeMillis()));
// a.setId_user(u1);
//    as.update(a);
//     //commentaire c = new commentaire();
//    c.getId_annonce(7);
//    c.setComment("comment updated");
//    //a.setDescription("Description updated");
//    //a.setDate_annonce(new java.util.Date(System.currentTimeMillis()));
//
//    cs.update(c);
        // annonce a = new annonce();
//a.setId_annonce(1);
//a.setType("talvza");
//a.setDescription("mise à jour");
//
//a.setDate_annonce(java.sql.Date.valueOf("2022-02-01"));
//AnnonceService as = new AnnonceService();
//as.update(a);
//annonce a = as.readById(16); // récupérer le commentaire à partir de son id
//c.setComment("Nouveau commentaire"); // mettre à jour le commentaire
//cs.update(c); // enregistrer les modifications
//AnnonceService as = new AnnonceService();
//List<annonce> annonce = as.readAll();
//as.readAll().forEach(System.out::println);
//List<annonce> annonce = as.readAll().f;
// supposons que vous avez appelé votre méthode readAll() pour récupérer tous les commentaires et annonces avec les utilisateurs correspondants



    }
}