package rw;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class Database {
	private int readers; // number of active readers
	private int read_or_write;
	private int canwrite;
	private int canread;
	private int existreader;
	private int lineofreader;
	private int lineofwriter;
	private Queue<Integer> w = new LinkedList<Integer>();
	Random rand = new Random();
	  /**
	    Initializes this database.
	  */
	  public Database()
	  {
		  this.lineofreader=0;
		  this.lineofwriter=0;
		  this.existreader=0;
		  this.canread = 1 ;
		  this.canwrite = 1 ;
		  this.readers = 0;
		  this.read_or_write = 0;
	  }
	 
	  public double exprand(float lambda) {
		    return  Math.log(1-rand.nextDouble())/(-lambda);
		}
	  void finishread()
	  {
		  if(this.readers==0 && !w.isEmpty())
		  {
			  read_or_write = 2 ;
			  canwrite=1;
			  canread=0;
		  }
		  else if(this.existreader!=0 && w.isEmpty())
		  {
			  read_or_write = 1;
			  canread = 1 ;
			  canwrite = 0;
		  }
		  else if (this.existreader == 0 && w.isEmpty())
		  {
			  read_or_write = 0 ;
			  canread = 1 ;
			  canwrite = 1;
		  }
		  this.notifyAll();
	  }
	  void finishwrite()
	  {
		  if(existreader!=0)
		  {
			  read_or_write = 1 ;
			  canread = 1;
			  canwrite = 0;
		  }
	    else if(existreader==0 && !w.isEmpty())
	    {
	    	read_or_write = 2;
	    	canwrite = 1;
	    	canread = 0;
	    }
	    else if(existreader==0 && w.isEmpty())
	    {
	    	read_or_write = 0;
	    	canwrite = 1;
	    	canread = 1;
	    }
	    this.notifyAll();
	  }
	  /**
	    Read from this database.
	 
	    @param number Number of the reader.
	  */
	  public void read(int number)
	  {
		  synchronized(this)
		  {
			  this.lineofreader++;
			  System.out.println("Reader " + number + " is in line.") ;
			  System.out.println("Line of readers : " +lineofreader);
			  System.out.println("Line of writers : " +lineofwriter);
			  System.out.println("Reading people : " +readers);
			  this.existreader++;
			  if(read_or_write == 0)
			  {
				  read_or_write = 1;
				  canwrite = 0 ;
			  }
			  else if(read_or_write == 2)
			  {
				  canwrite = 0 ;
				  canread = 1 ;
			  }
		 
			  
		  }
		  synchronized(this) {
			while(canread==0 || read_or_write != 1)
			{
				try {
					this.wait();
				}
				catch(InterruptedException e) {}
			}
			this.lineofreader--;
			System.out.println("Reader " + number + " Start reading. ");
			this.readers++;
		}
		
		try
		{ 
			int a = (int)exprand(1)*5000;
			Thread.sleep(a);
			System.out.println(a);
		}
		  catch (InterruptedException e ) {}
		synchronized(this) {
		  System.out.println("Reader " + number + " stops reading.");
		  this.readers--;
		  this.existreader--;
		 finishread();
		}
		}
	 
	  /**
	    Writes to this database.
	 
	    @param number Number of the writer.
	  */
	  public void write(int number)
	  {
		  synchronized(this)
		  {
			  this.lineofwriter++;
			  System.out.println("Line of readers : " +lineofreader);
			  System.out.println("Line of writers : " +lineofwriter);
			  System.out.println("Reading people : " +readers);
			  if(read_or_write==0) 
			  {
				  read_or_write = 2 ;
				  canread = 0 ;
			  }
			  else if (read_or_write == 1)
			  {
				  canread= 0 ;
				  canwrite = 1;
			  }
			  System.out.println("Writer " + number + " is in line");
			  w.offer(number);
		  }
		  synchronized(this)
		  {
			  while(canwrite==0||w.peek()!=number||read_or_write!=2)
			  {
				  try {
					  this.wait();
				  }catch(InterruptedException e) {}
			  }
			  this.lineofwriter--;
		  
			  System.out.println("Writer " + number + " starts writing.");
		  }
	    try
	    {
	    	int a = (int)(exprand(1)*5000)  ;
	    	Thread.sleep(a);
	    	System.out.println(a);
	    	//Thread.sleep((int) (Math.random() * DELAY));
	    }
	    catch (InterruptedException e) {}
	    synchronized(this) {
	    System.out.println("Writer " + number + " stops writing.");
	    w.poll();
	    finishwrite();
	   }
	   }
	  }
