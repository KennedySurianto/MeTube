package view.partials;

import java.io.File;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.Video;

public class SideContent {

	public BorderPane getView(Video video) {
		BorderPane content = new BorderPane();
		content.setPrefSize(300, 180);

		Label titleLabel = new Label(video.getTitle());
		titleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

		String thumbnailPath = new File(video.getThumbnail()).toURI().toString();
		Image videoImage = new Image(thumbnailPath);
		ImageView imageView = new ImageView(videoImage);
		imageView.setFitWidth(300);
		imageView.setFitHeight(180);
		imageView.setPreserveRatio(true);

		StackPane videoPane = new StackPane();
		videoPane.getChildren().add(imageView);

		content.setTop(titleLabel);
		content.setCenter(videoPane);

		content.setOnMouseEntered(e -> content.setStyle("-fx-cursor: hand;"));
		content.setOnMouseExited(e -> content.setStyle("-fx-cursor: default;"));

		return content;
	}

}
