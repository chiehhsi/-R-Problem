package application.view;

import javafx.fxml.FXML;
//import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
//import java.awt.TextField;

//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
import application.Main;

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
	private ImageView coffee;

	
	public void startbutton(){
		Main.showprocess();

	}
}
