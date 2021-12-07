package diy;

import java.util.List;

public class Consumer implements Runnable
{
	private List<Integer> taskQueue;
	
	public Consumer(List<Integer> sharedQueue)
	{
		this.taskQueue = sharedQueue;
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
			{}
		}
	}
	
	
	private void consume() throws InterruptedException
	{
		synchronized (taskQueue)
		{
			while (taskQueue.isEmpty())
			{
				System.out.println("Queue is empty. Size of queue now is "
						+ taskQueue.size()
						+ ". "
						+ Thread.currentThread().getName()
						+ " is waiting");
				taskQueue.wait();
			}
			Thread.sleep(500);
			int i = taskQueue.remove(0);
			System.out.println("** Consumed: " + i);
			taskQueue.notifyAll();
		}
	}
}






















