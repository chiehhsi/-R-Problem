package rw;


public class Database {
	private int readers; // number of active readers
	private int count;
	private boolean flag=false;
	private boolean writeaccess=false;
	 
	  /**
	    Initializes this database.
	  */
	  public Database()
	  {
	    this.readers = 0;
	    count=0;
	  }
	 
	  /**
	    Read from this database.
	 
	    @param number Number of the reader.
	  */
	  public void read(int number)
	  {
		  System.out.print("count"+count+"\n");
		  System.out.print(flag+"\n");
		  if(count<3&&flag==false) {	  
	    synchronized(this)
	    {
	    	count++;
	    	 System.out.println("start");
	      this.readers++;
	      System.out.println("Reader " + number + " starts reading.");
	    }
	    final int DELAY = 5000;
	    try
	    {
	      Thread.sleep((int) (Math.random() * DELAY));
	    }
	    catch (InterruptedException e) {}
		 // }
		  if(count==3) {
			  flag=true;
		  }
	    synchronized(this)
	    {
	    	count--;
	    	System.out.println("stop");
	      System.out.println("Reader " + number + " stops reading.");
	      this.readers--;
	      if (this.readers==0&&flag==true)
	      {
	    	    writeaccess=true;
	        this.notifyAll();
	      }
	    }

	      if(flag==true&&writeaccess==false) {
	    	  flag=false;
	      }
		  } 
	  }
	 
	  /**
	    Writes to this database.
	 
	    @param number Number of the writer.
	  */
	  public synchronized void write(int number)
	  {
	    while (this.readers!=0&&writeaccess==true)
	    {
	      try
	      {
	        this.wait();
	      }
	      catch (InterruptedException e) {}
	    }
	    
	    System.out.println("Writer " + number + " starts writing.");
	 
	    final int DELAY = 5000;
	    try
	    {
	      Thread.sleep((int) (Math.random() * DELAY));
	    }
	    catch (InterruptedException e) {}
	 
	    System.out.println("Writer " + number + " stops writing.");
	    writeaccess=false;
	    flag=false;
	    this.notifyAll();
	 }
}
