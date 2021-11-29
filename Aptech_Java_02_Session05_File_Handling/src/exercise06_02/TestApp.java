package exercise06_02;

public class TestApp 
{
	public static void main(String[] args) 
	{
		int i = Console.nextInt("Enter an integer: ");
		System.out.println("You entered " + i);
		double d = Console.nextDouble("Enter a double: ");
		System.out.printf("You entered %.2f", d);
		String s = Console.nextString("Enter a string: ");
		System.out.println("You entered: " + s);
		char c = Console.nextChar("Enter a character: ");
		System.out.println("You entered: " + c);
	}
}
