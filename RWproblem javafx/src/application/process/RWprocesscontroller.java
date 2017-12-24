package application.process;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import application.process.Reader;
import application.process.Writer;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class RWprocesscontroller {

	// private Main main;
	@FXML
	public TextField numreader;
	public TextField numwriter;
	private final IntegerProperty numr;
	private final IntegerProperty numw;

	@FXML private ImageView book;
	@FXML private ImageView eye;
	@FXML private ImageView pen;
	@FXML private ImageView queue;
	@FXML private ImageView i1;
	@FXML private ImageView i2;


	public String nread;
	public String nwrite;
	public static int READERS;
	public static int WRITERS;
	public Label w;
	public Label r;
	private int a;
	private int b;

    /**GUI gets input from text field*/
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
    /**main process starts*/
	public void GO() {
		//createpeo();
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
	
	class Database extends Thread {
		int readers = 0; // number of active readers
		int read_or_write = 0;
		Queue<Integer> w = new LinkedList<Integer>();
		boolean readentry = true;
		boolean writeentry = true;
		Random rand = new Random();
		int numqueue=0;

		public double exprand(float lambda) {
			return Math.log(1 - rand.nextDouble()) / (-lambda);
		}

		void print() {
			System.out.println(read_or_write);
			System.out.println(readentry);
			System.out.println(writeentry);
		}

		void readjudge() {
			if (this.readers == 0 && w.isEmpty()) {
				this.read_or_write = 0;
				this.readentry = true;
				this.writeentry = true;
			} else if (this.readers == 0 && !w.isEmpty()) {
				this.read_or_write = 2;
				this.readentry = true;
			}
			this.notifyAll();
		}

		void writejudge() {
			if (this.readers == 0 && w.isEmpty()) {
				this.read_or_write = 0;
				this.readentry = true;
				this.writeentry = true;
			} else if (this.readers != 0 && w.isEmpty()) {
				this.read_or_write = 1;
				this.writeentry = true;
			}
			this.notifyAll();
		}
        /**reader*/
		public void read(int number) {
			synchronized (this) {
				createpeo();
				System.out.println("Reader " + number + " want entry!");
				if (read_or_write == 0)
					read_or_write = 1;
				if (read_or_write == 2)
					writeentry = false;

			}
			synchronized (this) {
				while (readentry == false) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
				this.readers++;
				createpeo();
				System.out.println("Reader " + number + " is in line.");
			}
			synchronized (this) {
				while (read_or_write == 2) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
				System.out.println("Reader " + number + " Start reading. ");
			}
			try {
				Thread.sleep((int) (exprand(0.5f) * 1000));
				// Thread.sleep((int) (Math.random() * DELAY));
			} catch (InterruptedException e) {
			}
			synchronized (this) {
				System.out.println("Reader " + number + " stops reading.");
				this.readers--;
				readjudge();
			}
		}

		/**writer*/
		public void write(int number) {
			System.out.println("Writer " + number + " want entry!");
			if (read_or_write == 0)
				read_or_write = 2;
			if (read_or_write == 1)
				readentry = false;
			// this.writers++;
			synchronized (this) {
				while (writeentry == false) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
				System.out.println("Writer " + number + " is in line");
				w.offer(number);
			}
			synchronized (this) {
				while (read_or_write == 1) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			synchronized (this) {
				while (w.peek() != number) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}

				System.out.println("Writer " + number + " starts writing.");
			}
			final int DELAY = 1000;
			try {
				int a = (int) (exprand(0.5f) * DELAY);
				Thread.sleep(a);
				// System.out.println(a);
				// Thread.sleep((int) (Math.random() * DELAY));
			} catch (InterruptedException e) {
			}
			synchronized (this) {
				System.out.println("Writer " + number + " stops writing.");
				w.poll();
				writejudge();
			}
		}
	}
    /**GUI images control*/
	public void createpeo() {
		TranslateTransition node1 = new TranslateTransition();
		//TranslateTransition node2 = new TranslateTransition();
		node1.setDuration(Duration.seconds(2));
		node1.setFromX(eye.getLayoutX() - 380);
		node1.setFromY(eye.getLayoutY() - 480);
		node1.setToY(queue.getLayoutX() - 250);
		node1.setToX(queue.getLayoutY() - 400);
		i1.setFitHeight(60);
		i1.setFitWidth(60);
		i1.setImage(new Image("file:///Users/chiehhsi/eclipse-workspace/RWproblem/src/application/EYE%20copy.png"));
		node1.setNode(i1);
		node1.play();

	}

    /**Homebutton*/
	@FXML
	private void gohome() {
		Main.showHome();
	}
}
