/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.annonce;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.AnnonceService;
import service.CommentaireService;

/**
 * FXML Controller class
 *
 * @author lando
 */
public class FrontannonceCrontroller implements Initializable {

    @FXML
    private ComboBox<String> bouton_filtrer;
    @FXML
    private ImageView Image_user;
    public void initData(List<annonce> annonces) {
    this.annonces = annonces;
}


    @FXML
    private Pane pnl_annonce;
    @FXML
    private ScrollPane scrollpane_Salle;
    @FXML
    private HBox hbox_annonce;

   static int id_annonce=0;
    private AnnonceService  as = new AnnonceService();
     private CommentaireService  cs = new CommentaireService();
    private int indiceannonce=0;
    private int taille_annonce=0 ;
    @FXML
    private Button button_news;
    @FXML
    private Button button_publier;
    @FXML
    private TextField texte_recherche;
    @FXML
    private Button button_recherche;
    private List<annonce> annonces;
    
   
    /**
     * Initializes the controller class.
     */
public void initialize(URL url, ResourceBundle rb) {
    List<annonce> annonces = new ArrayList<>();
    String recherche = texte_recherche.getText().trim();
    annonces = as.readAll();
    taille_annonce = annonces.size();
    Node[] nodes_annonce = new Node[taille_annonce];
    scrollpane_Salle.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    hbox_annonce.getChildren().clear();
    ObservableList<Node> children = hbox_annonce.getChildren();
    int count = 0;
    HBox ligneAnnonces = new HBox();
    ligneAnnonces.setSpacing(10);
    for (indiceannonce = 0; indiceannonce < taille_annonce; indiceannonce++) {
        try {
            id_annonce = annonces.get(indiceannonce).getId_annonce();
            nodes_annonce[indiceannonce] = FXMLLoader.load(getClass().getResource("/gui/itemannonce.fxml"));
            ligneAnnonces.getChildren().add(nodes_annonce[indiceannonce]);
            count++;
            if (count == 3) {
                children.add(ligneAnnonces);
                ligneAnnonces = new HBox();
                ligneAnnonces.setSpacing(10);
                count = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (count > 0) {
        children.add(ligneAnnonces);
    }
    if (count > 0 && count < 3) {
        // Ajouter manuellement la dernière ligne si elle contient moins de 3 annonces
       // children.add(ligneAnnonces);
    }

    int itemsPerPage = 3;
    int numPages = (int) Math.ceil((double) taille_annonce / itemsPerPage);
    Pagination pagination = new Pagination(numPages, 0);
    pagination.setPageCount(numPages);
    pagination.setPageFactory((Integer pageIndex) -> {
        HBox pageAnnonces = new HBox();
        pageAnnonces.setSpacing(10);
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, taille_annonce);
        List<Node> currentPageNodes = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageNodes.add(nodes_annonce[i]);
        }
        pageAnnonces.getChildren().addAll(currentPageNodes);
        return pageAnnonces;
    });
    scrollpane_Salle.setContent(pagination);
    
    
    
    
    
    
    
   bouton_filtrer.getItems().addAll(
            "les plus récentes",
            "les plus aimées"
        );
        bouton_filtrer.setPromptText("Filtrer par...");
        bouton_filtrer.setOnAction(event -> {
            String selectedItem = bouton_filtrer.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("les plus récentes")) {
                    try {
                        boutonLesPlusRecentesClicked(event);
                    } catch (SQLException ex) {
                        Logger.getLogger(FrontannonceCrontroller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (selectedItem.equals("les plus aimées")) {
                    try {
                        boutonLesPlusAimeesClicked(event);
                    } catch (SQLException ex) {
                        Logger.getLogger(FrontannonceCrontroller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
    }
    
    
    
    



    @FXML
    private void news(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NEWS.fxml"));
                Scene scene = new Scene(root);
                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
    }

    @FXML
    private void publier(ActionEvent event) throws IOException {
        
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addannoncefront.fxml"));
    Parent root = loader.load();
    AddannoncefrontController annonceController = loader.getController();
    
  
   // commenterController.setItemannonceController(this);
    Scene scene = button_publier.getScene();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
        
    }

@FXML
private void rechercher(ActionEvent event) throws SQLException {
    String recherche = texte_recherche.getText().trim();
    List<annonce> annonces = new ArrayList<>();
    if (recherche.isEmpty()) {
        annonces = as.readAll();
    } else {
      
            annonces = as.search(recherche);
        
    
    int taille_annonce = annonces.size();
    Node[] nodes_annonce = new Node[taille_annonce];
    hbox_annonce.getChildren().clear();
    ObservableList<Node> children = hbox_annonce.getChildren();
    int count = 0;
    HBox ligneAnnonces = new HBox();
    ligneAnnonces.setSpacing(10);
    for (int i = 0; i < taille_annonce; i++) {
        try {
          id_annonce = annonces.get(i).getId_annonce();
            nodes_annonce[i] = FXMLLoader.load(getClass().getResource("itemannonce.fxml"));
            ligneAnnonces.getChildren().add(nodes_annonce[i]);
            count++;
            if (count == 3) {
                children.add(ligneAnnonces);
                ligneAnnonces = new HBox();
                ligneAnnonces.setSpacing(10);
                count = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (count != 0) {
        children.add(ligneAnnonces);
    }

    int itemsPerPage = 3; //Assuming 3 items per row
    int numPages = (int) Math.ceil((double) taille_annonce / itemsPerPage);
    Pagination pagination = new Pagination(numPages, 0);
    pagination.setPageFactory((Integer pageIndex) -> {
        HBox pageAnnonces = new HBox();
        pageAnnonces.setSpacing(10);
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, taille_annonce);
        List<Node> currentPageNodes = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageNodes.add(nodes_annonce[i]);
        }
        pageAnnonces.getChildren().addAll(currentPageNodes);
        return pageAnnonces;
    });
    scrollpane_Salle.setContent(pagination);
}}

private void trierselonlike(ActionEvent event) throws SQLException {
   
        List<annonce> annonces = as.getAnnoncesTriesParLikes();
       // hbox_annonce.getChildren().clear(); // Effacer les annonces précédentes
         int taille_annonce = annonces.size();
    Node[] nodes_annonce = new Node[taille_annonce];
    hbox_annonce.getChildren().clear();
    ObservableList<Node> children = hbox_annonce.getChildren();
    int count = 0;
    HBox ligneAnnonces = new HBox();
    ligneAnnonces.setSpacing(10);
    for (int i = 0; i < taille_annonce; i++) {
        try {
          id_annonce = annonces.get(i).getId_annonce();
            nodes_annonce[i] = FXMLLoader.load(getClass().getResource("itemannonce.fxml"));
            ligneAnnonces.getChildren().add(nodes_annonce[i]);
            count++;
            if (count == 3) {
                children.add(ligneAnnonces);
                ligneAnnonces = new HBox();
                ligneAnnonces.setSpacing(10);
                count = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (count != 0) {
        children.add(ligneAnnonces);
    }

    int itemsPerPage = 9; //Assuming 3 items per row
    int numPages = (int) Math.ceil((double) taille_annonce / itemsPerPage);
    Pagination pagination = new Pagination(numPages, 0);
    pagination.setPageFactory((Integer pageIndex) -> {
        HBox pageAnnonces = new HBox();
        pageAnnonces.setSpacing(10);
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, taille_annonce);
        List<Node> currentPageNodes = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageNodes.add(nodes_annonce[i]);
        }
        pageAnnonces.getChildren().addAll(currentPageNodes);
        return pageAnnonces;
    });
    scrollpane_Salle.setContent(pagination);
}

    @FXML
    private void affichermesannonces(ActionEvent event) {
        
         List<annonce> annonces = as.get_annonces_by_user(Affariet.userID);
       // hbox_annonce.getChildren().clear(); // Effacer les annonces précédentes
         int taille_annonce = annonces.size();
    Node[] nodes_annonce = new Node[taille_annonce];
    hbox_annonce.getChildren().clear();
    ObservableList<Node> children = hbox_annonce.getChildren();
    int count = 0;
    HBox ligneAnnonces = new HBox();
    ligneAnnonces.setSpacing(10);
    for (int i = 0; i < taille_annonce; i++) {
        try {
          id_annonce = annonces.get(i).getId_annonce();
            nodes_annonce[i] = FXMLLoader.load(getClass().getResource("itemannonce.fxml"));
            ligneAnnonces.getChildren().add(nodes_annonce[i]);
            count++;
            if (count == 3) {
                children.add(ligneAnnonces);
                ligneAnnonces = new HBox();
                ligneAnnonces.setSpacing(10);
                count = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (count != 0) {
        children.add(ligneAnnonces);
    }

    int itemsPerPage = 3; //Assuming 3 items per row
    int numPages = (int) Math.ceil((double) taille_annonce / itemsPerPage);
    Pagination pagination = new Pagination(numPages, 0);
    pagination.setPageFactory((Integer pageIndex) -> {
        HBox pageAnnonces = new HBox();
        pageAnnonces.setSpacing(10);
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, taille_annonce);
        List<Node> currentPageNodes = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageNodes.add(nodes_annonce[i]);
        }
        pageAnnonces.getChildren().addAll(currentPageNodes);
        return pageAnnonces;
    });
    scrollpane_Salle.setContent(pagination);
    }



    private void boutonLesPlusRecentesClicked(ActionEvent event) throws SQLException {
         List<annonce> annonces = as.readAll();
           annonces =as.trierAnnoncesParDate(annonces);
       // hbox_annonce.getChildren().clear(); // Effacer les annonces précédentes
         int taille_annonce = annonces.size();
    Node[] nodes_annonce = new Node[taille_annonce];
    hbox_annonce.getChildren().clear();
    ObservableList<Node> children = hbox_annonce.getChildren();
    int count = 0;
    HBox ligneAnnonces = new HBox();
    ligneAnnonces.setSpacing(10);
    for (int i = 0; i < taille_annonce; i++) {
        try {
          id_annonce = annonces.get(i).getId_annonce();
            nodes_annonce[i] = FXMLLoader.load(getClass().getResource("itemannonce.fxml"));
            ligneAnnonces.getChildren().add(nodes_annonce[i]);
            count++;
            if (count == 3) {
                children.add(ligneAnnonces);
                ligneAnnonces = new HBox();
                ligneAnnonces.setSpacing(10);
                count = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (count != 0) {
        children.add(ligneAnnonces);
    }

    int itemsPerPage = 9; //Assuming 3 items per row
    int numPages = (int) Math.ceil((double) taille_annonce / itemsPerPage);
    Pagination pagination = new Pagination(numPages, 0);
    pagination.setPageFactory((Integer pageIndex) -> {
        HBox pageAnnonces = new HBox();
        pageAnnonces.setSpacing(10);
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, taille_annonce);
        List<Node> currentPageNodes = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageNodes.add(nodes_annonce[i]);
        }
        pageAnnonces.getChildren().addAll(currentPageNodes);
        return pageAnnonces;
    });
    scrollpane_Salle.setContent(pagination);
        
        
    }
    private void boutonLesPlusAimeesClicked(ActionEvent event) throws SQLException {
        
         List<annonce> annonces = as.getAnnoncesTriesParLikes();
       // hbox_annonce.getChildren().clear(); // Effacer les annonces précédentes
         int taille_annonce = annonces.size();
    Node[] nodes_annonce = new Node[taille_annonce];
    hbox_annonce.getChildren().clear();
    ObservableList<Node> children = hbox_annonce.getChildren();
    int count = 0;
    HBox ligneAnnonces = new HBox();
    ligneAnnonces.setSpacing(10);
    for (int i = 0; i < taille_annonce; i++) {
        try {
          id_annonce = annonces.get(i).getId_annonce();
            nodes_annonce[i] = FXMLLoader.load(getClass().getResource("itemannonce.fxml"));
            ligneAnnonces.getChildren().add(nodes_annonce[i]);
            count++;
            if (count == 3) {
                children.add(ligneAnnonces);
                ligneAnnonces = new HBox();
                ligneAnnonces.setSpacing(10);
                count = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (count != 0) {
        children.add(ligneAnnonces);
    }

    int itemsPerPage = 3; //Assuming 3 items per row
    int numPages = (int) Math.ceil((double) taille_annonce / itemsPerPage);
    Pagination pagination = new Pagination(numPages, 0);
    pagination.setPageFactory((Integer pageIndex) -> {
        HBox pageAnnonces = new HBox();
        pageAnnonces.setSpacing(10);
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, taille_annonce);
        List<Node> currentPageNodes = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageNodes.add(nodes_annonce[i]);
        }
        pageAnnonces.getChildren().addAll(currentPageNodes);
        return pageAnnonces;
    });
    scrollpane_Salle.setContent(pagination);
        
    }

    @FXML
    private void actualiserContenu(ActionEvent event) {
          List<annonce> annonces = new ArrayList<>();
    String recherche = texte_recherche.getText().trim();
    annonces = as.readAll();
    taille_annonce = annonces.size();
    Node[] nodes_annonce = new Node[taille_annonce];
    scrollpane_Salle.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    hbox_annonce.getChildren().clear();
    ObservableList<Node> children = hbox_annonce.getChildren();
    int count = 0;
    HBox ligneAnnonces = new HBox();
    ligneAnnonces.setSpacing(10);
    for (indiceannonce = 0; indiceannonce < taille_annonce; indiceannonce++) {
        try {
            id_annonce = annonces.get(indiceannonce).getId_annonce();
            nodes_annonce[indiceannonce] = FXMLLoader.load(getClass().getResource("/gui/itemannonce.fxml"));
            ligneAnnonces.getChildren().add(nodes_annonce[indiceannonce]);
            count++;
            if (count == 3) {
                children.add(ligneAnnonces);
                ligneAnnonces = new HBox();
                ligneAnnonces.setSpacing(10);
                count = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (count > 0) {
        children.add(ligneAnnonces);
    }
    if (count > 0 && count < 3) {
        // Ajouter manuellement la dernière ligne si elle contient moins de 3 annonces
       // children.add(ligneAnnonces);
    }

    int itemsPerPage = 6;
    int numPages = (int) Math.ceil((double) taille_annonce / itemsPerPage);
    Pagination pagination = new Pagination(numPages, 0);
    pagination.setPageCount(numPages);
    pagination.setPageFactory((Integer pageIndex) -> {
        HBox pageAnnonces = new HBox();
        pageAnnonces.setSpacing(10);
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, taille_annonce);
        List<Node> currentPageNodes = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            currentPageNodes.add(nodes_annonce[i]);
        }
        pageAnnonces.getChildren().addAll(currentPageNodes);
        return pageAnnonces;
    });
    scrollpane_Salle.setContent(pagination);
    }


   @FXML
    private void evenement(ActionEvent event) throws IOException {
        
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("FrontEv.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }

    @FXML
    private void blog(ActionEvent event) throws IOException {
          Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("frontannonce.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void monprofil(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("Frontmodifier.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void reclamation(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("reclamation.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void produit(ActionEvent event) throws IOException {
        
          Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("FrontProduit.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }

    @FXML
    private void commande(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("mesCommandeTroc.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
        
    }
    
    
    
}
