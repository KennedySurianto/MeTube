import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application {
	private Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.stage.setTitle("MeTube");
		
		Image icon = new Image("/resources/metube-icon.png");
		this.stage.getIcons().add(icon);
		
		showHomeView();
	}
	
	public void showHomeView() {
		this.stage.setScene(new Scene(new MainView().getView(), 1300, 660));
		this.stage.show();
	}
}
