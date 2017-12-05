package application.view;

import javafx.fxml.FXML;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
import application.Main;

public class Homecontroller {
	
	//@FXML
	//private Main main;
	
	@FXML
	private void startbutton(){
		Main.showprocess();
	}
}
