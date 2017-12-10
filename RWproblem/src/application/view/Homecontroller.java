package application.view;

import javafx.fxml.FXML;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
import application.Main;
import javafx.scene.image.ImageView;
//import javafx.scene.image.Image;
//import javafx.fxml.Initializable;

public class Homecontroller {
	
	//@FXML
	//private Main main;
	@FXML
	private ImageView book;
	@FXML
	private ImageView eye;
	@FXML
	private ImageView pen;
	@FXML
	private void startbutton(){
		Main.showprocess();
	
	}
}
