/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Categorie;
import entity.Commande;
import entity.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import service.CommandeService;
import entity.Troc;
import entity.user;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import service.TrocService;
import javax.mail.*;
import javax.mail.internet.*;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author jawhe
 */
public class DetailTrocController implements Initializable {
    private String path;
    private String pathUpdateimage;
    private  static int id;
    private Troc tr;
    private Produit p;
    @FXML
    private Label detailtrocfx;
    @FXML
    private TextField nomProduitFX;
    @FXML
    private TextField discriptionfx;
    @FXML
    private ImageView imageTrocproduit;
    @FXML
    private Button mailfx;
    @FXML
    private Button updatefx;
    @FXML
    private AnchorPane txt1;
    @FXML
    private Button anulerfx;
    @FXML
    private Button imageupdatefx;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//       if (id != 0) {
//           TrocService ts = new TrocService();
//           Troc troc = ts.readById(id);
//           nomProduitFX.setText(troc.getNom_produitTroc());
//           discriptionfx.setText(troc.getDescription_produitTroc());
////        byte[] imageData = troc.getImageData();
////           InputStream inputStream = new ByteArrayInputStream(imageData);
////           Image image = new Image(inputStream);
////           imageTrocproduit.setImage(image);
//           
//
//}
    
    }
    public void setDetailtrocfx(String detailtrocfx) {
        this.detailtrocfx.setText(detailtrocfx);
    }

    public void setT(Troc t) {
        this.tr = t;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageTrocproduit(int id) {
        String s=String.valueOf(id);
        String dossierImages = "C:\\xampp\\htdocs\\img\\";
       File dossier = new File(dossierImages);
        File[] fichiers = dossier.listFiles();
        for (File fichier : fichiers) {
        String nomFichier = fichier.getName();
         if (nomFichier.startsWith(s) && (nomFichier.endsWith(".jpg")
                || nomFichier.endsWith(".png") || nomFichier.endsWith(".PNG"))) {
            String cheminImage = fichier.toURI().toString();
            Image img = new Image(cheminImage);
            imageTrocproduit.setImage(img);
            System.out.println(cheminImage);
            } 
        }
    }

    
    

    public void setNomProduitFX(String nomProduitFX) {
        this.nomProduitFX.setText(nomProduitFX);
    }

    public void setDiscriptionfx(String discriptionfx) {
        this.discriptionfx.setText(discriptionfx);
    }

    public void setP(Produit p) {
        this.p = p;
    }

   

    @FXML
    private void envoyermail(ActionEvent event) throws MessagingException {
         UserService pst=new UserService();
          user u= new user();
          u=pst.readById(Affariet.userID);
        String username = "jawherjaziri2023@gmail.com";
        String password = "whmlmiirylrkifva";



    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    Message message = new MimeMessage(session);

    try {
        message.setFrom(new InternetAddress("jawherjaziri2023@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(p.getuser().getEmail()));

        message.setSubject("Proposition echange");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Bonjour\n"
                + "nom produit :"+nomProduitFX.getText()+"\n"+
                "description de produit :"+discriptionfx.getText()+"\n"+
                "vous pouvez le contacer\n"+
                "son email :"+u.getEmail()+"\n"+
        "son numero telephone :"+u.getNumero()+"\n"+
                "son nom"+u.getNom()+"\n");

        // Créer la pièce jointe
        MimeBodyPart attachmentPart = new MimeBodyPart();
        String s=String.valueOf(id);
        String dossierImages = "C:\\xampp\\htdocs\\img\\";
       File dossier = new File(dossierImages);
        File[] fichiers = dossier.listFiles();
        String cheminImage="";
        for (File fichier : fichiers) {
        String nomFichier = fichier.getName();
        if (nomFichier.startsWith(s) && (nomFichier.endsWith(".jpg")
                || nomFichier.endsWith(".png") || nomFichier.endsWith(".PNG"))) {
            cheminImage = fichier.toURI().toString();
            System.out.println(cheminImage);
            } 
        }
        String fileName = cheminImage.substring(cheminImage.lastIndexOf("/") + 1);
        DataSource source = new FileDataSource(dossierImages+fileName); // Remplacez "chemin/vers/votre/image.jpg" par le chemin d'accès à votre fichier image
        attachmentPart.setDataHandler(new DataHandler(source));
        
        System.out.println(fileName);
        attachmentPart.setFileName(fileName); // Remplacez "nom_de_votre_fichier.jpg" par le nom que vous souhaitez donner à votre fichier

        // Créer le message multipart et y ajouter le corps du message et la pièce jointe
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);
        message.setContent(multipart);

        // Envoyer le message
        Transport.send(message);

        System.out.println("Email sent successfully.");

    } catch (MessagingException ex) {
        Logger.getLogger(TrocService.class.getName()).log(Level.SEVERE, null, ex);
        throw ex; // re-throw the exception so that the caller can handle it
    }
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontProduit.fxml"));

            Parent root = loader.load();

            // Create a new Scene object for the new page
            Scene scene = new Scene(root);

            // Get the Stage object for the current page
            Stage stage = (Stage) anulerfx.getScene().getWindow();

            // Set the new Scene on the Stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void update(ActionEvent event) throws IOException, URISyntaxException {
       if (id != 0) {
           String s=String.valueOf(id);
        String dossierImages = "C:\\xampp\\htdocs\\img\\";
       File dossier = new File(dossierImages);
        File[] fichiers = dossier.listFiles();
        String cheminImage="";
        for (File fichier : fichiers) {
        String nomFichier = fichier.getName();
        if (nomFichier.startsWith(s) && (nomFichier.endsWith(".jpg")
                || nomFichier.endsWith(".png") || nomFichier.endsWith(".PNG"))) {
            cheminImage = fichier.toURI().toString();
            File filechange = new File(new URI(cheminImage));
            path = filechange.getPath().replace("/", "\\");
            System.out.println( path);
            File file = new File( path);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
            } 
        }
         UserService pt=new UserService();
          user u= new user();
          u=pt.readById(Affariet.userID);
            
            

       TrocService pst=new TrocService();
       Troc t2=new Troc(id,p,nomProduitFX.getText(),discriptionfx.getText(),pathUpdateimage,u);
       pst.update(t2);
          String destinationPath = "C:\\xampp\\htdocs\\img\\"+id+";";

// Récupérer le fichier sélectionné
        File selectedFile = new File(pathUpdateimage);

// Créer un nouveau fichier dans le dossier de destination avec le même nom que le fichier sélectionné
        File destinationFile = new File(destinationPath + selectedFile.getName());

// Copier le fichier sélectionné dans le dossier de destination
        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

// Définir le nom de l'image dans l'utilisateur
        tr.setImage_produitTroc(selectedFile.getName());

            String su=String.valueOf(id);
        String dossierUpdateImages = "C:\\xampp\\htdocs\\img\\";
       File dossiers = new File(dossierUpdateImages);
        File[] files = dossiers.listFiles();
        for (File fichier : files) {
        String nomFichier = fichier.getName();
        if (nomFichier.startsWith(su) && nomFichier.endsWith(".jpg")||nomFichier.startsWith(s) && nomFichier.endsWith(".png")) {
            String chemin = fichier.toURI().toString();
            Image image = new Image(chemin);
            imageTrocproduit.setImage(image);
            } 
        }

}
        
    }

    @FXML
    private void annuler(ActionEvent event) {
        TrocService pst=new TrocService();
        pst.delete(id);
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontProduit.fxml"));

            Parent root = loader.load();

            // Create a new Scene object for the new page
            Scene scene = new Scene(root);

            // Get the Stage object for the current page
            Stage stage = (Stage) anulerfx.getScene().getWindow();

            // Set the new Scene on the Stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void imageupdate(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
        new FileChooser.ExtensionFilter("All Files", "*.*"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
       String imagePath=selectedFile.getAbsolutePath();
         pathUpdateimage=imagePath;

   }
    }
    
    
}
