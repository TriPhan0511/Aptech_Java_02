package diy;

import java.util.ArrayList;
import java.util.List;

public class TestApp 
{
	public static void main(String[] args)
	{
		List<Integer> evenQueue = new ArrayList<>();
		List<Integer> oddQueue = new ArrayList<>();
		int max_capacity = 5;
		
		Thread tProducer = new Thread(new Producer(evenQueue, oddQueue, max_capacity), "Producer");
		Thread tEvenConsumer = new Thread(new EvenConsumer(evenQueue), "Even Cosumer");
		Thread tOddConsumer = new Thread(new OddConsumer(oddQueue), "Odd Consumer");
		
		tProducer.start();
		tEvenConsumer.start();
		tOddConsumer.start();
	}
}
