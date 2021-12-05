package samples;

public class EventTester 
{
	public static void main(String[] args)
	{
		Event e1 = new Event();
		Event e2 = new Event();
		e1.setName("Thread 2");
		e2.setName("Thread 3");
		
		e1.start();
		e2.start();
		
		if (e2.isAlive())
		{
			System.out.println(e2.getName() + " is alive.");
		}
		else
		{ 
			System.out.println(e2.getName() + " is NOT alive.");
		}
		
		if (e1.isAlive())
		{
			System.out.println(e1.getName() + " is alive.");
		}
		else
		{ 
			System.out.println(e1.getName() + " is NOT alive.");
		}
		
		System.out.println("Number of threads running: " + Thread.activeCount());
	}
}
