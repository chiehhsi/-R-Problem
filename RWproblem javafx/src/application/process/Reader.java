package application.process;
import java.util.Random;
public class Reader extends Thread {
	private static int readers = 0; // number of readers
	 
	  private int number;
	  private Database database;
	  Random rand = new Random();
	 
	  /**
	    Creates a Reader for the specified database.
	 
	    @param database database from which to be read.
	  */
	  public Reader(Database database)
	  {
	    this.database = database;
	    this.number = Reader.readers++;
	  }
	  public double exprand(float lambda) {
		    return  Math.log(1-rand.nextDouble())/(-lambda);
		}
	  /**
	    Reads.
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
	        //Thread.sleep((int) (Math.random() * DELAY));
	      }
	      catch (InterruptedException e) {}
	      this.database.read(this.number);
	    }
	    
	  }
}
