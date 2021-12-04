package exercise06;

import java.util.Scanner;

/**
 * This class consists of some static methods which used 
 * to get data from the user input from the console.
 * @version 1.0 2021-11-29
 * @author Tri Phan
 *
 */
public class Console 
{
//	Initializes a Scanner object which used to get data from the user.
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Gets a string from the user input from the console.
	 * @param prompt A String which used to ask the user enter a string.
	 * @param errorMessage A String displays error. 
	 * @return A String object.
	 */
	public static String nextString(String prompt, String errorMessage)
	{
		String s;
		while (true)
		{
			System.out.print(prompt);
			s = sc.nextLine();
			if (s == null || s.length() == 0)
			{
				System.out.println(errorMessage);
				continue;
			}
			return s;
		}
	}
	
	/**
	 * Gets a character from the user input from the console.
	 * @param prompt A String which used to ask the user enter a string.
	 * @param errorMessage A String displays error.
	 * @return A char.
	 */
	public static char nextChar(String prompt, String errorMessage)
	{
		return nextString(prompt, errorMessage).charAt(0);
	}
	
	/**
	 * Gets an integer from the user input from the console.
	 * @param prompt A String which used to ask the user enter a string.
	 * @param errorMessage A String displays error.
	 * @return An integer.
	 */
	public static int nextInt(String prompt, String errorMessage)
	{
		String s;
		while (true)
		{
			System.out.print(prompt);
			s = sc.nextLine();
			try
			{
				return Integer.parseInt(s);
			}
			catch (NumberFormatException e)
			{
				System.out.println(errorMessage);
			}
		}
	}
	
	/**
	 * Gets a double from the user input from the console.
	 * @param prompt A String which used to ask the user enter a string.
	 * @param errorMessage A String displays error.
	 * @return A double.
	 */
	public static double nextDouble(String prompt, String errorMessage)
	{
		String s;
		while (true)
		{
			System.out.print(prompt);
			s = sc.nextLine();
			try
			{
				return Double.parseDouble(s);
			}
			catch (NumberFormatException e)
			{
				System.out.println(errorMessage);
			}
		}
	}
}

























