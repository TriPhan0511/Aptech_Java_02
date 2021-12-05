package samples;

public class EventTester 
{
	public static void main(String[] args)
	{
		System.out.println(Thread.currentThread().getName());
		
		Runnable event = new Event();
		Thread t = new Thread(event, "An event");
		t.start();
		
		System.out.println(Thread.currentThread().getName());
//		t.setPriority(Thread.MAX_PRIORITY);
//		t.setPriority(Thread.NORM_PRIORITY);
		t.setPriority(Thread.MIN_PRIORITY);
		System.out.println(t.getPriority());
		System.out.println(t.isDaemon());
	}
}
