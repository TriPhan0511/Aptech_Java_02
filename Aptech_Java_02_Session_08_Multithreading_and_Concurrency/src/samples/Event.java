package samples;

public class Event extends Thread 
{
	public void run()
	{
		while (true)
		{
			System.out.println(Thread.currentThread().getName());
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
	}
}
