package rw;
import java.util.Random;
public class Writer extends Thread {
	private static int writers = 0; // number of writers
	 
	  private int number;
	  private Database database;
	  Random rand = new Random();
	 
	  /**
	    Creates a Writer for the specified database.
	 
	    @param database database to which to write.
	  */
	  public Writer(Database database)
	  {
	    this.database = database;
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
	      try
	      {
	    	  int a = (int)(exprand(5f)*1000)  ;
		    	Thread.sleep(a);
		    	System.out.println(a);
	       // Thread.sleep((int) (Math.random() * DELAY));
	      }
	      catch (InterruptedException e) {}
	      this.database.write(this.number);
	    }
	  }
}
