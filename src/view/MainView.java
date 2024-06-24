package view;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainView {

	public BorderPane getView() {
		BorderPane view = new BorderPane();

		// Correctly format the video path as a URI
		String videoPath = new File(getResource("ncs-superhero.mp4")).toURI().toString();

		Media media = new Media(videoPath);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);

		// Create a StackPane for the video
		StackPane videoPane = new StackPane();
		videoPane.setStyle("-fx-background-color: black;");

		// Bind MediaView size to StackPane size
		mediaView.fitWidthProperty().bind(videoPane.widthProperty());
		mediaView.fitHeightProperty().bind(videoPane.heightProperty());

		// Ensure the MediaView preserves the aspect ratio
		mediaView.setPreserveRatio(true);

		videoPane.getChildren().add(mediaView);
		StackPane.setAlignment(mediaView, Pos.CENTER);

		// Create playback control buttons
		Button playPauseButton = new Button("Pause");
		Button skipForwardButton = new Button(">> 10s");
		Button skipBackwardButton = new Button("<< 10s");

		// Set up event handlers
		playPauseButton.setOnAction(e -> {
			if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
				mediaPlayer.pause();
				playPauseButton.setText("Play");
			} else {
				mediaPlayer.play();
				playPauseButton.setText("Pause");
			}
		});

		skipForwardButton.setOnAction(e -> {
			mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(10)));
		});

		skipBackwardButton.setOnAction(e -> {
			mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.seconds(10)));
		});

		// Arrange buttons in an HBox
		HBox controls = new HBox(10, skipBackwardButton, playPauseButton, skipForwardButton);
		controls.setAlignment(Pos.CENTER);
		controls.setStyle("-fx-background-color: #333; -fx-padding: 10px;");

		// Create a VBox for the video layout
		VBox videoLayout = new VBox();
		videoLayout.getChildren().addAll(videoPane, controls);
		VBox.setVgrow(videoPane, Priority.ALWAYS); // Allow videoPane to grow

		// Create a VBox for the right sidebar
		VBox sidebar = new VBox(10);
		sidebar.setStyle("-fx-padding: 10px; -fx-background-color: #f4f4f4;");

		// Simulate related videos with rectangles
		Rectangle rectangle1 = new Rectangle(300, 180, Color.RED);
		Rectangle rectangle2 = new Rectangle(300, 180, Color.RED);
		Rectangle rectangle3 = new Rectangle(300, 180, Color.RED);

		sidebar.getChildren().addAll(rectangle1, rectangle2, rectangle3);

		// Add the video layout to the center and the sidebar to the right of the
		// BorderPane
		view.setCenter(videoLayout);
		view.setRight(sidebar);

		// Play the video automatically
		mediaPlayer.play();

		return view;
	}

	private String getResource(String fileName) {
		return System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + fileName;
	}
}
