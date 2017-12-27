package application.process;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import application.process.Reader;
import application.process.Writer;
import javafx.animation.PathTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
	public Label numqu;
	public Label numpeo;
	private final IntegerProperty numr;
	private final IntegerProperty numw;

	@FXML
	private ImageView book;
	@FXML
	private ImageView eye;
	@FXML
	private ImageView pen;
	@FXML
	private ImageView queue;
	@FXML
	private ImageView i0; // reader
	@FXML
	private ImageView i1;
	@FXML
	private ImageView i2;
	@FXML
	private ImageView i3;
	@FXML
	private ImageView i4;
	@FXML
	private ImageView i5; // writer
	@FXML
	private ImageView i6;

	
	public String nread;
	public String nwrite;
	public static int READERS;
	public static int WRITERS;
	public Label w;
	public Label r;
	private int a;
	private int b;
	public int count = 0;
	public int numqueue = 0;

	PathTransition[] path = new PathTransition[7];
	ImageView[] icon = new ImageView[7];
	Path[] p = new Path[7];

	Media musicFile_r = new Media("file:///Users/chiehhsi/eclipse-workspace/RWproblem/src/application/book.mp3");
	Media musicFile_w = new Media("file:///Users/chiehhsi/eclipse-workspace/RWproblem/src/application/writer.mp3");

<<<<<<< HEAD
	MediaPlayer mediaplayer;

	/** GUI gets input from text field */
=======
	/** GUI gets input from text field */
	
	Media musicFile_r = new Media("file:///C:/Users/ASUS/Desktop/106上學期/writer.mp3");
	Media musicFile_w = new Media("file:///C:/Users/ASUS/Desktop/106上學期/book.mp3");
	
	MediaPlayer mediaplayer;
	
    /**GUI gets input from text field*/
>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783
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

	/** main process starts */
	public void GO() {
		icon[0] = i0;
		icon[1] = i1;
		icon[2] = i2;
		icon[3] = i3;
		icon[4] = i4;
		icon[5] = i5;
		icon[6] = i6;
		for (int i = 0; i < 7; i++) {
			p[i] = new Path();
			path[i] = new PathTransition();
		}
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
		int canwrite = 1;
		int canread = 1;
		int existreader = 0;
		int lineofreader = 0;
		int lineofwriter = 0;
		Queue<Integer> w = new LinkedList<Integer>();
		boolean readentry = true;
		boolean writeentry = true;
		Random rand = new Random();

		public double exprand(float lambda) {
			return Math.log(1 - rand.nextDouble()) / (-lambda);
		}

		void finishread() {
			if (this.readers == 0 && !w.isEmpty()) {
				read_or_write = 2;
				canwrite = 1;
				canread = 0;
			} else if (this.existreader != 0 && w.isEmpty()) {
				read_or_write = 1;
				canread = 1;
				canwrite = 0;
			} else if (this.existreader == 0 && w.isEmpty()) {
				read_or_write = 0;
				canread = 1;
				canwrite = 1;
			}
			this.notifyAll();
		}

		void finishwrite() {
			if (existreader != 0) {
				read_or_write = 1;
				canread = 1;
				canwrite = 0;
			} else if (existreader == 0 && !w.isEmpty()) {
				read_or_write = 2;
				canwrite = 1;
				canread = 0;
			} else if (existreader == 0 && w.isEmpty()) {
				read_or_write = 0;
				canwrite = 1;
				canread = 1;
			}
			this.notifyAll();
		}

		public void read(int number) {
			synchronized (this) {
				path[number]=new PathTransition();
				//create("READER", number, lineofreader+lineofwriter);
				this.lineofreader++;
				System.out.println("Reader " + number + " is in line.");
				System.out.println("Line of readers : " + lineofreader);
				System.out.println("Line of writers : " + lineofwriter);
				System.out.println("Reading people : " + readers);
				this.existreader++;
				if (read_or_write == 0) {
					read_or_write = 1;
					canwrite = 0;
				} else if (read_or_write == 2) {
					canwrite = 0;
					canread = 1;
				}
			//create("READER", number, lineofreader+lineofwriter);

			}
			synchronized (this) {
				while (canread == 0 || read_or_write != 1) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
				hi(number, readers, "READER");
				
				System.out.println("Reader " + number + " Start reading. ");
<<<<<<< HEAD
				this.lineofreader--;
=======

				numqueue--;
				count++;
				System.out.println("-people in Qline- " + numqueue);
				System.out.println("-people in Bline- " + count);


>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783
				mediaplayer = new MediaPlayer(musicFile_r);
				mediaplayer.setAutoPlay(true);
				mediaplayer.setVolume(3);
				
<<<<<<< HEAD
				this.readers++;

=======
>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783
			}

			try {
<<<<<<< HEAD
	
				int a = (int)(exprand(1)* 5000);
=======
				int a = (int) (exprand(0.5f) * 10000);
>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783
				Thread.sleep(a);
				
				mediaplayer.setStartTime(Duration.seconds((double) a));
				System.out.println(a);
			} catch (InterruptedException e) {
			}
			synchronized (this) {
				System.out.println("Reader " + number + " stops reading.");
<<<<<<< HEAD
				mediaplayer.setAutoPlay(false);
				icon[number].setImage(null);
=======

				count--;
				System.out.println("-people in Bline- " + count);
				mediaplayer.setAutoPlay(false);
>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783
				this.readers--;
				this.existreader--;
				finishread();
			}
		}

		public void write(int number) {
			synchronized (this) {
				this.lineofwriter++;
				System.out.println("Line of readers : " + lineofreader);
				System.out.println("Line of writers : " + lineofwriter);
				System.out.println("Reading people : " + readers);
				if (read_or_write == 0) {
					read_or_write = 2;
					canread = 0;
				} else if (read_or_write == 1) {
					canread = 0;
					canwrite = 1;
				}
				//create("WRITER", number + 5,  lineofreader+lineofwriter);
				System.out.println("Writer " + number + " is in line");
				w.offer(number);
			}
			synchronized (this) {
				while (canwrite == 0 || w.peek() != number || read_or_write != 2) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
				this.lineofwriter--;
			
				hi(number + 5, 1, "WRITER");
				System.out.println("Writer " + number + " starts writing.");
<<<<<<< HEAD
				mediaplayer = new MediaPlayer(musicFile_w);
				mediaplayer.setAutoPlay(true);
			}
=======
				count++;
				numqueue--;
				System.out.println("-people in Qline- " + numqueue);
				System.out.println("-people in Bline- " + count);
				mediaplayer = new MediaPlayer(musicFile_w);
				mediaplayer.setAutoPlay(true);
			}
			final int DELAY = 10000;
>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783
			try {
				int a = (int) (exprand(1)*5000);
				Thread.sleep(a);
				System.out.println(a);
				// Thread.sleep((int) (Math.random() * DELAY));
			} catch (InterruptedException e) {
			}
			synchronized (this) {
				System.out.println("Writer " + number + " stops writing.");
<<<<<<< HEAD
				mediaplayer.setAutoPlay(false);
				icon[number+5].setImage(null);
=======
				System.out.println("-people in Bline- " + count);
				mediaplayer.setAutoPlay(false);
>>>>>>> 8b0556e1e042b587be589e54b482310ae925d783
				w.poll();
				finishwrite();
			}
		}

		public void create(String name, int n, int people) {
			path[n]=new PathTransition();
			photoset(icon[n], name);
			pathset(p[n], name, people, n);
			path[n].setNode(icon[n]);
			move(path[n], p[n]);
		}

		double[] loc = new double[7];

		public void pathset(Path p, String name, int people, int n) {
			switch (name) {
			case "READER":
				p.getElements().add(new MoveTo(-20, -400));
				break;
			case "WRITER":
				p.getElements().add(new MoveTo(-40, -380));
				break;
			}
			loc[n] = getX(people, name);
			p.getElements().add(new LineTo(loc[n], -180));
		}

		public double getX(int people, String name) {
			double i = 0;
			switch (name) {
			case "READER":
				i = -100 + 70 * people;
				break;
			case "WRITER":
				i = -230 + 70 * people;
				break;
			}
			return i;
		}

		public void hi(int n, int people, String name) {
			path[n]=new PathTransition();
			photoset(icon[n], name);
			p[n].getElements().clear();
			p[n].getElements().add(new MoveTo(-100, -180));
			p[n].getElements().add(new LineTo(getF(people, name), 0));
			path[n].setNode(icon[n]);
			move(path[n], p[n]);
		}

		public double getF(int people, String name) {
			double i = 0;
			switch (name) {
			case "READER":
				i = -80 + 80 * people;
				break;
			case "WRITER":
				i = -200;
				break;
			}
			return i;
		}

		public void move(PathTransition t, Path p) {
			t.setDuration(Duration.seconds(1));
			t.setPath(p);
			t.play();
		}

		public void photoset(ImageView i, String name) {
			switch (name) {
			case "READER":
				i.setImage(new Image("file:src/application/EYE%20copy.png"));
				break;
			case "WRITER":
				i.setImage(new Image("file:src/application/PEN%20copy.png"));
				break;

			}
			i.setFitHeight(60);
			i.setFitWidth(60);
		}

	}
	/** Homebutton */
	@FXML
	private void gohome() {
		Main.showHome();
	}
}
