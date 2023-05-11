/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entity.evenement;
import entity.reservation;
import entity.user;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.File;
import java.io.IOException;
import javax.mail.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import service.evenementService;
import service.reservationService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import service.UserService;
public class ListeEvenementsController implements Initializable {

    @FXML
    private HBox itemEv;
    @FXML
    private Label libelle;
    @FXML
    private Label label_prix;

    @FXML
    private Label desc;
    @FXML
    private ImageView image;
    
    evenementService evs = new evenementService();
    private evenement e = evs.readById(FrontEvController.id_ev_static);
    reservation res = new reservation();
    reservationService resS = new reservationService();
    @FXML
    private ImageView qrcodee;
UserService us =new UserService();
    user CurrentUser = us.readById(Affariet.userID);
    public static int c=0 ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       

        System.out.println("xxxx");
        Image img =Retournerimage(e.getAffiche());
        
        image.setImage(img);

        libelle.setText(e.getNom_event());
//        libelle.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        
        label_prix.setText(String.valueOf(e.getPrix()));
//        label_prix.setStyle("-fx-font-size: 16px;");

        
        desc.setText(e.getDescription_event());
//        desc.setStyle("-fx-font-size: 14px;");

           
        
    }
public static int showNumberInputDialog() {
        // Créer une boîte de dialogue de saisie de texte
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Saisir un nombre");
        dialog.setHeaderText("Entrez un nombre :");
        dialog.setContentText("Nombre : ");

        // Afficher la boîte de dialogue et attendre la saisie de l'utilisateur
        Optional<String> result = dialog.showAndWait();

        // Traiter la réponse de l'utilisateur
        if (result.isPresent()) {
            try {
                int number = Integer.parseInt(result.get());

                // Créer une boîte de dialogue de confirmation
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setHeaderText("Vous avez saisi : " + number);
                confirmation.setContentText("Voulez-vous continuer ?");

                // Afficher la boîte de dialogue de confirmation et attendre la réponse de l'utilisateur
                Optional<ButtonType> response = confirmation.showAndWait();

                // Traiter la réponse de l'utilisateur
                if (response.isPresent() && response.get() == ButtonType.OK) {
                    return number; // renvoyer le nombre saisi
                }
            } catch (NumberFormatException e) {
                // La saisie de l'utilisateur n'est pas un nombre entier
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText("La saisie n'est pas un nombre entier");
                error.showAndWait();
            }
        }

        // Si l'utilisateur a annulé ou si une erreur s'est produite, renvoyer -1
        return -1;
    }
   private Image Retournerimage(String i) {
    // Chemin du dossier contenant les images
    String dossierImages = "C:\\Users\\lando\\Desktop\\affariet12345\\affariet1\\public\\uploads\\event_images\\";
  
    // Convertir l'entier i en une chaîne de caractères
    //String id_event_str = String.valueOf(i);
    
    // Créer un objet File pour représenter le dossier
    File dossier = new File(dossierImages);       

    // Récupérer la liste des fichiers dans le dossier
    File[] fichiers = dossier.listFiles();
    
    // Parcourir la liste des fichiers
    for (File fichier : fichiers) {
        // Récupérer le nom du fichier
        String nomFichier = fichier.getName();
         
        // Vérifier si le nom de fichier commence par id_event_str et se termine par .jpg, .png ou .PNG
        if (nomFichier.equals(i) ) {
            // Si le fichier correspond, convertir le chemin du fichier en une URL d'image et créer un objet Image à partir de l'URL
            String cheminImage = fichier.toURI().toString();
            Image img = new Image(cheminImage);
            
            // Retourner l'image trouvée
            return img;
        }
    }
    
    // Si aucun fichier ne correspond, retourner null
    return null;
}

    @FXML
    private void reserver(ActionEvent event) throws MessagingException {
        // Vérifier s'il y a encore des places disponibles pour l'événement
        int compteur = e.getNbr_max();
        List<reservation> LR = resS.readAll();
        
        if (compteur > 0 && hasReservationForEvent(LR, e) ) {
           
            
            c = showNumberInputDialog();
            System.out.println("vv"+c);
            // Si oui, décrémenter le compteur de places disponibles
            e.setNbr_max(compteur - c);
          
            LocalDate dateRes = LocalDate.now(); // Utiliser la date actuelle pour la réservation

            // Créer une nouvelle réservation pour l'utilisateur actuellement connecté
            envoyerEmail(CurrentUser);
            res = new reservation(dateRes, e, CurrentUser);
            // Ajouter la réservation à la base de données pour conserver les enregistrements
            resS.insert(res);
            evs.update(e);
            // Afficher un message de confirmation ou rediriger l'utilisateur vers une page de confirmation
            showConfirmationMessage("Réservation réussi","Réservation effectuée avec succès !");
        } else {
            // Si non, afficher un message d'erreur ou rediriger l'utilisateur vers une page d'erreur
            showErrorMesssage("Réservation invalide","Désolé, il n'y a plus de places disponibles pour cet événement.");
        }
    }



public boolean showConfirmationMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    // afficher la boîte de dialogue et attendre que l'utilisateur fasse un choix
    ButtonType buttonType = alert.showAndWait().orElse(ButtonType.CANCEL);

    // retourner true si l'utilisateur a cliqué sur "OK", false sinon
    return buttonType == ButtonType.OK;
}

public void showErrorMesssage(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    alert.showAndWait();
}


public static Image generateQRCodeImage(String information, int width, int height) throws WriterException {
    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.MARGIN, 0);
    hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);
    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix bitMatrix = writer.encode(information, BarcodeFormat.QR_CODE, width, height, hints);
    BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
        }
    }
    return SwingFXUtils.toFXImage(qrCodeImage, null);
}




public boolean hasReservationForEvent(List<reservation> LR, evenement e) {
    for (reservation r : LR) {
        if (r.getId_user1().getId_user()==CurrentUser.getId_user() && r.getId_event().getId_event() == e.getId_event()) {
            return false;
        }
    }
    return true;
}
    final String username = "zakaria.bouharb771@gmail.com";
final String password = "zmyspquizjuqatmw";

public void envoyerEmail(user u) throws MessagingException {

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
        message.setFrom(new InternetAddress("ahmedturki2662@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(u.getEmail()));

        message.setSubject("Confirmation reservation");

        // Generate QR code image
        String reservationString = "Réservation pour l'événement :" + e.getNom_event()+ " effectuée par :" + u.getEmail();        
        int width = 300;
        int height = 300;
        Image qrImage = generateQRCodeImage(reservationString, width, height);

        // Convert image to byte array
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(qrImage, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] qrBytes = baos.toByteArray();

        // Add QR code as attachment
        MimeBodyPart qrAttachment = new MimeBodyPart();
        qrAttachment.setFileName("qr-code.png");
        qrAttachment.setContent(qrBytes, "image/png");

        // Add text message
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText("Bonjour ,\n"
                + " \n"
                + "Votre reservation a etait effectuer avec succes."
                + "\n "
                + "voici votre code QR pour votre reservation");

        // Add multipart message
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(qrAttachment);
        message.setContent(multipart);

        Transport.send(message);

        System.out.println("Email sent successfully.");

    } catch (MessagingException | WriterException | IOException ex) {
        Logger.getLogger(evenementService.class.getName()).log(Level.SEVERE, null, ex);
        throw new MessagingException("Failed to send email", ex); // re-throw the exception so that the caller can handle it
    }
}

    }



