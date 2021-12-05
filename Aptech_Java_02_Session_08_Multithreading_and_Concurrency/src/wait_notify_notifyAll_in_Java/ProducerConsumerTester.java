package wait_notify_notifyAll_in_Java;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerTester 
{
	public static void main(String[] args)
	{
		List<Integer> taskQueue = new ArrayList<>();
		final int MAX_CAPACITY = 5;
		
		Thread tProducer = new Thread(new Producer(taskQueue, MAX_CAPACITY), "Producer");
		Thread tConsumer = new Thread(new Consumer(taskQueue), "Consumer");
		tProducer.start();
		tConsumer.start();
	}
}
