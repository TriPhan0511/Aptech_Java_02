package diy;

import java.util.List;

public class Producer implements Runnable 
{
	private List<Integer> taskQueue;
	private int max_capacity;
	
	public Producer(List<Integer> sharedQueue, int size)
	{
		this.taskQueue = sharedQueue;
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
				produce(counter);
			}
			catch (InterruptedException e)
			{}
			counter++;
		}
	}
	
	private void produce(int i) throws InterruptedException
	{
		synchronized (taskQueue)
		{
			while (taskQueue.size() == max_capacity)
			{
				System.out.println("Queue is full. Size of queue now is "
						+ taskQueue.size()
						+ ". "
						+ Thread.currentThread().getName()
						+ " is waiting.");
				taskQueue.wait();
			}
			produce(i);
			System.out.println("Produced: " + i);
//			notifyAll();
		}
	}
}
































