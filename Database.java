package rw;
import java.util.LinkedList;
import java.util.Queue;

public class Database {
	private int readers; // number of active readers
	private int read_or_write;
	//private int writers;
	private Queue<Integer> w = new LinkedList<Integer>();
	private boolean readentry;
	private boolean writeentry;
	  /**
	    Initializes this database.
	  */
	  public Database()
	  {
	    this.readers = 0;
	    this.read_or_write = 0;
	    this.readentry = true ;
	    this.writeentry = true ;
	  //  this.writers = 0;
	  }
	 
	  /**
	    Read from this database.
	 
	    @param number Number of the reader.
	  */
	  void print() {
		  System.out.println(read_or_write);
		  System.out.println(readentry);
		  System.out.println(writeentry);
	  }
	  void readjudge() {
		  if(this.readers ==0 && w.isEmpty() )
		  {
			  this.read_or_write = 0;
			  this.readentry = true ;
			  this.writeentry = true ;
		  }
		  else if(this.readers == 0 && !w.isEmpty())
		  {  
			  this.read_or_write = 2;
		  }
		  this.notifyAll();
	  }
	  void writejudge() {
		  if(this.readers ==0 && w.isEmpty() )
		  {
			  this.read_or_write = 0;
			  this.readentry = true ;
			  this.writeentry = true ;
		  }
		  else if(this.readers != 0 && w.isEmpty())
		  {  
			  this.read_or_write = 1;
		  }
		  this.notifyAll();
	  }
	  public void read(int number)
	  {
		  synchronized(this)
		  {
			  System.out.println("Reader " + number +   " want entry!");
			  if(read_or_write==0)
				  read_or_write=1;
			  if(read_or_write==2)
				  writeentry = false;
			  
		  }
		synchronized(this)
		{
		while(readentry==false){
		  try {
			  this.wait();
			  }
		  catch(InterruptedException e) {}
		}
		this.readers++;
		System.out.println("Reader " + number + " is in line.") ;
		}
		synchronized(this) {
			while(read_or_write == 2)
			{
				try {
					this.wait();
				}
				catch(InterruptedException e) {}
			}
			System.out.println("Reader " + number + " Start reading. ");
			}
		final int DELAY = 5000;
		try
		{
			  Thread.sleep((int) (Math.random() * DELAY));
		  }
		  catch (InterruptedException e ) {}
		synchronized(this) {
		  System.out.println("Reader " + number + " stops reading.");
		  this.readers--;
		  readjudge();
		}
		}
	 
	  /**
	    Writes to this database.
	 
	    @param number Number of the writer.
	  */
	  public void write(int number)
	  {
		  System.out.println("Writer " + number + " want entry!");
		  if(read_or_write==0)
			read_or_write=2;
		  if(read_or_write==1)
		   readentry=false;
	//	  this.writers++;
		  synchronized(this)
		  {
			  while(writeentry==false)
			  {
				  try
				  {
					  this.wait();
				  }
				  catch (InterruptedException e) {}
			  }
	   System.out.println("Writer " + number + " is in line");
	   w.offer(number);
		  }
		  synchronized(this)
		  {
			  while(read_or_write == 1)
			  {
				  try {
					  this.wait();
				  }
				  catch(InterruptedException e) {}
			  }
		  }
		  synchronized(this)
		  {
			  while(w.peek()!=number)
			  {
				  try {
					  this.wait();
				  }catch(InterruptedException e) {}
			  }
		  
			  System.out.println("Writer " + number + " starts writing.");
		  }
			  final int DELAY = 5000;
	    try
	    {
	      Thread.sleep((int) (Math.random() * DELAY));
	    }
	    catch (InterruptedException e) {}
	    synchronized(this) {
	    System.out.println("Writer " + number + " stops writing.");
	    w.poll();
	    writejudge();
	   }
	   }
	  }
