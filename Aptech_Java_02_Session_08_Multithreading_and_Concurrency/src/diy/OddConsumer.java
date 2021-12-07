package diy;

import java.util.List;

public class OddConsumer implements Runnable 
{
	private List<Integer> oddQueue;
	
	public OddConsumer(List<Integer> oddList) 
	{
		this.oddQueue = oddList;
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
		synchronized (oddQueue) 
		{
			while (oddQueue.isEmpty())
			{
				System.out.println("Odd queue is empty. Size of odd queue now is "
						+ oddQueue.size()
						+ ". "
						+ Thread.currentThread().getName()
						+ " is waiting");
				oddQueue.wait();
			}
			Thread.sleep(500);
			int i = oddQueue.remove(0);
			System.out.println("** Consumed an odd: " + i);
			oddQueue.notifyAll();
		}
	}
}
