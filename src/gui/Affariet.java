package gui;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Affariet extends Application {
    public static int userID;
    public static String email ; 
    public static int id_role ;
        public static String imagee;
        public static String codeConfirmation;
    @Override
    public void start(Stage stage) throws Exception {
       
        
        Parent root = FXMLLoader.load(getClass().getResource("seconnecter.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("affarietstyle.css");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
