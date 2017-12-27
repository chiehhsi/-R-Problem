package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
<<<<<<< HEAD
=======

>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783



public class Main extends Application {
	private static Stage primaryStage;
	private static BorderPane home;
<<<<<<< HEAD
	Media musicFile_r = new Media("file:///Users/chiehhsi/eclipse-workspace/RWproblem/src/application/jazz.mp3");
	MediaPlayer mediaplayer;
=======
	MediaPlayer mediaplayer;

>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783

	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage= primaryStage;
		Main.primaryStage.setTitle("RW problem");
		showHome();
<<<<<<< HEAD
		mediaplayer = new MediaPlayer(musicFile_r);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(0.5);
=======
		Media BGM = new Media("file:///C:/Users/ASUS/Desktop/106¤W¾Ç´Á/jazz.mp3");
		mediaplayer = new MediaPlayer(BGM);
		mediaplayer.setAutoPlay(true);
>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783

	}
	public static void showHome() {
		try {
            // Load Home from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Home.fxml"));
            home = (BorderPane) loader.load();

            // Show the scene containing the home.
            Scene scene = new Scene(home);
            primaryStage.setScene(scene);
            primaryStage.show();
            
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
