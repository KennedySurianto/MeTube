package view;

import java.io.File;
import java.util.ArrayList;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import model.Video;
import view.partials.SideContent;

public class MainView {
	BorderPane view;
	ArrayList<Video> videoList;

	public BorderPane getView() {
		MainController mainController = new MainController();
		view = new BorderPane();
		videoList = mainController.getVideoList();

		playVideo(videoList.get(0), null);

		return view;
	}

	private void playVideo(Video video, MediaPlayer prevMediaPlayer) {
		if (prevMediaPlayer != null) {
			prevMediaPlayer.stop();
			prevMediaPlayer.dispose();
		}

		// Correctly format the video path as a URI
		String videoPath = new File(video.getUri()).toURI().toString();
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

		// Duration slider
		Slider timeSlider = new Slider();
		timeSlider.setMin(0);
		timeSlider.setMax(100);

		timeSlider.setStyle("-fx-background-color: #333;");

		mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
			if (!timeSlider.isValueChanging()) {
				timeSlider.setValue(newTime.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
			}
		});

		timeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			if (timeSlider.isValueChanging()) {
				mediaPlayer.seek(
						Duration.millis(newValue.doubleValue() / 100 * mediaPlayer.getTotalDuration().toMillis()));
			}
		});

		videoPane.getChildren().add(mediaView);
		StackPane.setAlignment(mediaView, Pos.CENTER);

		// Create playback control buttons
		Button playPauseButton = new Button("Pause");
		Button skipForwardButton = new Button("10s >>");
		Button skipBackwardButton = new Button("<< 10s");

		// Set up event handlers
		playPauseButton.setOnAction(e -> {
			setButtonAction(mediaPlayer, playPauseButton);
		});

		// Handle media end event
		mediaPlayer.setOnEndOfMedia(() -> {
			playPauseButton.setText("Play Again");

			playPauseButton.setOnAction(event -> {
				mediaPlayer.seek(Duration.ZERO);

				setButtonText(mediaPlayer, playPauseButton);
				playPauseButton.setOnAction(e -> {
					setButtonAction(mediaPlayer, playPauseButton);
				});
			});
		});

		skipForwardButton.setOnAction(e -> {
			mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
		});

		skipBackwardButton.setOnAction(e -> {
			mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(10)));
			setButtonText(mediaPlayer, playPauseButton);
		});

		// Arrange buttons in an HBox
		HBox controls = new HBox(10, skipBackwardButton, playPauseButton, skipForwardButton);
		controls.setAlignment(Pos.CENTER);
		controls.setStyle("-fx-background-color: #333; -fx-padding: 10px;");

		Label videoLabel = new Label(video.getTitle());
		videoLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

		HBox title = new HBox(10, videoLabel);
		title.setStyle("-fx-padding: 10px;");

		// Create a VBox for the video layout
		VBox videoLayout = new VBox();
		videoLayout.getChildren().addAll(title, videoPane, timeSlider, controls);
		VBox.setVgrow(videoPane, Priority.ALWAYS); // Allow videoPane to grow

		// Create a VBox for the right sidebar
		VBox sidebar = new VBox(10);
		sidebar.setStyle("-fx-padding: 10px; -fx-background-color: #f4f4f4;");

		// Add videos to sidebar
		for (Video v : videoList) {
			if (!media.getSource().equals(new File(v.getUri()).toURI().toString())) {
				BorderPane sideContent = new SideContent().getView(v);

				sideContent.setOnMouseClicked(e -> {
					playVideo(v, mediaPlayer);
				});

				sidebar.getChildren().add(sideContent);
			}
		}

		// ScrollPane
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(sidebar);
		scrollPane.setFitToWidth(true);
		scrollPane.setPrefSize(340, 600);

		view.setCenter(videoLayout);
		view.setRight(scrollPane);

		mediaPlayer.play();
	}

	private void setButtonText(MediaPlayer mediaPlayer, Button playPauseButton) {
		if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
			playPauseButton.setText("Pause");
		} else {
			playPauseButton.setText("Play");
		}
	}

	private void setButtonAction(MediaPlayer mediaPlayer, Button playPauseButton) {
		if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
			mediaPlayer.pause();
			playPauseButton.setText("Play");
		} else {
			mediaPlayer.play();
			playPauseButton.setText("Pause");
		}
	}
}
