package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootlayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("address");
		showoverview();
	}
		//try {
		//	BorderPane root = new BorderPane();
		//	Scene scene = new Scene(root,400,400);
		//	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//	primaryStage.setScene(scene);
		//	primaryStage.show();
		//} catch(Exception e) {
		//	e.printStackTrace();
		//}
	//}
	public void showoverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Mainview.fxml"));
			rootlayout=(BorderPane) loader.load();
			Scene scene = new Scene(rootlayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
