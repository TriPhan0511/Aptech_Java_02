package samples;

public class ThreadTester 
{
	public static void main(String[] args)
	{
		System.out.println("Current thread is: " + Thread.currentThread().getName());
		System.out.println("Number of current threads: " + Thread.activeCount());
		
//		Gets reference to the main thread
		Thread tMain = Thread.currentThread();
		
		NamedThread tName = new NamedThread();
		tName.setName("Thread1");
		System.out.println("Create a new thread but not execute it:");
		System.out.println("Current thread is: " + Thread.currentThread().getName());
		System.out.println("Number of current threads: " + Thread.activeCount());
		
//		Execute tName thread
		tName.start();
		
		System.out.println("After execute tName thread:");
		System.out.println("Current thread is: " + Thread.currentThread().getName());
		System.out.println("Number of current threads: " + Thread.activeCount());
		
		System.out.println("------------------------------------------------------------");
		
		System.out.println(tName.getName() + " thread is alive? " + tName.isAlive());
		System.out.println(tMain.getName() + " is alive? " + tMain.isAlive());
	}
}





























