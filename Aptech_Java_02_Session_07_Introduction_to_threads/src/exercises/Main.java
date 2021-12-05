package exercises;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
	public static void main(String[] args)
	{
		final String NUMBERS_FILE = "src/numbers.txt";
		final String RESULT_FILE = "src/result.txt";
		List<Integer> numberList = new ArrayList<>();
		List<Integer> divisors = new ArrayList<>();
		
//		Creates a TextFileHandler object
		TextFileHandler fileHandler = new TextFileHandler();
		
//		Answer to Question 1 in the exercises:
//		Writes random numbers to the numbers file
		fileHandler.writeRandomNumbers(NUMBERS_FILE);
		
//		Answer to Question 2 in the exercises:
//		Reads the numbers file and gets numbers from it
//		then add them to the list.
		fileHandler.readFile(NUMBERS_FILE, numberList);
		
//		 Clears existing content in result file		
		fileHandler.clearContent(RESULT_FILE);  
		
		String s;
		StringBuilder builder;
		
//		Iterates over the numbers in the numberList
//		Processes each number, prints out the result to the console,
//		then, writes the result to the result file 		
		for (Integer i : numberList)
		{
			if (isEven(i))
			{
				divisors = getDivisors(i);
				builder = new StringBuilder();
				builder.append(i);
				builder.append(" -> Divisors of ");
				builder.append(i);
				builder.append(": ");
				builder.append(stringListAll(divisors));
				
				s = builder.toString();
//				Prints out the result to the console
				System.out.println(s); 
//				Writes the result to the text file
				fileHandler.writeResult(s, RESULT_FILE); 
			}
			else
			{
				builder = new StringBuilder();
				builder.append(i);
				builder.append(" -> Square of ");
				builder.append(i);
				builder.append(": ");
				builder.append(i * i);
				
				s = builder.toString();
//				Prints out the result to the console
				System.out.println(s); 
//				Writes the result to the text file
				fileHandler.writeResult(s, RESULT_FILE); 
			}
		}
	}

	/**
	 * Prints out the content in a list of numbers to the console.
	 * 
	 * @param numbers A list of Integers.
	 */
	private static String stringListAll(List<Integer> numbers)
	{
		StringBuilder builder = new StringBuilder();
		int item;
		for (int i = 0; i < numbers.size(); i++)
		{
			item = numbers.get(i);
			if (i == numbers.size() - 1)
			{
				builder.append(item);
			}
			else
			{
				builder.append(item);
				builder.append(", ");
			}
		}
		return builder.toString();
	}
	
	/**
	 * Gets all of divisors of an integer.
	 * 
	 * @param number An integer.
	 * @return A list of Integers.
	 */
	private static List<Integer> getDivisors(int number)
	{
		List<Integer> divisors = new ArrayList<>();
		for (int i = 1; i <= number; i++)
		{
			if (number % i == 0)
			{
				divisors.add(i);
			}
		}
		return divisors;
	}
	
	/**
	 * Indicates a number is an even or odd.
	 * 
	 * @param number An integer.
	 * @return true indicates that number is an even,
	 * 	otherwise, returns false.
	 */
	private static boolean isEven(int number)
	{
		if (number % 2 == 0)
		{
			return true;
		}
		return false;
	}
}
