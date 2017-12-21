package application.process;
import java.util.Scanner;

import application.process.Database;
import application.process.Reader;
import application.process.Writer;

public class Simulator {
	private static Scanner  input;

	/**
	  This app creates a specified number of readers and 
	  writers and starts them.
	*/
	  /**
	    Creates the specified number of readers and writers and starts them.
	 
	    @param args[0] The number of readers.
	    @param args[1] The number of writers.
	  */
	  public static void main(String[] args)
	  {
	    //if (args.length < 2)
	    //{
	      System.out.println("Usage: java Simulator <number of readers> <number of writers>");
	      //input = new Scanner(System.in);
	      int READERS= input.nextInt();
	      int WRITERS= input.nextInt();
	      Database database = new Database();
	      for (int i = 0; i < READERS; i++)
	      {
	        new Reader(database).start();
	      }
	      for (int i = 0; i < WRITERS; i++)
	      {
	        new Writer(database).start();
	      }
	    //}
	  }
	}
