package diy;

import java.util.List;

public class Producer implements Runnable 
{
	private List<Integer> evenQueue;
	private List<Integer> oddQueue;
	private int max_capacity;
	
	public Producer(List<Integer> evenList, List<Integer> oddList, int size)
	{
		this.evenQueue = evenList;
		this.oddQueue = oddList;
		this.max_capacity = size;
	}
	
	@Override
	public void run()
	{
		int counter = 0;
		while (counter < 10)
		{
			try
			{
				produce(counter++);
			}
			catch (InterruptedException e)
			{}
		}
	}
	
	private void produce(int i) throws InterruptedException
	{
		synchronized (evenQueue)
		{
			while (evenQueue.size() == max_capacity && oddQueue.size() == max_capacity)
			{
				System.out.println("Queue is full. Size of queue now is "
						+ evenQueue.size()
						+ ". "
						+ Thread.currentThread().getName()
						+ " is waiting.");
				evenQueue.wait();
				oddQueue.wait();
			}
			Thread.sleep(1000);
			if (isEven(i))
			{
				evenQueue.add(i);
				System.out.println("Produced: " + i);
				evenQueue.notifyAll();
			}
			else
			{
				oddQueue.add(i);
				System.out.println("Produced: " + i);
				oddQueue.notifyAll();
			}
		}
	}
	
	private boolean isEven(int number)
	{
		if (number % 2 == 0)
		{
			return true;
		}
		return false;
	}
}
































