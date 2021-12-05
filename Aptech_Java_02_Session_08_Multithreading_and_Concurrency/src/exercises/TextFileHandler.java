package exercises;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

















