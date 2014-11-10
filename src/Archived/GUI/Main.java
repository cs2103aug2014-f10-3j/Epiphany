//@author A0118794R-unused
package Archived.GUI;
	
import java.io.IOException;

import Archived.GUI.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) throws IOException {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Epiphany");
			rootLayout = new BorderPane();
			Scene scene = new Scene(rootLayout,600,600);
			showEpiphanyOverview();
		
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public void showEpiphanyOverview() {
		try {
			// load epiphany overview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("EpiphanyStage.fxml"));
			AnchorPane epiphanyOverview = (AnchorPane) loader.load();

			// set epiphany overview into the centre of the root layout
			rootLayout.setCenter(epiphanyOverview);
			GUIController controller = loader.getController();
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
