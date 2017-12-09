package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Main extends Application {
	private Stage primaryStage;
	private static BorderPane home;
	private BorderPane rootLayout;
	
	


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage= primaryStage;
		this.primaryStage.setTitle("RW problem");
		initRootLayout();
		showHome();
		
	}
	
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private void showHome() {
		try {
            // Load Home from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Home.fxml"));
            home = (BorderPane) loader.load();

            // Show the scene containing the home.
            /*Scene scene = new Scene(home);
            primaryStage.setScene(scene);
            primaryStage.show();*/
            rootLayout.setCenter(home);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void showprocess() {
		try {
		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(Main.class.getResource("process/RWprocess.fxml"));
		BorderPane rwprocess = loader.load();
		home.setCenter(rwprocess);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	public static void main(String[] args) {
		launch(args);
	}
}
