package exercise06;

import java.util.Scanner;

public class Console 
{
	public static String getString(Scanner sc, String prompt)
	{
		
		String s;
		while (true)
		{
			System.out.print(prompt);
			s = sc.nextLine();
			if (s == null || s.length() == 0)
			{
				System.out.println("The input should not be empty.");
				continue;
			}
			return s;
		}
	}
	
	public static char getChar(Scanner sc, String prompt)
	{
		return getString(sc, prompt).charAt(0);
	}
	
	public static double getDouble(Scanner sc, String prompt)
	{
		while (true)
		{
			System.out.print(prompt);
			try
			{
				return Double.parseDouble(sc.nextLine());
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid double. Try again.");
			}
		}
	}
}
