package diy;

import java.util.List;

public class EvenConsumer implements Runnable 
{
	private List<Integer> evenQueue;
	
	public EvenConsumer(List<Integer> evenList) 
	{
		this.evenQueue = evenList;
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
		synchronized (evenQueue) 
		{
			while (evenQueue.isEmpty())
			{
				System.out.println("Even queue is empty. Size of even queue now is "
						+ evenQueue.size()
						+ ". "
						+ Thread.currentThread().getName()
						+ " is waiting");
				evenQueue.wait();
			}
			Thread.sleep(500);
			int i = evenQueue.remove(0);
			System.out.println("** Consumed an even: " + i);
			evenQueue.notifyAll();
		}
	}
}






















