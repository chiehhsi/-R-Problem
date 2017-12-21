package application.process;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import application.process.Database;
import application.process.Reader;
import application.process.Writer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RWprocesscontroller {

	// private Main main;
	@FXML
	public TextField numreader;
	public TextField numwriter;
	private final IntegerProperty numr;
	private final IntegerProperty numw;

	@FXML
	private ImageView book;
	@FXML
	private ImageView eye;
	@FXML
	private ImageView pen;

	@FXML
	public String nread;
	public String nwrite;
	public static int READERS;
	public static int WRITERS;
	public Label w;
	public Label r;
	private int a;
	private int b;

	public RWprocesscontroller() {
		this.numr = new SimpleIntegerProperty(0);
		this.numw = new SimpleIntegerProperty(0);
	}

	public void getreader() {

		try {
			a = Integer.parseInt(numreader.getText());
		} catch (NumberFormatException ex) {
			System.out.println("not a number");
		}
		r.setText("the content of reader " + a);
		numr.setValue(a);
	}

	public void getwriter() {

		try {
			b = Integer.parseInt(numwriter.getText());
		} catch (NumberFormatException ex) {
			System.out.println("not a number");
		}
		w.setText("the content of writer" + b);
		numw.setValue(b);
	}

	public void GO() {
		getreader();
		getwriter();
		READERS = numr.getValue();
		WRITERS = numw.getValue();
		System.out.print(READERS + " " + WRITERS + "\n");

		Database database = new Database();
		for (int i = 0; i < READERS; i++) {
			new Reader(database).start();
		}
		for (int i = 0; i < WRITERS; i++) {
			new Writer(database).start();
		}
	}

	@FXML
	private void gohome() {
		Main.showHome();
	}

}
