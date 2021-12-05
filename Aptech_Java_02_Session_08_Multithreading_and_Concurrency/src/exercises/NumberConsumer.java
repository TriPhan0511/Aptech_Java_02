package exercises;

import java.util.ArrayList;
import java.util.List;

public class NumberConsumer implements Runnable
{
	private final List<Integer> TASK_QUEUE;
	
	public NumberConsumer(List<Integer> sharedQueue)
	{
		this.TASK_QUEUE = sharedQueue;
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				consume();
			}
			
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void consume() throws InterruptedException
	{
		synchronized (TASK_QUEUE) 
		{
			while (TASK_QUEUE.isEmpty())
			{
				TASK_QUEUE.wait();
			}
			Thread.sleep(10);
			int i = (Integer) TASK_QUEUE.remove(0);
			
			List<Integer> divisors = new ArrayList<>();
			String s;
			StringBuilder builder;
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
				System.out.println(s); 
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
				System.out.println(s); 
			}
			TASK_QUEUE.notifyAll();
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
