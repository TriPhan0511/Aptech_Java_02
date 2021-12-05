package samples;

public class Event implements Runnable 
{
	public void run()
	{
		int count = 0;
		while (count <=3)
		{
			System.out.println(Thread.currentThread().getName());
			count++;
		}
	}
}
