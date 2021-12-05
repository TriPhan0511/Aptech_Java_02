package wait_notify_notifyAll_in_Java;

import java.util.List;

/**
 * Link: https://howtodoinjava.com/java/multi-threading/wait-notify-and-notifyall-methods/
 * 
 * @version 1.0 2021-12-05
 * @author Tri Phan
 *
 */
public class Producer implements Runnable 
{
	private final List<Integer> taskQueue;
	private final int MAX_CAPACITY;
	
	public Producer(List<Integer> sharedQueue, int size)
	{
		this.taskQueue = sharedQueue;
		this.MAX_CAPACITY = size;
	}
	
	@Override
	public void run()
	{
		int counter = 0;
		while (true)
		{
			try
			{
				produce(counter++);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void produce(int i) throws InterruptedException
	{
		synchronized(taskQueue)
		{
			while (taskQueue.size() == MAX_CAPACITY)
			{
				System.out.println("Queue is full. " 
						+ Thread.currentThread().getName()
						+ " is waiting, size: "
						+ taskQueue.size());
				taskQueue.wait();
			}
			
			Thread.sleep(1000);
			taskQueue.add(i);
			System.out.println("Produced: " + i);
			taskQueue.notifyAll();
		}
	}
}








































