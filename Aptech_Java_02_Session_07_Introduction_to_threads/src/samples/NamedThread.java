package samples;

public class NamedThread extends Thread 
{
	String name;
	
	public void run()
	{
		int count = 0;
		while (count <= 3)
		{
			System.out.println("\n==============================");
//			Display the name of the currently running thread
			System.out.println("Current thread IS: " + Thread.currentThread().getName());
			System.out.println("Number of current threads: " + Thread.activeCount());
			
			name = Thread.currentThread().getName();
			if (name.equals("Thread1"))
			{
				System.out.println("Marimba");
			}
			else
			{
				System.out.println("Jini");
			}
			count++;
		}
	}
}
