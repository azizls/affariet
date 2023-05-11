
package gui;
import entity.annonce;
import entity.commentaire;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.AnnonceService;
import service.CommentaireService;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;






/**
 * FXML Controller class
 *
 * @author lando
 */
public class HomeController implements Initializable {
    private Connection conn ;
    public static int id_annonce;
     
//private static user id_user1 ;
    private annonce annonceSelectionnee;
    @FXML
    private Button button_supprimer;
    @FXML
    private Button button_pdf;
    @FXML
    private Button button_news;
    @FXML
    private ImageView Image_user;
    
    
    public void setAnnonceSelectionnee(annonce annonceSelectionnee) {
        this.annonceSelectionnee = annonceSelectionnee;
    }
    
    
    @FXML
    private Button button_ajouter;
    
    
    @FXML
    private TableColumn<?, ?> texte_type;
    @FXML
    private TableColumn<?, ?> texte_description;
    private ImageView texte_image;
    private Button texte_button_image;
    @FXML
    private TableView<annonce> table_annonce;
    @FXML
    private TableColumn<?, ?> texte_id_annonce;
    @FXML
    private TableColumn<?, ?> texte_id_user;
    @FXML
    private TableView<commentaire> table_commentaire;
    @FXML
    private TableColumn<?, ?> texte_id_commentaire;
    @FXML
    private TableColumn<?, ?> texte_annonce;
    @FXML
    private TableColumn<?, ?> texte_comment;
    @FXML
    private TableColumn<?, ?> texte_user1;
    @FXML
    private Button button_commenter;
    @FXML
    private Button button_affichercommentaire;
   // TableColumn<annonce, String> texte_image = new TableColumn<>("Image");
    AnnonceService as = new AnnonceService();
     private ObservableList<annonce> annonces;
     
     CommentaireService cs =new CommentaireService();
     private ObservableList<commentaire> commentaires;
    private AnnonceService annonceService;
    private CommentaireService commentaireService;

    /**
     * Initializes the controller class.
     */
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        annonce a= new annonce();
System.out.println(Affariet.userID);
       
        
      this.annonceService = new AnnonceService();
        this.commentaireService = new CommentaireService(); 
        
        
          texte_id_commentaire.setCellValueFactory(new PropertyValueFactory<>("id_commentaire"));
        texte_comment.setCellValueFactory(new PropertyValueFactory<>("coment"));
        texte_user1.setCellValueFactory(new PropertyValueFactory<>("id_user1"));
        
        texte_annonce.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));

         
        texte_id_annonce.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        texte_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        texte_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        texte_id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
      // annonce annonceSelectionnee = table_annonce.getSelectionModel().getSelectedItem();
        
         chargerDonnees();
        try {
            chargerCommentaires();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

         
      
                }

    private void afficherannonce(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("afficherannonce.fxml"));
                Scene scene = new Scene(root);
                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
    }

    @FXML
    private void ajouterannonce(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("addannonce.fxml"));
                Scene scene = new Scene(root);
                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
                 
                 
                 
  

    }

    

    @FXML
private void ajoutercommentaire(ActionEvent event) throws IOException {
    // Récupérer l'annonce sélectionnée
    annonce annonceSelectionnee = table_annonce.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addcommentaire.fxml"));
    Parent root = loader.load();

    AddcommentaireController addCommentaireController = loader.getController();
    
    // Passer l'annonce sélectionnée au controlleur de la page addcommentaire.fxml
    addCommentaireController.setAnnonceSelectionnee(annonceSelectionnee);
    
   
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
}

 
   




    @FXML
    private void getSelection(MouseEvent event) {
       
    TableViewSelectionModel<annonce> selectionModel = table_annonce.getSelectionModel();
    if (!selectionModel.isCellSelectionEnabled()) {
        ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices();
        if (table_annonce.getItems().size() == selectedIndices.size()) {
           
        }
    }
}


    

        private void chargerDonnees() {
   
    List<annonce> listeannonces = as.readAll();
    annonces = FXCollections.observableArrayList(listeannonces);
    table_annonce.setItems(annonces); 
        }
        
        
        
//        
//        
//        
        private void chargerCommentaires() throws SQLException {
    // Récupérer la liste des commentaires
    List<commentaire> list = cs.getCommentairesByAnnonce1(id_annonce);
    commentaires = FXCollections.observableArrayList(list);
    // Ajouter les commentaires à la table
    table_commentaire.setItems(commentaires);
    
    }



    @FXML
    private void affichercommentaire(ActionEvent event) throws IOException, SQLException  {
         annonce annonceSelectionnee = table_annonce.getSelectionModel().getSelectedItem();
       // annonceSelectionnee.getId_annonce();
        texte_id_commentaire.setCellValueFactory(new PropertyValueFactory<>("id_commentaire"));
        texte_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        texte_user1.setCellValueFactory(new PropertyValueFactory<>("id_user1"));
        texte_annonce.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));

        int id_annonce1 = annonceSelectionnee.getId_annonce();
        List<commentaire> list = cs.getCommentairesByAnnonce1(id_annonce1);
        commentaires = FXCollections.observableArrayList(list);
        table_commentaire.setItems(commentaires);
        
    }
    
        
    


   


    @FXML
    private void supprimerannonce(ActionEvent event) {
        
           
        annonce a = table_annonce.getSelectionModel().getSelectedItem();
        if (a != null) {
            as.delete(a);
            annonces.remove(a);
    }
    
    }



@FXML
private void pdf(ActionEvent event) {
    List<annonce> annonces = table_annonce.getItems();

    // Créer un nouveau document PDF
    Document document = new Document();

    try {
        // Ouvrir un flux de sortie vers le fichier PDF
        PdfWriter.getInstance(document, new FileOutputStream("annonces.pdf"));

        // Ouvrir le document PDF
        document.open();

        // Parcourir toutes les annonces et les ajouter au document
        for (annonce a : annonces) {
            String type = a.getType();
            String description = a.getDescription();
            String nomFichier = a.getImage();
            String dossierImages = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\images\\";
            File dossier = new File(dossierImages);       
            File[] fichiers = dossier.listFiles();
            for (File fichier : fichiers) {
                if (fichier.getName().startsWith(nomFichier) && 
                   (fichier.getName().endsWith(".jpg") || 
                    fichier.getName().endsWith(".png") || 
                    fichier.getName().endsWith(".PNG"))) {
                    String cheminImage = fichier.toURI().toString();
                    
                    // Créer une instance de com.itextpdf.text.Image pour l'image
                    com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(new URL(cheminImage));
                    
                    // Créer une cellule pour l'image
                    PdfPCell cell = new PdfPCell(img, true);
                    cell.setBorder(Rectangle.NO_BORDER);

                    // Créer un paragraphe pour l'annonce
                    Paragraph p = new Paragraph("Type: " + type + "\n" + "Description: " + description + "\n\n");

                    // Créer une table pour contenir l'image et le texte
                    PdfPTable table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.setWidths(new int[]{1, 3});
                    table.addCell(cell);
                    table.addCell(p);

                    // Ajouter la table au document
                    document.add(table);
                }
            }
        }

        // Fermer le document PDF
        document.close();
    } catch (DocumentException | IOException e) {
        // Gérer les exceptions ici
    }
}



    @FXML
    private void news(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("NEWS.fxml"));
                Scene scene = new Scene(root);
                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
                 
    }

   @FXML
private void statistique(ActionEvent event) {
    

   DefaultPieDataset dataset = new DefaultPieDataset();


    // Récupérer la liste des annonces depuis votre service ou DAO
    List<annonce> annonces = as.readAll();

    // Compter le nombre d'annonces pour chaque type d'annonce
    int nbVentes = 0, nbLocations = 0, nbEchanges = 0;
    for (annonce a : annonces) {
       switch (a.getType()) {
           case "annonce commerciale":
               nbVentes++;
               break;
           case "avis sur un produit":
               nbLocations++;
               break;
           case "publicité":
               nbEchanges++;
               break;
           default:
               break;
       }
    }

    // Ajouter les données au dataset
    dataset.setValue("annonce commerciale", nbVentes);    
    dataset.setValue("avis sur un produit", nbLocations);
    dataset.setValue("publicité", nbEchanges);


    // Créer le graphique
    JFreeChart chart = ChartFactory.createPieChart(
            "Statistiques des annonces par type", // Titre
            dataset, // Dataset
            true, // Afficher la légende
            true, // Afficher les tooltips
            false // Pas besoin de générer les URLs
    );

    // Créer le ChartPanel
    ChartPanel chartPanel = new ChartPanel(chart);

    // Créer le JFrame et ajouter le ChartPanel
    JFrame frame = new JFrame("Statistiques des annonces par type");
    frame.getContentPane().add(chartPanel);
    frame.pack();
    frame.setVisible(true);

    // Exporter le graphique au format PDF
    try {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("statistiques.pdf"));
        writer.setStrictImageSequence(true);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        Graphics2D g2 = cb.createGraphics(500, 500);
        Shape oldClip = g2.getClip();
        g2.clipRect(0, 0, 500, 500);
        chartPanel.getChart().draw(g2, new java.awt.geom.Rectangle2D.Double(0, 0, 500, 500));
        g2.setClip(oldClip);
        g2.dispose();
        cb.restoreState();
        document.close();
    } catch (FileNotFoundException | DocumentException e) {
   
    }
}

    @FXML
    private void user_back(ActionEvent event) throws IOException {
     
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void produit_back(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("Produit.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
        
    }
    

    @FXML
    private void commande_back(ActionEvent event) throws IOException {
        
          Parent root = FXMLLoader.load(getClass().getResource("listedeTrocPropose.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void evenement_back(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("addev.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void blog_back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }

    @FXML
    private void reclamation_back(ActionEvent event) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("back.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }



}



 


