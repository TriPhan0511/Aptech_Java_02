package exercises;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class NumberProducer implements Runnable 
{
	private final List<Integer> TASK_QUEUE;
	private final int MAX_CAPACITY;
	private final String FILE_NAME;
	
	public NumberProducer(List<Integer> sharedQueue, int size, String fileName)
	{
		this.TASK_QUEUE = sharedQueue;
		this.MAX_CAPACITY = size;
		this.FILE_NAME = fileName;
	}
	
	@Override
	public void run()
	{
		String s;
		Integer i;
		
		try
		(
			FileReader textFile = new FileReader(FILE_NAME);
			BufferedReader stream = new BufferedReader(textFile);
		)
		{
			stream.readLine(); // Read the first line of the file and ignore it
			s = stream.readLine();
			while (s != null)
			{
				i = Integer.parseInt(s);
				try
				{
					produce(i);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				s = stream.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("\nNo file was read.");
		}
		catch (IOException e)
		{
			System.out.println("\nThere was a problem reading the file.");
		}
	}
	
	private void produce(int i) throws InterruptedException
	{
		synchronized (TASK_QUEUE) 
		{
			while (TASK_QUEUE.size() == MAX_CAPACITY)
			{
				TASK_QUEUE.wait();
			}
			Thread.sleep(1000);
			TASK_QUEUE.add(i);
			TASK_QUEUE.notifyAll();
		}
	}
}

































