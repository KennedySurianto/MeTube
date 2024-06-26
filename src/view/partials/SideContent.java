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
		BorderPane view = new BorderPane();
		view.setPrefSize(300, 180);
		
		Label titleLabel = new Label(video.getTitle());
		titleLabel.setStyle("-fx-font-size: 16px;");
		
		String thumbnailPath = new File(video.getThumbnail()).toURI().toString();
		Image videoImage = new Image(thumbnailPath);
		ImageView imageView = new ImageView(videoImage);
		imageView.setFitWidth(300);
	    imageView.setFitHeight(180);
	    imageView.setPreserveRatio(true);
		
		StackPane videoPane = new StackPane();
		videoPane.getChildren().add(imageView);
		
		view.setTop(titleLabel);
		view.setCenter(videoPane);
		
		return view;
	}
	
}
