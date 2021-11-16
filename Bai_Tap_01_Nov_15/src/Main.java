import java.util.Scanner;
import com.triphan.exceptions.*;

public class Main 
{
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		while (true)
		{
			try
			{
				int number = getInteger(sc, "\nEnter an integer: ");
				displayNumber(number);
				break;
			}
			catch (LessThanTenException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	private static void displayNumber(int number)
		throws LessThanTenException
	{
		if (number < 0) 
		{
			throw new LessThanTenException("The number should not be less than 0.");
		}
		else if (number == 0 )
		{
			throw new LessThanTenException("The number should be greater than 0.");
		}
		else if (number < 10)
		{
			throw new LessThanTenException("The number should be equal to or greater than 10.");
		}
		else
		{
			System.out.println("\nResult:");
			String s = String.valueOf(number);
			for (int i = 0; i < s.length(); i++)
			{
				System.out.println(s.charAt(i));
			}
		}
	}
	
	private static int getInteger(Scanner sc, String prompt)
	{
		while (true)
		{
			System.out.print(prompt);
			try
			{
				return Integer.parseInt(sc.nextLine());
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid integer. Try again.");
			}
		}
	}
}

















