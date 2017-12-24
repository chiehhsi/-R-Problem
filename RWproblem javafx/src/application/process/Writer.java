package application.process;
import java.util.Random;
public class Writer extends Thread {
	private static int writers = 0; // number of writers
	 
	  private int number;
	  private application.process.RWprocesscontroller.Database database;
	  Random rand = new Random();
	 
	  /**
	    Creates a Writer for the specified database.
	 
	    @param database2 database to which to write.
	  */
	  public Writer(application.process.RWprocesscontroller.Database database2)
	  {
	    this.database = database2;
	    this.number = Writer.writers++;
	  }
	  public double exprand(float lambda) {
		    return  Math.log(1-rand.nextDouble())/(-lambda);
		}
	 
	  /**
	    Writes.
	  */
	  public void run()
	  {
	    while (true)
	    {
	      final int DELAY = 1000;
	      try
	      {
	    	  int a = (int)(exprand(10)*DELAY)  ;
		    	Thread.sleep(a);
		    	//System.out.println(a);
	       // Thread.sleep((int) (Math.random() * DELAY));
	      }
	      catch (InterruptedException e) {}
	      this.database.write(this.number);
	    }
	  }
}
