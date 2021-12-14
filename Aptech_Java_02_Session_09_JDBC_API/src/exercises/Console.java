package exercises;

import java.util.Scanner;

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
}
