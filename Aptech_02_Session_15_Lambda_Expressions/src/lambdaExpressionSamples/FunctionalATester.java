package lambdaExpressionSamples;

import java.util.Arrays;

/**
 * This class used for testing the functional interface named FucntionalA
 */
public class FunctionalATester 
{
	public static void main(String[] args)
	{
////		Using basic lambda expression
//		FunctionalA f = (int num1, int num2) -> num1 + num2;
//		System.out.println(f.doWork(1, 2)); // -> 3
//		
////		Using lambda expression with inferred type
//		FunctionalA f2 = (num1, num2) -> num1 + num2;
//		System.out.println(f2.doWork(10, 20)); // -> 30
//		
////		Using lambda expression with the body containing the return statement
//		FunctionalA f3 = (num1, num2) -> { return num1 + num2; };
//		System.out.println(f3.doWork(100, 200)); // -> 300
//		
////		Using lambda expression with the body containing multiple statements
//		FunctionalA f4 = (num1, num2) -> {
//			num1 = num1 * 2;
//			num2 = num2 * 3;
//			return num1 + num2;
//		};
//		System.out.println(f4.doWork(1, 2)); // -> 8
		
//		Passing lambda expression as a method parameter
//		to Arrays.sort() method.
		String[] words = { "Hi", "Hello", "HelloWorld", "H" };
		System.out.println("Original array:\n" 
				+ Arrays.toString(words)); 
//		-> [Hi, Hello, HelloWorld, H]
		Arrays.sort(words, (first, second) -> Integer.compare(first.length(), second.length()));
		System.out.println("\nAfter sorting:\n" 
		+ Arrays.toString(words));
//		-> [H, Hi, Hello, HelloWorld]
		
	}
}
























