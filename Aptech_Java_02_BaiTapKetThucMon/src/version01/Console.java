package version01;


import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Console 
{
	public static String nextString(String prompt)
	{
		Scanner sc = new Scanner(System.in);
		String out;
		while (true)
		{
			System.out.print(prompt);
			out = sc.nextLine();
			if (out == null || out.trim().length() == 0)
			{
				System.out.println("The input should not be empty. Please re-enter.");
				continue;
			}
			return out;
		}
	}
	
	public static char nextChar(String prompt)
	{
		Scanner sc = new Scanner(System.in);
		String s;
		while (true)
		{
			System.out.print(prompt);
			s = sc.nextLine();
			if (s == null || s.trim().length() == 0)
			{
				System.out.println("The input should not be empty. Please re-enter.");
				continue;
			}
			return s.charAt(0);
		}
	}

	public static int nextInt(String prompt)
	{
		Scanner sc = new Scanner(System.in);
		while (true)
		{
			System.out.print(prompt);
			try
			{
				return Integer.parseInt(sc.nextLine());
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid integer. Please re-enter.");
			}
		}
	}
	
	public static double nextDouble(String prompt)
	{
		Scanner sc = new Scanner(System.in);
		while (true) 
		{
			System.out.print(prompt);
			try
			{
				return Double.parseDouble(sc.nextLine());
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid double. Please re-enter.");
			}
		}
	}
	
	public static String nextDate(String prompt)
	{
		Scanner sc = new Scanner(System.in);
		String s;
		while (true)
		{
			System.out.print(prompt);
			try
			{
				s = sc.nextLine();
				LocalDate.parse(s); // Use it to validate a string can be parse to a date or not
				return s;
			}
			catch (DateTimeParseException e)
			{
				System.out.println("Invalid date. A valid date should be: yyyy-mm-dd\n"
						+ "Please re-enter.");
			}
		}
	}
}





















