import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This class consists of some methods which used to 
 * read/write data from/to text file.
 * 
 * @version 1.0 2021-12-04
 * @author Tri Phan
 *
 */
public class TextFileHandler 
{
	/**
	 * Clears existing content in a text file.
	 * @param fileName A string represents a file name.
	 */
	public void clearContent(String fileName)
	{
		try
		(
			FileWriter textFile = new FileWriter(fileName);
			PrintWriter stream = new PrintWriter(textFile);
		)
		{
			stream.print("");
		}
		catch (IOException e)
		{
			System.out.println("\nThere was a problem writing the file.");
		}
	}
	
	/**
	 * Writes (append) a line to a text file.
	 * @param s A string.
	 * @param fileName A string represents a file name.
	 */
	public void writeResult(String s, String fileName)
	{
		try
		(
			FileWriter textFile = new FileWriter(fileName, true);
			PrintWriter stream = new PrintWriter(textFile);
		)
		{
			stream.println(s);
		}
		catch (IOException e)
		{
			System.out.println("\nThere was a problem writing the file.");
		}
	}
	
	/**
	 * Creates a random number, named n, which is between 10 and 100,
	 * next, writes that number to the first line of the file,
	 * then, creates n random numbers which are between 1 and 500,
	 * and writes them to the next lines of the file, each number
	 * on one line.
	 * 
	 * @param fileName A string represents a file name.
	 */
	public void writeRandomNumbers(String fileName)
	{
		try
		(
			FileWriter textFile = new FileWriter(fileName);
			PrintWriter stream = new PrintWriter(textFile);
		)
		{
//			Creates an random number between 10 and 100
//			Then write it to the first line of the file.
			int n = randomInt(10, 100);
			stream.println(n);
			
//			Writes n random numbers which are between 1 and 500
//			to the next lines of the file (each number on one line).
			for (int i = 0; i < n; i++)
			{
				stream.println(randomInt(1, 500));
			}
		}
		catch (IOException e)
		{
			System.out.println("\nThere was a problem writing the file.");
		}
	}
	
	/**
	 * 
	 * Reads every line in a text file, parses each line to a number,
	 * then adds that number to a list.
	 * 
	 * @param fileName A string represents a file name.
	 * @param numberList A list of numbers.
	 */
	public void readFile(String fileName, List<Integer> numberList)
	{
		String line;
		int number;
		try
		(
			FileReader textFile = new FileReader(fileName);
			BufferedReader stream = new BufferedReader(textFile);
		)
		{
//			Read the first line of the file
			line = stream.readLine();
			
//			Parses every line to a number and adds it to the list
			while (line != null) // A null indicates the end of file
			{
				try
				{
					number = Integer.parseInt(line);
					numberList.add(number);
				}
				catch (NumberFormatException e)
				{}
				line = stream.readLine();
			}
			
//			Removes the first number in the list
			numberList.remove(0);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("\nNo file was read.");
		}
		catch (IOException e)
		{
			System.out.println("\nThere was a problem reading the file.");
		}
	}
	
	/**
	 * Creates a random integer between low and high.
	 * @param low lower-bound.
	 * @param high upper-bound.
	 * @return A random integer.
	 */
	private int randomInt(int low, int high)
	{
		return (int) (Math.random() * (high - low + 1)) + low;
	}
}

















