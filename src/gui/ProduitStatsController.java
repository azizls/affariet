package gui;

import entity.Categorie;
import entity.Produit;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.ProduitService;
import utils.DataSource;

public class ProduitStatsController {
    
    @FXML
    private PieChart categoriesPieChart;
    
    @FXML
    private Label mostProductCategoryLabel;
    
    @FXML
    private void initialize() {
        displayCategoriesPieChart();
        displayMostProductCategory();
    }
    
    private void displayCategoriesPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ProduitService ps = new ProduitService();
        Map<String, Long> categoriesCount = ps.readAll()
                .stream()
                .collect(Collectors.groupingBy(p -> p.getId_categorie().getNom_categorie(), Collectors.counting()));
        categoriesCount.forEach((categoryName, count) -> {
            pieChartData.add(new PieChart.Data(categoryName, count));
        });
        categoriesPieChart.setData(pieChartData);
    }
    
    private void displayMostProductCategory() {
        ProduitService ps = new ProduitService();
        List<Categorie> categories = ps.readAll()
                .stream()
                .map(p -> p.getId_categorie())
                .distinct()
                .collect(Collectors.toList());
        Categorie mostProductCategory = categories
                .stream()
                .max(Comparator.comparing(c -> ps.findMostExpensiveProducts(categories).size()))
                .orElse(null);
        if (mostProductCategory != null) {
            mostProductCategoryLabel.setText(mostProductCategory.getNom_categorie());
        }
    }
    
    public static Categorie getCategoryWithMostProducts() {
        ProduitService ps = new ProduitService();
        List<Categorie> categories = ps.readAll()
                .stream()
                .map(p -> p.getId_categorie())
                .distinct()
                .collect(Collectors.toList());
        Categorie mostProductCategory = categories
                .stream()
                .max(Comparator.comparing(c -> ps.findMostExpensiveProducts(categories).size()))
                .orElse(null);
        return mostProductCategory;
    }
    
     @FXML
    private void back(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Categorie.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene); 
                stage.show();
    }
    
}
