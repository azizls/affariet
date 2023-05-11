package gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class StarRating extends HBox {
    private IntegerProperty rating = new SimpleIntegerProperty();
    Image image = new Image(getClass().getResourceAsStream("/ressources/star.png"));

    ImageView star = new ImageView(new Image(getClass().getResourceAsStream("/ressources/star.png")));

    public StarRating(int maxRating) {
        setAlignment(Pos.CENTER);
        for (int i = 0; i < maxRating; i++) {
            ImageView star = new ImageView(new Image(getClass().getResourceAsStream("/ressources/star.png")));
            star.setFitHeight(20);
            star.setFitWidth(20);
            star.setOpacity(0.5);
            final int index = i;
            star.setOnMouseClicked(event -> rating.set(index + 1));
            rating.addListener((observable, oldValue, newValue) -> {
                if (newValue.intValue() > index) {
                    star.setOpacity(1);
                } else {
                    star.setOpacity(0.5);
                }
            });
            getChildren().add(star);
        }
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }
}
