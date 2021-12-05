package exercises;

import java.util.ArrayList;
import java.util.List;

public class Main 
{
	public static void main(String[] args)
	{
		final String FILE_NAME = "numbers.txt";
		TextFileHandler fileHandler = new TextFileHandler();
		
		List<Integer> TASK_QUEUE = new ArrayList<Integer>();
		int MAX_CAPACITY = 5;
		
		fileHandler.writeRandomNumbers(FILE_NAME);
		
		Thread tProducer = new Thread(new NumberProducer(TASK_QUEUE, MAX_CAPACITY, FILE_NAME), "Producer");
		Thread tConsumer = new Thread(new NumberConsumer(TASK_QUEUE), "Consumer");
		
		tProducer.start();
		tConsumer.start();
	}
}
